package jasmine.vsnick.attendence;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jasmine.vsnick.attendence.R;

/**
 * Created by vsnick on 19-06-2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String name, int version) {
        super(context, name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //"CREATE TABLE table_name(Key_id INTEGER PRIMARY KEY,Key_name TEXT,Key_ph_no TEXT)"
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "atten"+ "(" + "date"+ " TEXT PRIMARY KEY," + "filled" + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public void add(String date,int no)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("date",date);
        values.put("filled",no);
        db.insert("atten",null,values);
        db.close();
    }
    public String no_of_periods(String date)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query("atten",new String[]{"date","filled"},"date=?",new String[]{date},null,null,null);
        //Log.d("vsn", "no_of_periods:"+cursor.getCount()+" "+cursor.toString());
        //Log.d("vsn",String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        if(cursor!=null&&cursor.getCount()>0)
            return cursor.getString(1);
        return String.valueOf(0);
    }
    public void update(String date,int no)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("date",date);
        values.put("filled",String.valueOf(no));
        Log.d("vsn", String.valueOf(db.update("atten",values,"date=?",new String[]{date})));;
        db.close();
    }
    public boolean exists(String date)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query("atten",new String[]{"date","filled"},"date=?",new String[]{date},null,null,null);
        cursor.moveToFirst();
        return (cursor.getCount()>0);
    }
    /*public void show(String date)
    {
        //Toast.makeText(activity,"hello",Toast.LENGTH_SHORT).show();
        SQLiteDatabase db=getReadableDatabase();
        //Toast.makeText(activity,date,Toast.LENGTH_SHORT).show();
        Cursor cursor=db.query("atten",new String[]{"date","filled"},"date=?",new String[]{date},null,null,null);
        if(cursor!=null&&cursor.getCount()>0) {
            cursor.moveToFirst();
            //Log.d("vsn", String.valueOf(cursor.getCount()));
            //TextView textView = (TextView) activity.findViewById(R.id.textView);
            //textView.setText(cursor.toString());
            Toast.makeText(activity, cursor.getString(0) + " " + cursor.getString(1), Toast.LENGTH_SHORT).show();
        }
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " +"atten");
        // Create tables again
        onCreate(db);
    }
    public void delete()
    {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " +"atten");
        onCreate(db);
    }
    public ArrayList<String> show()
    {
        ArrayList<String> list = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "atten";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                list.add(cursor.getString(0)+" "+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }
    public int periods_attended()
    {
        int sum=0;
        String selectQuery = "SELECT  * FROM " + "atten";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sum+=Integer.parseInt(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return sum;
    }
}
