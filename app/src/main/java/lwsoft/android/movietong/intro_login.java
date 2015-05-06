package lwsoft.android.movietong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import java.util.Vector;


public class intro_login extends ActionBarActivity {

    private ProgressDialog mPd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_login);
        getSupportActionBar().hide();
        setbtn();
        testurl();
    }

    private class httpTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String result="";
            try
            {
                //http://121.125.68.82/Select_AccountInfo.asp ? account_objectid=
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;
                nameValue.add(new BasicNameValuePair("account_objectid", "5a80e08c-5b97-4936-8694-cc6912f2e00c")) ;
                //nameValue.add( new BasicNameValuePair( "user_pwd", user_pwd ) ) ;

                String my_url = "http://121.125.68.82/Select_AccountInfo.asp"+ "?" + URLEncodedUtils.format(nameValue, null) ;
                HttpGet request = new HttpGet( my_url ) ;
                HttpClient client = new DefaultHttpClient() ;
                ResponseHandler<String> reshandler = new BasicResponseHandler() ;

                result = client.execute(request, reshandler);
                Log.i("tag_", result);
                JSONObject jo=new JSONObject(result);

                Log.i("tag_", "usernickname: "+ jo.getString("usernickname") );
                Log.i("tag_", "userpoint: "+ jo.getInt("userpoint"));
                Log.i("tag_", "usersex: "+ jo.getString("usersex"));
                Log.i("tag_", "userage: "+ jo.getInt("userage"));
                Log.i("tag_", "userarea: "+ jo.getString("userarea"));
                Log.i("tag_", "userrecommender: "+ jo.getString("userrecommender"));

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result != null){
                Log.d("ASYNC", "result = " + result);
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }
    void testurl()  {
        new httpTask().execute(null,null,null);
    }
    protected void showdialog_signup(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = dialog_signup.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
    }

    protected  void showdialog_login(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = dialog_login.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
    }

    protected void showdialog_quit(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
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
        Intent it = new Intent( this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(it);
        finish();
    }

    void send_signup( String email, String pw ){

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
        }     , 1000);


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

    @Override
    public void onBackPressed() {
        showdialog_quit();
    }
}
