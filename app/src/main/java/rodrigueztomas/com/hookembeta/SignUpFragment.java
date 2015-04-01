package rodrigueztomas.com.hookembeta;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpFragment extends Fragment {


    private EditText usernameEditText;
    private EditText passcodeEditText;
    private Button tapToSignUpButton;



    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();

        return fragment;
    }

    public SignUpFragment() {
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
        View v =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        usernameEditText = (EditText) v.findViewById(R.id.username);
        passcodeEditText = (EditText) v.findViewById(R.id.passcode);
        tapToSignUpButton = (Button) v.findViewById(R.id.taptosignup);

        usernameEditText.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        passcodeEditText.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        tapToSignUpButton.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));

        tapToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = usernameEditText.getText().toString().toUpperCase();
//                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(usernameString).matches())
//                {
//                    etEmail.setError("Invalid etEmail address.");
//                    return;
//                }
                String password = passcodeEditText.getText().toString();

                if (password.length() < 6) {
                    passcodeEditText.setError("Password must be at least 6 characters long.");
                    return;
                }

                if (usernameEditText.getText().toString().length() == 0) {
                    usernameEditText.setError("Username can't be empty.");
                    return;
                }

                ParseUser user = new ParseUser();
                user.setUsername(usernameString.toUpperCase());
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, HookemFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();

                        } else {

                            Toast.makeText(getActivity().getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                    }
                });
            }
        });


        return v;
    }


}
