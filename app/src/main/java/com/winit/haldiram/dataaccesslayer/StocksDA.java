package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.haldiram.dataobject.StockDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Srikanth on 5/11/2017.
 */

public class StocksDA {
    public HashMap<String,ArrayList<StockDO>> getStocks(SQLiteDatabase objSqliteDB)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            HashMap<String,ArrayList<StockDO>> hmStocks = new HashMap<>();
            Cursor cursor 			   	= 	null;

            try
            {

                String strQuery = "SELECT 'Model Stock',* FROM tblModelStock" +
                        " UNION " +
                        "SELECT 'OOS',* FROM tblOutOfStock" +
                        " UNION " +
                        "SELECT 'Over Stock',* FROM tblOverStock";

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String module = cursor.getString(0);
                        ArrayList<StockDO> vecStock = hmStocks.get(module);
                        if(vecStock == null){
                            vecStock = new ArrayList<>();
                            hmStocks.put(module,vecStock);
                        }
                        StockDO stockDO = new StockDO();
                        stockDO.itemCode = cursor.getString(1);
                        stockDO.itemName = cursor.getString(2);
                        stockDO.productNorm = cursor.getString(3);
                        stockDO.stockQty = cursor.getString(4);
                        stockDO.noOfDays = cursor.getString(5);
                        vecStock.add(stockDO);
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

            return hmStocks;
        }
    }

    public HashMap<String,ArrayList<StockDO>> getStocks()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            HashMap<String,ArrayList<StockDO>> hmStocks = new HashMap<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT 'Model Stock',* FROM tblModelStock" +
                        " UNION " +
                        "SELECT 'OOS',* FROM tblOutOfStock" +
                        " UNION " +
                        "SELECT 'Over Stock',* FROM tblOverStock";

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String module = cursor.getString(0);
                        ArrayList<StockDO> vecStock = hmStocks.get(module);
                        if(vecStock == null){
                            vecStock = new ArrayList<>();
                            hmStocks.put(module,vecStock);
                        }
                        StockDO stockDO = new StockDO();
                        stockDO.itemCode = cursor.getString(1);
                        stockDO.itemName = cursor.getString(2);
                        stockDO.productNorm = cursor.getString(3);
                        stockDO.stockQty = cursor.getString(4);
                        stockDO.noOfDays = cursor.getString(5);
                        vecStock.add(stockDO);
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

            return hmStocks;
        }
    }

    public boolean insertModelStock(Vector<StockDO> vecStocks)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblModelStock(ItemCode,ItemName,ProductNorm,StockQty,NoOfDays) VALUES(?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblModelStock SET " +
                        "ItemName =? , ProductNorm =?, StockQty =?, NoOfDays = ? WHERE ItemCode = ?");
                for(StockDO stockDO : vecStocks)
                {
                    int i =0;
                    if(stockDO!=null)
                    {
                        stmtUpdate.bindString(++i,stockDO.itemName);
                        stmtUpdate.bindString(++i,stockDO.productNorm);
                        stmtUpdate.bindString(++i,stockDO.stockQty);
                        stmtUpdate.bindString(++i,stockDO.noOfDays);
                        stmtUpdate.bindString(++i,stockDO.itemCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, stockDO.itemCode);
                            stmtInsert.bindString(++i,stockDO.itemName);
                            stmtInsert.bindString(++i, stockDO.productNorm);
                            stmtInsert.bindString(++i,stockDO.stockQty);
                            stmtInsert.bindString(++i, stockDO.noOfDays);
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

    public boolean insertOOS(Vector<StockDO> vecStocks)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblOutOfStock(ItemCode,ItemName,ProductNorm,StockQty,NoOfDays) VALUES(?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblOutOfStock SET " +
                        "ItemName =? , ProductNorm =?, StockQty =?, NoOfDays = ? WHERE ItemCode = ?");
                for(StockDO stockDO : vecStocks)
                {
                    int i =0;
                    if(stockDO!=null)
                    {
                        stmtUpdate.bindString(++i,stockDO.itemName);
                        stmtUpdate.bindString(++i,stockDO.productNorm);
                        stmtUpdate.bindString(++i,stockDO.stockQty);
                        stmtUpdate.bindString(++i,stockDO.noOfDays);
                        stmtUpdate.bindString(++i,stockDO.itemCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, stockDO.itemCode);
                            stmtInsert.bindString(++i,stockDO.itemName);
                            stmtInsert.bindString(++i, stockDO.productNorm);
                            stmtInsert.bindString(++i,stockDO.stockQty);
                            stmtInsert.bindString(++i, stockDO.noOfDays);
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
    public boolean insertOverStock(Vector<StockDO> vecStocks)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblOverStock(ItemCode,ItemName,ProductNorm,StockQty,NoOfDays) VALUES(?,?,?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblOverStock SET " +
                        "ItemName =? , ProductNorm =?, StockQty =?, NoOfDays = ? WHERE ItemCode = ?");
                for(StockDO stockDO : vecStocks)
                {
                    int i =0;
                    if(stockDO!=null)
                    {
                        stmtUpdate.bindString(++i,stockDO.itemName);
                        stmtUpdate.bindString(++i,stockDO.productNorm);
                        stmtUpdate.bindString(++i,stockDO.stockQty);
                        stmtUpdate.bindString(++i,stockDO.noOfDays);
                        stmtUpdate.bindString(++i,stockDO.itemCode);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, stockDO.itemCode);
                            stmtInsert.bindString(++i,stockDO.itemName);
                            stmtInsert.bindString(++i, stockDO.productNorm);
                            stmtInsert.bindString(++i,stockDO.stockQty);
                            stmtInsert.bindString(++i, stockDO.noOfDays);
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
