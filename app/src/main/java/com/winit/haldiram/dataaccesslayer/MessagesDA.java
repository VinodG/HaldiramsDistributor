package com.winit.haldiram.dataaccesslayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winit.common.application.HaldiramsDistributorApplication;
import com.winit.common.databaseaccess.DatabaseHelper;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.dataobject.CustomerMessageDO;
import com.winit.haldiram.dataobject.MessageDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class MessagesDA {
    public HashMap<String,ArrayList<MessageDO>> getMessages(String userId)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            HashMap<String,ArrayList<MessageDO>> hmMessages = new HashMap<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DISTINCT MESSAGEID,FROMID,TOID,MESSAGE,MESSAGEDATE,IFNULL(IsRead,''),PARENTMESSAGEID FROM tblMessages WHERE IFNULL(TOID,'')!='' ORDER BY MESSAGEDATE DESC";

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String fromId = cursor.getString(1);
                        String toId = cursor.getString(2);
                        String key="";
                        if(toId.equalsIgnoreCase(userId))
                            key = fromId;
                        else
                            key = toId;
                        ArrayList<MessageDO> arrMessages = hmMessages.get(key);
                        if(arrMessages == null){
                            arrMessages = new ArrayList<>();
                            hmMessages.put(key,arrMessages);
                        }
                        MessageDO messageDO = new MessageDO();
                        messageDO.messageId = cursor.getString(0);
                        messageDO.fromId = fromId;
                        messageDO.toId = toId;
                        messageDO.message = cursor.getString(3);
                        messageDO.messageDate = cursor.getString(4);
                        messageDO.isRead = cursor.getString(5).equalsIgnoreCase("True");
                        messageDO.parentMessageId = cursor.getString(6);
                        arrMessages.add(messageDO);
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

            return hmMessages;
        }
    }

    public ArrayList<MessageDO> getMessages(String fromId,String toId)
    {
        synchronized (HaldiramsDistributorApplication.MyLock) {
            ArrayList<MessageDO> arrMessages = new ArrayList<>();
            Cursor cursor 			   	= 	null;
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB = DatabaseHelper.openDataBase();
                String strQuery = "SELECT DISTINCT MESSAGEID,FROMID,TOID,MESSAGE,MESSAGEDATE,IFNULL(IsRead,''),PARENTMESSAGEID FROM tblMessages " +
                        "WHERE IFNULL(TOID,'')!='' AND ((FROMID = '"+fromId+"' AND TOID = '"+toId+"') OR (FROMID = '"+toId+"' AND TOID = '"+fromId+"')) ORDER BY MESSAGEDATE DESC";

                cursor = objSqliteDB.rawQuery(strQuery, null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        MessageDO messageDO = new MessageDO();
                        messageDO.messageId = cursor.getString(0);
                        messageDO.fromId = cursor.getString(1);
                        messageDO.toId = cursor.getString(2);
                        messageDO.message = cursor.getString(3);
                        messageDO.messageDate = cursor.getString(4);
                        messageDO.isRead = cursor.getString(5).equalsIgnoreCase("True");
                        messageDO.parentMessageId = cursor.getString(6);
                        arrMessages.add(messageDO);
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

            return arrMessages;
        }
    }

    public boolean insertIntoMessage(Vector<CustomerMessageDO> vecCustomerMessageDOs)
    {
        synchronized(HaldiramsDistributorApplication.MyLock)
        {
            SQLiteDatabase objSqliteDB = null;
            try
            {
                objSqliteDB  = DatabaseHelper.openDataBase();
                SQLiteStatement stmtSelectRec = objSqliteDB.compileStatement("SELECT COUNT(*) from tblMessages WHERE MessageId =?");
                SQLiteStatement stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblMessages(MESSAGEID,FROMID,TOID,MESSAGE,MESSAGEDATE,PARENTMESSAGEID,IsRead) VALUES(?,?,?,?,?,?,?)");
                for(int i=0;i<vecCustomerMessageDOs.size();i++)
                {
                    CustomerMessageDO customerMessageDO = vecCustomerMessageDOs.get(i);
                    stmtSelectRec.bindString(1,customerMessageDO.strMessageID);
                    long countRec = stmtSelectRec.simpleQueryForLong();
                    if(countRec != 0)
                    {

                    }
                    else
                    {
                        stmtInsert.bindLong(1, StringUtils.getLong(customerMessageDO.strMessageID));
                        stmtInsert.bindString(2, customerMessageDO.strFROMID);
                        stmtInsert.bindString(3,customerMessageDO.strTOID);
                        stmtInsert.bindString(4, customerMessageDO.strMessage.trim());
                        stmtInsert.bindString(5,customerMessageDO.strMessageDate);
                        stmtInsert.bindString(6, customerMessageDO.parentMessageId);
                        stmtInsert.bindString(7, customerMessageDO.ISREAD);
                        stmtInsert.executeInsert();
                    }
                }
                stmtInsert.close();
                stmtSelectRec.close();
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
