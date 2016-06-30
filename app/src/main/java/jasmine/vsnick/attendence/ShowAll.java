package jasmine.vsnick.attendence;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity {

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        dbHelper=new DBHelper(ShowAll.this,"myDB",1 );
        SharedPreferences preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        TextView textView = (TextView) findViewById(R.id.total);
        textView.setText("Total:"+preferences.getString("TotalPeriods",""));
        ArrayList<String> list = dbHelper.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
