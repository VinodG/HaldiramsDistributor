package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.haldiram.dataobject.SalesEcoDO;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class SalesEcoDA {
    public ArrayList<SalesEcoDO> getSales()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<SalesEcoDO> arrSales = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMName,DSMCode,UOB,Eco,ULC,ZeroSales,NewOutlet FROM tblEcoDetails" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        SalesEcoDO salesEcoDO = new SalesEcoDO();
                        salesEcoDO.dsmCode = cursor.getString(0);
                        salesEcoDO.dsmName = cursor.getString(1);
                        salesEcoDO.uob = cursor.getString(2)+"%";
                        salesEcoDO.eco = cursor.getString(3)+"%";
                        salesEcoDO.ulc = cursor.getString(4);
                        salesEcoDO.zeroSales = cursor.getString(5);
                        salesEcoDO.newOutlet = cursor.getString(6);
                        arrSales.add(salesEcoDO);
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
            return arrSales;
        }
    }

    public ArrayList<SalesEcoDO> getSales(SQLiteDatabase objSqliteDB)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<SalesEcoDO> arrSales = new ArrayList<>();
            Cursor cursor 			   	= 	null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMName,DSMCode,UOB,Eco,ULC,ZeroSales,NewOutlet FROM tblEcoDetails" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        SalesEcoDO salesEcoDO = new SalesEcoDO();
                        salesEcoDO.dsmCode = cursor.getString(0);
                        salesEcoDO.dsmName = cursor.getString(1);
                        salesEcoDO.uob = cursor.getString(2)+"%";
                        salesEcoDO.eco = cursor.getString(3)+"%";
                        salesEcoDO.ulc = cursor.getString(4);
                        salesEcoDO.zeroSales = cursor.getString(5);
                        salesEcoDO.newOutlet = cursor.getString(6);
                        arrSales.add(salesEcoDO);
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

            }
            return arrSales;
        }
    }


    public boolean insertSalesECO(Vector<SalesEcoDO> vecDashboards)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblEcoDetails(DSMName,DSMCode,UOB,Eco,ULC,ZeroSales,NewOutlet) VALUES(?,?,?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblEcoDetails SET " +
                        "DSMName =? , UOB =?, Eco =?, ULC = ?, ZeroSales= ?, NewOutlet = ? WHERE DSMCode = ?");
                for(SalesEcoDO dashboardDO : vecDashboards)
                {
                    int i =0;
                    if(dashboardDO!=null)
                    {
                        stmtUpdate.bindString(++i,dashboardDO.dsmName);
                        stmtUpdate.bindString(++i,dashboardDO.uob);
                        stmtUpdate.bindString(++i,dashboardDO.eco);
                        stmtUpdate.bindString(++i,dashboardDO.ulc);
                        stmtUpdate.bindString(++i,dashboardDO.zeroSales);
                        stmtUpdate.bindString(++i,dashboardDO.newOutlet);
                        stmtUpdate.bindString(++i,dashboardDO.dsmCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, dashboardDO.dsmName);
                            stmtInsert.bindString(++i,dashboardDO.dsmCode);
                            stmtInsert.bindString(++i, dashboardDO.uob);
                            stmtInsert.bindString(++i,dashboardDO.eco);
                            stmtInsert.bindString(++i, dashboardDO.ulc);
                            stmtInsert.bindString(++i, dashboardDO.zeroSales);
                            stmtInsert.bindString(++i, dashboardDO.newOutlet);
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
