package com.winit.common.databaseaccess;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.winit.common.constant.AppConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 *  Description of class : This class having Database creation using SQLiteOpenHelper. 
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	
    public static SQLiteDatabase _database;
    private final Context myContext;

	public DatabaseHelper(Context context)
    {	 
    	super(context, AppConstants.DATABASE_NAME, null, 1);
        this.myContext = context;
    }
	
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws Exception
    {
       	boolean dbExist = checkDataBase();
 
    	if(!dbExist)
    	{
    		//By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
    		try
    		{
    			copyDataBase();
    		} 
    		catch (Exception e)
    		{	 
        		throw e;
         	}
    	} 
    }
    
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {	 
    	SQLiteDatabase checkDB = null;
    	try
    	{
    		String myPath = AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	}
    	catch(SQLiteException e)
    	{
    		//database does't exist yet.	 
    	}
    	if(checkDB != null)
    	{	 
    		checkDB.close();	 
    	}
 
    	return checkDB != null ? true : false;
    }
    
    /**
     * To Copy the database
     * @throws IOException
     */
    public void copyDataBase() throws IOException
    {
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(AppConstants.DATABASE_NAME);
 
    	// Path to the just created empty db
    	String outFileName = AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[2048];
    	int length;
    	while ((length = myInput.read(buffer))>0)
    	{
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
    
    //To open the database
    public static SQLiteDatabase openDataBase() throws SQLException
    {	
    	try
    	{
	    	//Open the database
	    	if(_database == null){
	    		_database = SQLiteDatabase.openDatabase(AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
	    				| SQLiteDatabase.CREATE_IF_NECESSARY);
	    	}else if(!_database.isOpen()){
	    		_database = SQLiteDatabase.openDatabase(AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE
	                    | SQLiteDatabase.CREATE_IF_NECESSARY);
	    	}
	    	return _database;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return _database;
    	}
    }
    
    //To close database
    public static void closedatabase() 
    { 
	    if(_database != null && _database.isOpen()){
//			_database.endTransaction();
			_database.close();
		}
	}

	public synchronized static void clearDatabase()
	{
		_database = null;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{		
		
	}

}
