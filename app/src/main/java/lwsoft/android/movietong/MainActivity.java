package lwsoft.android.movietong;

import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


//ActionBarActivity
public class MainActivity extends FragmentActivity {
    private fragment_event mFr_event;
    //private fragment_currentmovie mFr_currentmovie;
    public static MainActivity inst;
    //final int DIALOG_GAME_RESTART = 100;
    //Dialog dialog;
    public  void doResetAllBtn(){
        Button tabbtn = (Button)findViewById(R.id.tab_button_0);
        Button tabbtn1 = (Button)findViewById(R.id.tab_button_1);
        Button tabbtn2 = (Button)findViewById(R.id.tab_button_2);
        Button tabbtn3 = (Button)findViewById(R.id.tab_button_3);

        tabbtn.setSelected(false);
        tabbtn1.setSelected(false);
        tabbtn2.setSelected(false);
        tabbtn3.setSelected(false);
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

        Button tabbtn = (Button)findViewById(R.id.tab_button_0);
        tabbtn.setOnClickListener(mClickListener);

        Button tabbtn1 = (Button)findViewById(R.id.tab_button_1);
        tabbtn1.setOnClickListener(mClickListener);

        Button tabbtn2 = (Button)findViewById(R.id.tab_button_2);
        tabbtn2.setOnClickListener(mClickListener);

        Button tabbtn3 = (Button)findViewById(R.id.tab_button_3);
        tabbtn3.setOnClickListener(mClickListener);

        //must go on
        proc_firstconnect();
        changeFragment(R.id.tab_button_0); // R.id presss
    }

    private void changeFragment(int id){

        Fragment fr = getSupportFragmentManager().findFragmentByTag("mainFrag") ;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if( fr != null)
            ft.remove( fr );
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch(id){
            case R.id.tab_button_0:
            {
                fr = fragment_currentmovie.newInstance(0);
                //if( mFr_currentmovie == null)
            }break;

            case R.id.tab_button_1:{
                fr = fragment_currentmovie.newInstance(1);

            }break;

            case R.id.tab_button_2:{
                fr = new fragment_event();
            }break;

            case R.id.tab_button_3:{
//                if( mFr_event == null )
                fr = new fragment_event();
                //ft.replace(R.id.mainlayout_fragment, fr,"mainFrag");
                //ft.replace(R.id.mainlayout_fragment, mFr_event,"mainFrag");
            }
            break;
        }
        //ft.addToBackStack(null);
        ft.replace(R.id.mainlayout_fragment, fr,"mainFrag");
        ft.commit();

    }
    Button.OnClickListener mClickListener =  new View.OnClickListener(){
        public void onClick(View v){
            Log.i("tag_", "id: " + v.getId());
            MainActivity.inst.doResetAllBtn();
            v.setSelected(true);
            changeFragment(v.getId());
        }
    };

    int mStackLevel = 0;
    //test??
    void showYesNoDialog() {
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
            showFirstConnectDlg();


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

    public void proc_adpopup(){
        SharedPreferences pref = getSharedPreferences("adpopup", MODE_PRIVATE);
        // if 24 hour check
        showdialog_adpopup();
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

    public void showdialog_adpopup(){
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

