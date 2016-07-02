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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "atten"+ "(" + "date"+ " TEXT PRIMARY KEY," + "filled" + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public void add(String date,String filled)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("date",date);
        values.put("filled",filled);
        db.insert("atten",null,values);
        db.close();
    }
    public String no_of_periods(String date)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query("atten",new String[]{"date","filled"},"date=?",new String[]{date},null,null,null);
        //Log.d("vsn", "no_of_periods:"+cursor.getCount()+" "+cursor.toString());
        //Log.d("vsn",String.valueOf(cursor.getCount()));
        String filled="";
        for(int i=0;i<8;i++)
            filled+="false ";
        cursor.moveToFirst();
        if(cursor!=null&&cursor.getCount()>0)
            return cursor.getString(1);
        return filled;
    }
    public void update(String date,String periods)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("date",date);
        values.put("filled",periods);
        db.update("atten",values,"date=?",new String[]{date});
        db.close();
    }
    public void delete(String date)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete("atten","date=?",new String[]{date});
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
    public ArrayList<String> show()
    {
        ArrayList<String> list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + "atten";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public int periods_attended()
    {
        String selectQuery = "SELECT  * FROM " + "atten";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String s;
        int sum = 0;
        if (cursor.moveToFirst()) {
            do {
                s = cursor.getString(1);
                s.trim();
                for(String i : s.split(" "))
                {
                    if(i.equals("true"))
                        sum+=1;
                }

            } while (cursor.moveToNext());
        }
        return sum;
    }
}
