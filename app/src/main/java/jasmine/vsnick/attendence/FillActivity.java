package jasmine.vsnick.attendence;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FillActivity extends AppCompatActivity {

    DBHelper dbhelper;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        dbhelper=new DBHelper(FillActivity.this,"myDB",1 );
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!dbhelper.exists(date))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //Uncomment the below code to Set the message and title from the strings.xml file
            //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Add "+date+" to Attendence?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            addDate();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            finish();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("");
            alert.show();
        }
        Fill fragment = new Fill();
        Bundle bundle = new Bundle();
        bundle.putString("date",date);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.frame,fragment,"fill");
        fragmentTransaction.commit();
        String svc = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager
                = (NotificationManager)getSystemService(svc);
        notificationManager.cancelAll();
    }
    public void update(View view)
    {
        try {
            ListView listView = (ListView) findViewById(R.id.listview);
            String s="";
            View v;
            TextView t;
            CheckBox ch;
            for (int i = 0; i < listView.getCount(); i++) {
                v = listView.getChildAt(i);
                ch = (CheckBox) v.findViewById(R.id.rowCheck);
                t = (TextView) v.findViewById(R.id.period);
                if(t.getText().toString().equals("Free Period"))
                    s+="free ";
                else if(ch.isChecked())
                    s+="true ";
                else
                    s+="false ";
            }
            dbhelper.update(date,s);
            int f=0;
            s.trim();
            for(String i:s.split(" "))
                if(i.equals("free"))
                    f++;
            Log.d("vsn", "update: "+f);
            SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
            int total = Integer.parseInt(sharedPreferences.getString("TotalPeriods",null));
            if(total>=8)
                total=(total-8);
            else
                total = 0;
            total+=8-f;
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("TotalPeriods",String.valueOf(total));
            editor.commit();
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
        }
    }
    void addDate()
    {
        String filled="";
        for(int i=0;i<8;i++)
            filled+="false ";
        dbhelper.add(date,filled);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        int total = Integer.parseInt(sharedPreferences.getString("TotalPeriods",null));
        total+=8;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("TotalPeriods",String.valueOf(total));
        editor.commit();
    }
    public void cancel(View view)
    {
        finish();
    }
}
