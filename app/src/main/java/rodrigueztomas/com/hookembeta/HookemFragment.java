package rodrigueztomas.com.hookembeta;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class HookemFragment extends Fragment {

    private ListView friendsListView;
    private Button plusButton;
    private EditText addFriendEditText;
    private Button logoutButton;

    private List<ParseObject> friends;


    public static HookemFragment newInstance() {
        HookemFragment fragment = new HookemFragment();
        return fragment;
    }

    public HookemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hookem, container, false);

        friendsListView = (ListView) v.findViewById(R.id.friends);





        plusButton = (Button) v.findViewById(R.id.plus);
        addFriendEditText = (EditText) v.findViewById(R.id.addFriend);
        logoutButton = (Button) v.findViewById(R.id.logout);

        plusButton.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        addFriendEditText.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        logoutButton.setTag(MainActivity.MonseratBold(getActivity().getApplicationContext()));



        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButton.setVisibility(View.GONE);
                addFriendEditText.setVisibility(View.VISIBLE);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, LoginSignupFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });


        addFriendEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {



            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(v.toString().length() == 0)
                    return false;
                else
                {
                    String friendName = addFriendEditText.getText().toString().toUpperCase();
                    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("username", friendName);

                    userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {

                            if (e == null && parseUser != null)
                            {
                                final ParseUser friend;
                                friend = parseUser;
                                final ParseUser currentUser = ParseUser.getCurrentUser();

                                ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequest");
                                query.whereEqualTo("fromUser", friend.getObjectId());
                                query.whereEqualTo("toUser", currentUser.getObjectId());

                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> results, ParseException e) {
                                        if (e == null) {
                                            if (results.isEmpty()){
                                                ParseObject friendRequest = new ParseObject("FriendRequest");
                                                friendRequest.put("fromUser", currentUser.getObjectId());
                                                friendRequest.put("toUser", friend.getObjectId());
                                                friendRequest.put("fromUsername", currentUser.getUsername());
                                                friendRequest.put("toUsername", friend.getUsername());

                                                friendRequest.put("status", "Pending");
                                                friendRequest.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e == null) {
                                                            Toast.makeText(getActivity().getApplicationContext(), "Friend request sent!", Toast.LENGTH_LONG).show();
                                                            updateFriends();
                                                            addFriendEditText.setText("");
                                                            addFriendEditText.setVisibility(View.GONE);
                                                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                            imm.hideSoftInputFromWindow(addFriendEditText.getWindowToken(), 0);
                                                            plusButton.setVisibility(View.VISIBLE);



                                                        } else if (e == null) {
                                                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                                                        }
                                                    }
                                                });


                                            }
                                        } else {
                                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                            } else if (parseUser == null && e == null) {
                                Log.d("friendRequest", "user null");
                                Toast.makeText(getActivity().getApplicationContext(), "No user found with that email address.", Toast.LENGTH_LONG).show();
                            } else if (e != null) {
                                Log.d("friendRequest", "parse exception: " + e.toString());
                                Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                    return true;
                }

            }
        });

        updateFriends();


        return v;
    }

    private void updateFriends()
    {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("FriendRequest");
        query1.whereEqualTo("toUser", ParseUser.getCurrentUser().getObjectId());

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("FriendRequest");
        query2.whereEqualTo("fromUser", ParseUser.getCurrentUser().getObjectId());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                // results of all the friend Requests

                 Log.d("HookemFragment", "Parse: " + results.size() + "  " + results.toString());

                friends = results;

                FriendsArrayAdapter adapter = new FriendsArrayAdapter(getActivity().getApplicationContext(),
                        getActivity().getLayoutInflater(), results);
                friendsListView.setAdapter(adapter);

                friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity().getApplicationContext(), "HOOKEM sent!", Toast.LENGTH_LONG).show();
                        Log.d("HookemFragment", "ONITEMCLICKLISTENER");


                    }
                });


                adapter.notifyDataSetChanged();
            }
        });



    }

}
