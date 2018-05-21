package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.haldiram.dataobject.InvestmentAnalysisDO;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class InvestmentAnalysisDA {
    public ArrayList<InvestmentAnalysisDO> getInvestments()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<InvestmentAnalysisDO> arrInvestments = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT SOH,SIT,CashWithCompany,SOHDays,SITDays,CashWithCompanyDays,MarketOutstanding,ProjectSales,ROWC " +
                        "FROM tblInvestmentAnalysis" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        InvestmentAnalysisDO investmentAnalysisDO = new InvestmentAnalysisDO();
                        investmentAnalysisDO.soh = cursor.getString(0);
                        investmentAnalysisDO.sit = cursor.getString(1);
                        investmentAnalysisDO.cashwithCompany = cursor.getString(2);
                        investmentAnalysisDO.sohDays = cursor.getString(3);
                        investmentAnalysisDO.sitDays = cursor.getString(4);
                        investmentAnalysisDO.cashwithCompanyDays = cursor.getString(5);
                        investmentAnalysisDO.marketOutstanding = cursor.getString(6);
                        investmentAnalysisDO.projectSales = cursor.getString(7);
                        investmentAnalysisDO.rowc = cursor.getString(8);
                        arrInvestments.add(investmentAnalysisDO);
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
            return arrInvestments;
        }
    }

    public boolean InvestmentAnalysis(Vector<InvestmentAnalysisDO> vecDashboards)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblInvestmentAnalysis(SOH,SIT,CashWithCompany,SOHDays,SITDays,CashWithCompanyDays,MarketOutstanding,ProjectSales,ROWC) VALUES(?,?,?,?,?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblInvestmentAnalysis SET " +
                        "SOH =? , CashWithCompany =?, SOHDays =?, SITDays = ?, CashWithCompanyDays= ?, MarketOutstanding = ? ,ProjectSales=?, ROWC=? WHERE SIT = ?");
                for(InvestmentAnalysisDO dashboardDO : vecDashboards)
                {
                    int i =0;
                    if(dashboardDO!=null)
                    {
                        stmtUpdate.bindString(++i,dashboardDO.soh);
                        stmtUpdate.bindString(++i,dashboardDO.cashwithCompany);
                        stmtUpdate.bindString(++i,dashboardDO.sohDays);
                        stmtUpdate.bindString(++i,dashboardDO.sitDays);
                        stmtUpdate.bindString(++i,dashboardDO.cashwithCompanyDays);
                        stmtUpdate.bindString(++i,dashboardDO.marketOutstanding);
                        stmtUpdate.bindString(++i,dashboardDO.projectSales);
                        stmtUpdate.bindString(++i,dashboardDO.rowc);
                        stmtUpdate.bindString(++i,dashboardDO.sit);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, dashboardDO.soh);
                            stmtInsert.bindString(++i,dashboardDO.sit);
                            stmtInsert.bindString(++i, dashboardDO.cashwithCompany);
                            stmtInsert.bindString(++i,dashboardDO.sohDays);
                            stmtInsert.bindString(++i, dashboardDO.sitDays);
                            stmtInsert.bindString(++i, dashboardDO.cashwithCompanyDays);
                            stmtInsert.bindString(++i, dashboardDO.marketOutstanding);
                            stmtInsert.bindString(++i, dashboardDO.projectSales);
                            stmtInsert.bindString(++i, dashboardDO.rowc);
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
