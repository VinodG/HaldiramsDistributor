package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.haldiram.dataobject.UserDO;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class UserDA {
    public boolean insertUsers(Vector<UserDO> vecUsers)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblUser(UserId,UserCode,UserName) VALUES(?,?,?)");
                SQLiteStatement stmtUpdate = objSqliteDB.compileStatement("UPDATE tblUser SET " +
                        "UserCode =? , UserName =? WHERE UserCode = ?");
                for(UserDO userDO : vecUsers)
                {
                    int i =0;
                    if(userDO!=null)
                    {
                        stmtUpdate.bindString(++i,userDO.userId);
                        stmtUpdate.bindString(++i,userDO.userName);
                        stmtUpdate.bindString(++i,userDO.userId);
                        if(stmtUpdate.executeUpdateDelete() <=0)
                        {
                            i =0;
                            stmtInsert.bindString(++i, userDO.userId);
                            stmtInsert.bindString(++i,userDO.userId);
                            stmtInsert.bindString(++i, userDO.userName);
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

    public Vector<UserDO> getUsers()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            Vector<UserDO> arrUsers = new Vector<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT UserCode,UserName FROM tblUser" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        UserDO userDO = new UserDO();
                        userDO.userId = cursor.getString(0);
                        userDO.userName = cursor.getString(1);
                        arrUsers.add(userDO);
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
            return arrUsers;
        }
    }

    public ArrayList<String> getAllUsers()
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<String> arrUsers = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;

            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT UserCode,UserName FROM tblUser" ;

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        arrUsers.add(cursor.getString(0));
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
            return arrUsers;
        }
    }

}
