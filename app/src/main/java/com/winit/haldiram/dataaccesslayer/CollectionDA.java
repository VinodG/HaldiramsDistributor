package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.dataobject.CollectionsDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class CollectionDA {
    public ArrayList<CollectionsDO> getCollections()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<CollectionsDO> arrCollections = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMName,DSMCode,CustomerCode,CustomerName,TotalOutstanding,NoOfBillsOverdue,NoOfBillsOutstanding " +
                        "FROM tblCollection WHERE IFNULL(DSMCode,'')!=''" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        CollectionsDO collectionsDO = new CollectionsDO();
                        collectionsDO.dsmCode = cursor.getString(0);
                        collectionsDO.dsmName = cursor.getString(1);
                        collectionsDO.customerCode = cursor.getString(2);
                        collectionsDO.customerName = cursor.getString(3);
                        collectionsDO.totalOutStanding = cursor.getString(4);
                        collectionsDO.noOfBillsOverdue = cursor.getString(5);
                        collectionsDO.noOfBillsOutstanding = cursor.getString(6);
                        arrCollections.add(collectionsDO);
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
            return arrCollections;
        }
    }
    public ArrayList<CollectionsDO> getCollections(SQLiteDatabase objSqliteDB)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<CollectionsDO> arrCollections = new ArrayList<>();
            Cursor cursor 			   	= 	null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMName,DSMCode,CustomerCode,CustomerName,TotalOutstanding,NoOfBillsOverdue,NoOfBillsOutstanding " +
                        "FROM tblCollection WHERE IFNULL(DSMCode,'')!=''" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        CollectionsDO collectionsDO = new CollectionsDO();
                        collectionsDO.dsmCode = cursor.getString(0);
                        collectionsDO.dsmName = cursor.getString(1);
                        collectionsDO.customerCode = cursor.getString(2);
                        collectionsDO.customerName = cursor.getString(3);
                        collectionsDO.totalOutStanding = cursor.getString(4);
                        collectionsDO.noOfBillsOverdue = cursor.getString(5);
                        collectionsDO.noOfBillsOutstanding = cursor.getString(6);
                        arrCollections.add(collectionsDO);
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
            return arrCollections;
        }
    }
    public Object[] getHMCollections()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            Object[] object = new Object[2];
            HashMap<String,Vector<CollectionsDO>> hmCollections = new HashMap<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;
            Double totalDue = 0d;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DSMCode,DSMName,CustomerCode,CustomerName,TotalOutstanding,NoOfBillsOverdue,NoOfBillsOutstanding " +
                        "FROM tblCollection WHERE IFNULL(DSMCode,'')!=''" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String module = cursor.getString(0);
                        Vector<CollectionsDO> vecCollections = hmCollections.get(module);
                        if(vecCollections == null){
                            vecCollections = new Vector<>();
                            hmCollections.put(module,vecCollections);
                        }
                        CollectionsDO collectionsDO = new CollectionsDO();
                        collectionsDO.dsmCode = cursor.getString(0);
                        collectionsDO.dsmName = cursor.getString(1);
                        collectionsDO.customerCode = cursor.getString(2);
                        collectionsDO.customerName = cursor.getString(3);
                        collectionsDO.totalOutStanding = cursor.getString(4);
                        collectionsDO.noOfBillsOverdue = cursor.getString(5);
                        collectionsDO.noOfBillsOutstanding = cursor.getString(6);
                        totalDue += StringUtils.roundDouble(cursor.getDouble(4),2);
                        vecCollections.add(collectionsDO);
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
                object[0] = hmCollections;
                object[1] = totalDue;
                if(cursor != null && !cursor.isClosed())
                    cursor.close();
                if(objSqliteDB != null && objSqliteDB.isOpen())
                {
                    objSqliteDB.close();
                }
            }

            return object;
        }
    }


    public boolean insertCollection(Vector<CollectionsDO> vecDashboards)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblCollection(DSMName,DSMCode,CustomerCode,CustomerName,TotalOutstanding,NoOfBillsOverdue,NoOfBillsOutstanding) VALUES(?,?,?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblCollection SET " +
                        "DSMName =? , CustomerCode =?, CustomerName =?, TotalOutstanding = ?, NoOfBillsOverdue= ?, NoOfBillsOutstanding = ? WHERE DSMCode = ? AND CustomerCode=?");
                for(CollectionsDO collectionsDO : vecDashboards)
                {
                    int i =0;
                    if(collectionsDO!=null)
                    {
                        stmtUpdate.bindString(++i,collectionsDO.dsmName);
                        stmtUpdate.bindString(++i,collectionsDO.customerCode);
                        stmtUpdate.bindString(++i,collectionsDO.customerName);
                        stmtUpdate.bindString(++i,collectionsDO.totalOutStanding);
                        stmtUpdate.bindString(++i,collectionsDO.noOfBillsOverdue);
                        stmtUpdate.bindString(++i,collectionsDO.noOfBillsOutstanding);
                        stmtUpdate.bindString(++i,collectionsDO.dsmCode);
                        stmtUpdate.bindString(++i,collectionsDO.customerCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i,collectionsDO.dsmName);
                            stmtInsert.bindString(++i, collectionsDO.dsmCode);
                            stmtInsert.bindString(++i, collectionsDO.customerCode);
                            stmtInsert.bindString(++i,collectionsDO.customerName);
                            stmtInsert.bindString(++i, collectionsDO.totalOutStanding);
                            stmtInsert.bindString(++i, collectionsDO.noOfBillsOverdue);
                            stmtInsert.bindString(++i, collectionsDO.noOfBillsOutstanding);
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
