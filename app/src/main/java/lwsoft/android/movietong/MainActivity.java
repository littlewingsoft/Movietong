package lwsoft.android.movietong;

import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

//ActionBarActivity
public class MainActivity extends FragmentActivity {


    public static MainActivity inst;
    //final int DIALOG_GAME_RESTART = 100;
    //Dialog dialog;
    public  void doResetAllBtn(){
        Button btn = (Button)findViewById(R.id.tab_button_0);
        Button btn1 = (Button)findViewById(R.id.tab_button_1);
        Button btn2 = (Button)findViewById(R.id.tab_button_2);
        Button btn3 = (Button)findViewById(R.id.tab_button_3);

        btn.setSelected(false);
        btn1.setSelected(false);
        btn2.setSelected(false);
        btn3.setSelected(false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        //showDialog();
        inst = this;

        Button btn = (Button)findViewById(R.id.tab_button_0);
        btn.setOnClickListener(mClickListener);

        Button btn1 = (Button)findViewById(R.id.tab_button_1);
        btn1.setOnClickListener(mClickListener);

        Button btn2 = (Button)findViewById(R.id.tab_button_2);
        btn2.setOnClickListener(mClickListener);

        Button btn3 = (Button)findViewById(R.id.tab_button_3);
        btn3.setOnClickListener(mClickListener);

        //must go on
        proc_firstconnect();

    }

    Button.OnClickListener mClickListener =  new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.inst.doResetAllBtn();
            v.setSelected(true);
        }
    };

    int mStackLevel = 0;
    //test??
    void showDialog() {
        mStackLevel++;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = YesNoDlg.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
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

    public void proc_firstconnect(){
        SharedPreferences pref = getSharedPreferences("inputprofile", MODE_PRIVATE);
        if( pref.contains("SecondConnect") == false )
        //showFirstConnectDlg();

        showdialog_adpopup();
    }

    public void proc_inputprofile(){
        SharedPreferences pref = getSharedPreferences("inputprofile", MODE_PRIVATE);

        //if input profile
        showInputProfileDlg();

        //SharedPreferences.Editor editor = pref.edit();
//        editor.putString(pref_mEmail, mEmail);
 //       editor.putString(pref_mPw, mPw);
        //editor.commit();
    }

    public void showFirstConnectDlg(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        //ft.commit();

        DialogFragment newFragment = dialog_firstconnect.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");
    }

    public void showInputProfileDlg(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        //ft.addToBackStack(null);


        Fragment d = getSupportFragmentManager().findFragmentByTag("dialog");

        DialogFragment newFragment = dialog_inputprofile.newInstance();
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

    protected void showdialog_adpopup(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        //ft.addToBackStack(null);

        DialogFragment newFragment = dialog_adpopup.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(ft, "dialog");

    }


    @Override
    public void onBackPressed() {
        showdialog_quit();
    }



}

