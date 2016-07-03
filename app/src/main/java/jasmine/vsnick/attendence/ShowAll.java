package jasmine.vsnick.attendence;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity {

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper=new DBHelper(ShowAll.this,"myDB",1 );
        Button button = (Button) findViewById(R.id.deleteAll);
        if(dbHelper.show().size() > 0)
            button.setVisibility(View.VISIBLE);
        else
            button.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        TextView textView = (TextView) findViewById(R.id.total);
        textView.setText("Total Periods Conducted:"+preferences.getString("TotalPeriods",""));
        textView = (TextView) findViewById(R.id.attended);
        textView.setText("Total Periods Attended:"+dbHelper.periods_attended());
        ArrayList<String> list = dbHelper.show();
        String[] s = list.toArray(new String[list.size()]);
        ShowAllAdapter adapter = new ShowAllAdapter(getApplicationContext(),s);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        final Context context =this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView1 = (TextView) view.findViewById(R.id.text1);
                String date = textView1.getText().toString();
                Intent intent = new Intent(getApplicationContext(),FillActivity.class);
                intent.putExtra("Date",date);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView1 = (TextView) findViewById(R.id.text1);
                final String date = textView1.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Delete "+date+" from Attendence?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                DBHelper  dbhelper = new DBHelper(ShowAll.this,"myDB",1);
                                dbhelper.delete(date);
                                SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
                                int total = Integer.parseInt(sharedPreferences.getString("TotalPeriods",null));
                                if(total>8)
                                    total=(total-8);
                                else
                                    total = 0;
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("TotalPeriods",String.valueOf(total));
                                editor.commit();
                                onResume();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                onResume();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("");
                alert.show();
                return true;
            }
        });
    }
    public void deleteTable(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Delete All?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.delete();
                        onResume();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("");
        alert.show();
    }
}
