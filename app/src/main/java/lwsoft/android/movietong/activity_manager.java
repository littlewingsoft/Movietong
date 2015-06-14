package lwsoft.android.movietong;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class activity_manager extends ActionBarActivity {

    public String mEmail="_none_";
    public String mPw="_none_";
    public String mObjectID="_none_";
    final String pref_mEmail = "mEmail";
    final String pref_mPw = "mPw";
    public static activity_manager inst;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        goLogin();
        activity_manager.inst = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_manager, menu);
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

    public void goLogin(){
        Intent it = new Intent( getApplicationContext(), intro_login.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
    public void gohome( Activity ac ){

    }

    public boolean hasAccount(){
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        if( pref.contains("mEmail") )
            return true;

        return false;
    }

    public void restoreAccount(){
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        mEmail = pref.getString(pref_mEmail, "");
        mPw = pref.getString(pref_mPw, "");
    }

    public void saveAccount(){
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(pref_mEmail, mEmail);
        editor.putString(pref_mPw, mPw);
        editor.commit();
    }
}
