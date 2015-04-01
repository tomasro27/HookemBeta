package rodrigueztomas.com.hookembeta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    public static final Typeface MonseratBold(Context ctx){
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "Montserrat-Bold.ttf");
        return typeface;
    }
    public int one = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, ParseKeys.APPLICATION_ID, ParseKeys.CLIENT_KEY);
        ParseACL defaultACL = new ParseACL();

        ParseACL.setDefaultACL(defaultACL, true);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser != null)
        {
            if(savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, HookemFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }

        }
        else
        {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, LoginSignupFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
