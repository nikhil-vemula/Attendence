package jasmine.vsnick.attendence;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getApplicationContext().deleteDatabase("myDB");
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        if(sharedPreferences.getString("TotalPeriods",null) == null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("TotalPeriods", "0");
            editor.commit();
        }
        if(sharedPreferences.getBoolean("new",true) == true)
        {
            Log.d("vsn", "onCreate:new");
            Intent myIntent = new Intent(MainActivity.this , NotifyIntentService.class);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),16,0,0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("new",false);
            editor.commit();
        }
        calendar= (CalendarView) findViewById(R.id.calender);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getApplicationContext(),FillActivity.class);
                month++;
                intent.putExtra("Date",dayOfMonth+"/"+month+"/"+year);
                startActivity(intent);
            }
        });
    }
    public void showAll(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ShowAll.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.atten);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        int t = Integer.parseInt(sharedPreferences.getString("TotalPeriods","0"));
        DBHelper dbHelper =new DBHelper(MainActivity.this,"myDB",1);
        int a = dbHelper.periods_attended();
        float atten=0;
        if(t!=0)
            atten=((float)a/t)*100;
        textView.setText("Attendence:"+(atten));
    }
}
