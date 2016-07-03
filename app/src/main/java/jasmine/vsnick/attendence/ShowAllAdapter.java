package jasmine.vsnick.attendence;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vsnick on 03-07-2016.
 */
public class ShowAllAdapter extends BaseAdapter {
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;
    public ShowAllAdapter(Context context,String[] data)
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
            vi = inflater.inflate(R.layout.litem, null);
        final TextView day = (TextView) vi.findViewById(R.id.text1);
        day.setText(data[position]);
        return vi;
    }
}

