package rodrigueztomas.com.hookembeta;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by tomasrodriguez on 3/31/15.
 */
public class FriendsArrayAdapter extends BaseAdapter {

    private static class ViewHolder{
        private TextView friendNameButton;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_row_friends, null);
            viewHolder.friendNameButton = (TextView) convertView.findViewById(R.id.friendsItemRow);

            String from = (String) friends.get(position).get("fromUsername");
            String to = (String) friends.get(position).get("toUsername");
            String friendName = "";

            if(ParseUser.getCurrentUser().getUsername().equals(from))
            {
                friendName = to;
            }
            else
            {
                friendName = from;
            }
            viewHolder.friendNameButton.setText(friendName);
            viewHolder.friendNameButton.setTypeface(MainActivity.MonseratBold(context));


            switch(position%5)
            {
                case 0:
                    viewHolder.friendNameButton.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
                    break;
                case 1:
                    viewHolder.friendNameButton.setBackgroundColor(context.getResources().getColor(R.color.blue));
                    break;
                case 2:
                    viewHolder.friendNameButton.setBackgroundColor(context.getResources().getColor(R.color.orange));
                    break;
                case 3:
                    viewHolder.friendNameButton.setBackgroundColor(context.getResources().getColor(R.color.light_orange));
                    break;
                case 4:
                    viewHolder.friendNameButton.setBackgroundColor(context.getResources().getColor(R.color.dark_orange));
                    break;
            }



            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }
}
