package rodrigueztomas.com.hookembeta;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginFragment extends Fragment {

    private EditText usernameLoginEditText;
    private EditText passcodeEditText;
    private Button loginButton;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    public LoginFragment() {
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        usernameLoginEditText = (EditText) v.findViewById(R.id.usernameLogin);
        passcodeEditText = (EditText) v.findViewById(R.id.passcodeLogin);
        loginButton = (Button) v.findViewById(R.id.taptologin);

        usernameLoginEditText.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        passcodeEditText.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        loginButton.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(usernameLoginEditText.getText().toString().toUpperCase(), passcodeEditText.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, HookemFragment.newInstance())
                                    .commit();
                        } else {

                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            // Signup failed. Look at the ParseException to see what happened.
                        }
                    }
                });
            }
        });


        return v;
    }
}
