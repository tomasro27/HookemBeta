package rodrigueztomas.com.hookembeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by tomasrodriguez on 3/31/15.
 */
public class FriendsArrayAdapter extends BaseAdapter {

    private static class ViewHolder{
        private Button friendNameButton;
    }

    private Context context;
    private LayoutInflater inflater;
    private List<ParseObject> friends;

    public FriendsArrayAdapter(Context context, LayoutInflater inflater, List<ParseObject> friends) {
        this.context = context;
        this.inflater = inflater;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_row_friends, null);
            viewHolder.friendNameButton = (Button) convertView.findViewById(R.id.friendsItemRow);
            viewHolder.friendNameButton.setText(friends.get(position).toString());
            viewHolder.friendNameButton.setTypeface(MainActivity.MonseratBold(context));
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.friendNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: PUSH NOTIFICATION
            }
        });




        return convertView;
    }
}
