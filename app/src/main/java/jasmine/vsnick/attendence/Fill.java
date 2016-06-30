package jasmine.vsnick.attendence;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        // Inflate the layout for this fragment
        date = getArguments().getString("date");
        return inflater.inflate(R.layout.fragment_fill, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("vsn", "onStart: ");
        DBHelper dbHelper=new DBHelper(getActivity().getApplicationContext(),"myDB",1);
        TextView textView= (TextView) getActivity().findViewById(R.id.date);
        textView.setText(date);
        EditText editText = (EditText) getActivity().findViewById(R.id.no_of_periods);
        editText.setText(dbHelper.no_of_periods(date));
    }
}
