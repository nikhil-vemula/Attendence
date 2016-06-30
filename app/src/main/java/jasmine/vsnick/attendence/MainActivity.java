package jasmine.vsnick.attendence;

import android.content.Intent;
import android.content.SharedPreferences;
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
        getApplicationContext().deleteDatabase("myDB");
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        //if(sharedPreferences.getString("TotalPeriods",null) == null)
        if(true){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("TotalPeriods", "0");
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
        Log.d("vsn", "onResume: "+t+" "+a);
        float atten=0;
        if(t!=0)
            atten=((float)a/t)*100;
        Log.d("vsn", "onResume: "+String.valueOf(atten));
        textView.setText("Attendence:"+(atten));
    }
}
