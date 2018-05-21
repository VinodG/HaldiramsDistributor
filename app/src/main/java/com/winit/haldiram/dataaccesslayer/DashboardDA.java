package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.dataobject.DashboardDO;
import com.winit.haldiram.dataobject.MonthlyDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class DashboardDA {
    public ArrayList<DashboardDO> getAchieved()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<DashboardDO> arrAchieved = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMName,DSMCode,Target,Acheived,Percentage,BTG,DailyAverage FROM tblAchievement" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        DashboardDO dashboardDO = new DashboardDO();
                        dashboardDO.dsmCode = cursor.getString(0);
                        dashboardDO.dsmName = cursor.getString(1);
                        dashboardDO.target = cursor.getString(2);
                        dashboardDO.achieved = cursor.getString(3);
                        dashboardDO.percentage = cursor.getString(4)+"%";
                        dashboardDO.btg = cursor.getString(5);
                        dashboardDO.dailyAverage = cursor.getString(6)+"%";
                        arrAchieved.add(dashboardDO);
                    }
                    while(cursor.moveToNext());
                }
                if(cursor != null && !cursor.isClosed())
                    cursor.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(cursor != null && !cursor.isClosed())
                    cursor.close();
                if(objSqliteDB != null && objSqliteDB.isOpen())
                {
                    objSqliteDB.close();
                }

            }
            return arrAchieved;
        }
    }

    public HashMap<String,ArrayList<MonthlyDO>> getGraphData()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            HashMap<String,ArrayList<MonthlyDO>> hmSales = new HashMap<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT Year,CAST(Month AS Integer),CAST(Sales AS Double)FROM tblYearWiseSales ORDER BY CAST(Month AS Integer)" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String year = cursor.getString(0);
                        ArrayList<MonthlyDO> arrMonthlySales = hmSales.get(year);
                        if(arrMonthlySales == null){
                            arrMonthlySales = new ArrayList<>();
                            hmSales.put(year,arrMonthlySales);
                        }
                        MonthlyDO monthlyDO = new MonthlyDO();
                        monthlyDO.month = cursor.getInt(1);
//                        if(year.equalsIgnoreCase("2017") && monthlyDO.month == 1)
//                            monthlyDO.sales = 80000;
//                        else if(year.equalsIgnoreCase("2016") && monthlyDO.month == 1)
//                            monthlyDO.sales = 100000;
//                        else if(year.equalsIgnoreCase("2015") && monthlyDO.month == 1)
//                            monthlyDO.sales = 150000;
//                        else
                            monthlyDO.sales = StringUtils.roundDouble(cursor.getDouble(2),2);
                        arrMonthlySales.add(monthlyDO);
                    }
                    while(cursor.moveToNext());
                }
                if(cursor != null && !cursor.isClosed())
                    cursor.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(cursor != null && !cursor.isClosed())
                    cursor.close();
                if(objSqliteDB != null && objSqliteDB.isOpen())
                {
                    objSqliteDB.close();
                }

            }
            return hmSales;
        }
    }
    public boolean insertAchievement(Vector<DashboardDO> vecDashboards)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblAchievement(DSMName,DSMCode,Target,Acheived,Percentage,BTG,DailyAverage) VALUES(?,?,?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblAchievement SET " +
                        "DSMName =? , Target =?, Acheived =?, Percentage = ?, BTG= ?, DailyAverage = ? WHERE DSMCode = ?");
                for(DashboardDO dashboardDO : vecDashboards)
                {
                    int i =0;
                    if(dashboardDO!=null)
                    {
                        stmtUpdate.bindString(++i,dashboardDO.dsmName);
                        stmtUpdate.bindString(++i,dashboardDO.target);
                        stmtUpdate.bindString(++i,dashboardDO.achieved);
                        stmtUpdate.bindString(++i,dashboardDO.percentage);
                        stmtUpdate.bindString(++i,dashboardDO.btg);
                        stmtUpdate.bindString(++i,dashboardDO.dailyAverage);
                        stmtUpdate.bindString(++i,dashboardDO.dsmCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, dashboardDO.dsmCode);
                            stmtInsert.bindString(++i,dashboardDO.dsmName);
                            stmtInsert.bindString(++i, dashboardDO.target);
                            stmtInsert.bindString(++i,dashboardDO.achieved);
                            stmtInsert.bindString(++i, dashboardDO.percentage);
                            stmtInsert.bindString(++i, dashboardDO.btg);
                            stmtInsert.bindString(++i, dashboardDO.dailyAverage);
                            stmtInsert.executeInsert();
                        }
                    }
                }
                stmtInsert.close();
                stmtUpdate.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
            finally
            {
                if(objSqliteDB != null)
                {
                    objSqliteDB.close();
                }
            }
            return true;
        }
    }

}
