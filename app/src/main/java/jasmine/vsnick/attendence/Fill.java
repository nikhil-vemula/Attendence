package jasmine.vsnick.attendence;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fill extends Fragment {

    String date;
    public Fill() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        date = getArguments().getString("date");
        return inflater.inflate(R.layout.fragment_fill, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        DBHelper dbHelper=new DBHelper(getActivity().getApplicationContext(),"myDB",1);
        ListView listView = (ListView) getActivity().findViewById(R.id.listview);
        String s = dbHelper.no_of_periods(date);
        s.trim();
        String[] periods = s.split(" ");
        CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(),periods);
        listView.setAdapter(customAdapter);
    }
}
