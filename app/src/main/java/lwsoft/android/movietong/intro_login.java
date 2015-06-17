package lwsoft.android.movietong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;


public class intro_login extends ActionBarActivity {

    private ProgressDialog mPd;
    public static intro_login inst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_login);
        getSupportActionBar().hide();
        setbtn();
        //testurl();
        inst = this;
    }

    protected void showdialog_signup(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

//        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            DialogFragment df = (DialogFragment)prev;
//            df.dismiss();
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);

        DialogFragment newFragment = dialog_signup.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
    }

    protected  void showdialog_login(){
     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            DialogFragment df = (DialogFragment)prev;
//            df.dismiss();
//            ft.remove(prev);
//        }
        //ft.addToBackStack(null);

     DialogFragment newFragment = dialog_login.newInstance();
     newFragment.setShowsDialog(true);
     newFragment.show(ft, "dialog");
    }

    protected void showdialog_quit(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment)prev;
            df.dismiss();
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = dialog_quit.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
    }

    protected  void setbtn(){
        Button btn = (Button)findViewById(R.id.button_signup);
        btn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        showdialog_signup();
                    }

                });

        btn = (Button)findViewById(R.id.button_login );
        btn.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        showdialog_login();
                    }

                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro_login, menu);
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

    public  void goMain(){

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                //You can perform additional check to remove some (not all) fragments:
                if (f instanceof DialogFragment) {
                    FragmentManager manager = f.getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove( f);
                    trans.commit();
                }
            }
        }

        fragments = getSupportFragmentManager().getFragments();

        Intent it = new Intent( this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(it);
        finish();
    }

    void send_signup( String email, String pw, dialog_signup handle_dlg ){

        //http send

        if( mPd != null)
            mPd.dismiss();

        mPd = ProgressDialog.show( this, "", "wait a minute",true);
        //send http post
        //callback
        new Handler().postDelayed(new Runnable() { // new Handler and Runnable
            @Override
            public void run() {
                mPd.dismiss();
                goMain();
            }
        }, 1000);


    }

    void send_login( String email, String pw )
    {
        if (mPd != null)
            mPd.dismiss();
            mPd = ProgressDialog.show( this, "", "wait a minute", true);
        //send http

        new Handler().postDelayed(new Runnable() { // new Handler and Runnable
            @Override
            public void run() {
                mPd.dismiss();
                goMain();
            }
        } , 1000);
    }

    public void onSignup(View v){
        Log.i("tag_", "hey: " );
    }

    @Override
    public void onBackPressed() {
        showdialog_quit();
    }
}
