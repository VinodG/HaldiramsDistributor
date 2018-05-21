package com.winit.common.databaseaccess;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vinay.Y on 11/19/2016.
 */

public class WinitDB {


    private static WinitDB winitDB;
    private final String LOG_TAG = "vin";


    public void executeTransactionAsync(DBTaskListener dbTaskListener) {
        TransactionRunnable transactionRunnable = new TransactionRunnable();
        transactionRunnable.setDataListner(dbTaskListener);
        Thread transactionThread = new Thread(transactionRunnable);
        transactionThread.start();
    }

    public void executeTransaction(DBTaskListener dbTaskListener) {
        runProgram(dbTaskListener);
    }

    public static WinitDB getDefaultInstance() {
        if (winitDB == null) {
            winitDB = new WinitDB();
        }
        return winitDB;
    }

    private class TransactionRunnable implements Runnable {
        private DBTaskListener dbTaskListener;

        public void setDataListner(DBTaskListener dbTaskListener) {
            this.dbTaskListener = dbTaskListener;
        }

        @Override
        public void run() {
            runProgram(dbTaskListener);
        }
    }

    private void runProgram(DBTaskListener dbTaskListener) {

        SQLiteDatabase sqLiteDatabase = null;
        try {
//            sqLiteDatabase = DatabaseHelper.openDataBase();
//            sqLiteDatabase.beginTransaction();
            dbTaskListener.executeTask(sqLiteDatabase);
//            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            dbTaskListener.onError();
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
//                sqLiteDatabase.close();
            }
        }
    }

    public interface DBTaskListener {
        void executeTask(SQLiteDatabase sqLiteDatabase);

        void onError();
    }

}
