package jasmine.vsnick.attendence;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Empty extends Fragment {

    DBHelper dbHelper;
    public Empty() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper= new DBHelper(getActivity().getApplicationContext(),"myDB",1);
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }
}
