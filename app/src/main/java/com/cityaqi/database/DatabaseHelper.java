package com.cityaqi.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cityaqi.models.CityAqiData;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME        =   "city_aqi.db";
    public static final int DATABASE_VIRSION        =   1;





    public static final String  AQI_TABLE          =   "cityaqi";
    public static final String  _ID                 =   "id";
    public static final String  CITY_NAME           =   "city_name";
    public static final String  AQI                 =   "aqi";
    public static final String  UPDATE_TIME         =   "last_update_time";

    String aqiTable   = "create table "+AQI_TABLE+
            "( " +_ID+" integer primary key autoincrement,"+ CITY_NAME+"  text,"+
            AQI+"  Numeric,"+
            UPDATE_TIME+"   Numeric); ";




    Context context;



    public  static DatabaseHelper  databaseHelper;

    public static DatabaseHelper getIntance(Context context)
    {
        if(databaseHelper   == null)
        {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }
    public static DatabaseHelper newIntance(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
        return databaseHelper;
    }
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VIRSION);
        this.context        =   context;
    }
    public DatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VIRSION, errorHandler);
        this.context        =   context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(aqiTable);
        db.execSQL(insertRecordTrigger());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //in Version 2


    }



    public String insertRecordTrigger(){
        return "CREATE TRIGGER if not exists city_Aqi "
                + " AFTER INSERT "
                + " ON "+AQI_TABLE+" "
                + " for each row "
                + " BEGIN "
                + " DELETE from "+AQI_TABLE+" where "+ UPDATE_TIME+ " < " +
                "( SELECT "+UPDATE_TIME
                +" FROM "+AQI_TABLE +" WHERE "+CITY_NAME+" = new."+CITY_NAME
                +" ORDER BY "+UPDATE_TIME+" DESC"
                    +" LIMIT 1 OFFSET 100"
                +" );"
                + " END;";
    }

    public synchronized void insertCityAqi(ArrayList<CityAqiData> cityAqiData)
    {
        SQLiteDatabase database = getWritableDatabase();
        for (int i = 0; i < cityAqiData.size(); i++) {

            double  max =  cityAqiData.get(i).getAqi();
            max =Double.parseDouble(new DecimalFormat("##.##").format(max));
            ContentValues values = new ContentValues();
            values.put(CITY_NAME, cityAqiData.get(i).getCity());
            values.put(AQI, max);
            values.put(UPDATE_TIME, Calendar.getInstance().getTimeInMillis());
            database.insert(AQI_TABLE, null, values);
        }

        database.close();
    }
    public synchronized ArrayList<CityAqiData> getCityAQi()
    {
        ArrayList<CityAqiData> cityAqiDataList  = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cSSID = database.rawQuery("SELECT * FROM "+AQI_TABLE + " GROUP BY " + CITY_NAME+" order by "+UPDATE_TIME+" desc",null);
        if(cSSID.moveToFirst())
        {
            do {
                CityAqiData cityAqiData = new CityAqiData();
                cityAqiData.setCity(cSSID.getString(cSSID.getColumnIndex(CITY_NAME)));
                cityAqiData.setAqi(cSSID.getDouble(cSSID.getColumnIndex(AQI)));
                cityAqiData.setTimeStamp(cSSID.getLong(cSSID.getColumnIndex(UPDATE_TIME)));
                cityAqiDataList.add(cityAqiData);
            }while (cSSID.moveToNext());
        }
        cSSID.close();
        database.close();
        return cityAqiDataList;
    }

    public synchronized ArrayList<CityAqiData> getCityAQiByCIty(String city)
    {
        ArrayList<CityAqiData> cityAqiDataList  = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cSSID = database.rawQuery("SELECT * FROM "+AQI_TABLE+ " WHERE " + CITY_NAME + " = '" + city + "' order by "+UPDATE_TIME+" desc limit 8",null);
        if(cSSID.moveToFirst())
        {
            do {
                CityAqiData cityAqiData = new CityAqiData();
                cityAqiData.setCity(cSSID.getString(cSSID.getColumnIndex(CITY_NAME)));
                cityAqiData.setAqi(cSSID.getLong(cSSID.getColumnIndex(AQI)));
                cityAqiData.setTimeStamp(cSSID.getLong(cSSID.getColumnIndex(UPDATE_TIME)));
                cityAqiDataList.add(cityAqiData);
            }while (cSSID.moveToNext());
        }
        cSSID.close();
        database.close();
        return cityAqiDataList;
    }

}