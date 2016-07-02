package jasmine.vsnick.attendence;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vsnick on 30-06-2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;
    public CustomAdapter(Context context,String[] data)
    {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        final TextView period = (TextView) vi.findViewById(R.id.period);
        final CheckBox checkBox = (CheckBox) vi.findViewById(R.id.rowCheck);
        period.setText("Period "+(position+1)+":");
        checkBox.setVisibility(View.VISIBLE);
        period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(period.getText().toString().equals("Period "+(position+1)+":"))
                {
                    period.setText("Free Period");
                    checkBox.setVisibility(View.INVISIBLE);
                }
                else {
                    period.setText("Period " + (position + 1) + ":");
                    checkBox.setVisibility(View.VISIBLE);
                }

            }
        });
        if(data[position].equals("free"))
        {
            period.setText("Free Period");
            checkBox.setVisibility(View.INVISIBLE);
        }
        if(data[position].equals("true")){
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
        return vi;
    }
}
