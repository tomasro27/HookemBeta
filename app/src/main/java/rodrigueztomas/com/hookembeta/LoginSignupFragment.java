package rodrigueztomas.com.hookembeta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class LoginSignupFragment extends Fragment {

    private Button loginButton;
    private Button signupButton;

    public static LoginSignupFragment newInstance() {
        LoginSignupFragment fragment = new LoginSignupFragment();

        return fragment;
    }

    public LoginSignupFragment() {
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
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        loginButton = (Button) v.findViewById(R.id.login);
        loginButton.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));
        signupButton = (Button) v.findViewById(R.id.signup);
        signupButton.setTypeface(MainActivity.MonseratBold(getActivity().getApplicationContext()));

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, SignUpFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, LoginFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });




        return v;
    }





}
