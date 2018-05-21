package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.dataobject.SyncLogDO;

import java.util.ArrayList;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class SyncLogDA {
    public SyncLogDO getSyncLog(String module)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            SyncLogDO syncLogDO = new SyncLogDO();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT  UPMJ,UPMT,TimeStamp FROM tblSynLog WHERE Entity = '"+module+"'" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    syncLogDO.lsd = cursor.getString(0);
                    syncLogDO.lst = cursor.getString(1);
                    syncLogDO.timeStamp = cursor.getString(2);
                }
                else{
                    syncLogDO.lsd = "0";
                    syncLogDO.lst = "0";
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
            return syncLogDO;
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

    public boolean insertSyncLog(SyncLogDO syncLogDO)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblSynLog(Entity,Action,UPMJ,UPMT,TimeStamp) VALUES(?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblSynLog SET " +
                        "Action =? , UPMJ =?, UPMT =?, TimeStamp = ? WHERE Entity = ?");
                {
                    int i =0;
                    if(syncLogDO!=null)
                    {
                        stmtUpdate.bindString(++i,syncLogDO.action);
                        stmtUpdate.bindString(++i,syncLogDO.lsd);
                        stmtUpdate.bindString(++i,syncLogDO.lst);
                        stmtUpdate.bindString(++i,syncLogDO.timeStamp);
                        stmtUpdate.bindString(++i,syncLogDO.entity);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i,syncLogDO.entity);
                            stmtInsert.bindString(++i, syncLogDO.action);
                            stmtInsert.bindString(++i, syncLogDO.lsd);
                            stmtInsert.bindString(++i,syncLogDO.lst);
                            stmtInsert.bindString(++i, syncLogDO.timeStamp);
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
