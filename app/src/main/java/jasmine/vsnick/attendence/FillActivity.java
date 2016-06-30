package jasmine.vsnick.attendence;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
        if(dbhelper.exists(date)) {
            Fill fragment = new Fill();
            Bundle bundle = new Bundle();
            bundle.putString("date",date);
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.frame,fragment,"fill");
            fragmentTransaction.commit();
        }
        else
        {
            fragmentTransaction.add(R.id.frame,new Empty(),"empty");
            fragmentTransaction.commit();
        }
    }
    public void update(View view)
    {
        try {
            EditText editText = (EditText) findViewById(R.id.no_of_periods);
            int number = Integer.parseInt(editText.getText().toString());
            dbhelper.update(date, number);
            //Log.d("vsn", editText.getText().toString());
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
        }
    }
    public void addDate(View view)
    {
        dbhelper.add(date,0);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        int total = Integer.parseInt(sharedPreferences.getString("TotalPeriods",null));
        total+=8;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("TotalPeriods",String.valueOf(total));
        editor.commit();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag("empty");
        fragmentTransaction.remove(fragment);
        Fill frag = new Fill();
        Bundle bundle = new Bundle();
        bundle.putString("date",date);
        frag.setArguments(bundle);
        fragmentTransaction.add(R.id.frame,frag,"fill");
        fragmentTransaction.commit();
    }
    public void cancel(View view)
    {
        finish();
    }
}
