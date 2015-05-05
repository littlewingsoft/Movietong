package lwsoft.android.movietong;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static lwsoft.android.movietong.MainActivity.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class dialog_signup extends DialogFragment {
    protected View thisParentView;
    protected ProgressDialog mPd;
    static protected dialog_signup dialogFragment;
    static public dialog_signup  newInstance() {
        if( dialogFragment == null )
        {
            dialogFragment = new dialog_signup();
            return dialogFragment;
        }

        return dialogFragment;

    }
/*
    public void showDialog() {
        // Create the fragment and show it as a dialog.
        DialogFragment newFragment = dialog_signup.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(getFragmentManager(), "dialog" );
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().hide();// setTitle("My Dialog Title");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_signup, container, false);
        thisParentView = v;

        Button btn_signup=(Button)v.findViewById(R.id.dialog_button_signup_ok);
        btn_signup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText et = (EditText) thisParentView.findViewById(R.id.signup_editText_email);
                String email = et.getText().toString();

                et = (EditText) thisParentView.findViewById(R.id.signup_editText_pw);
                String pw = et.getText().toString();
                Log.i("_tag", email + " : " + pw);
                dialogFragment.dismiss();

                //MainActivity.inst.showProgressdlg();
                //send http post
                send_signup( email,pw );
                if( mPd != null)
                    mPd.dismiss();

                mPd = ProgressDialog.show( dialogFragment.getActivity() ,
                        "",
                        "wait a minute",true);

            }
        });


        //TextView tv = v.findViewById(R.id.textfield_);

        /*
        View tv = v.findViewById(R.id.dlg_text);
        ((TextView)tv).setText("This is an instance of MyDialogFragment");

        WebView wv =(WebView ) v.findViewById(R.id.webView_dlg);
        wv.loadUrl("http://m.naver.com");
        */

        //setCancelable();
        return v;
    }

    void send_signup( String email, String pw ){

        //send http post
        //callback
        new Handler().postDelayed(new Runnable() { // new Handler and Runnable
            @Override
            public void run() {
                mPd.dismiss();
                MainActivity.inst.gohome();


            }}

            ,1000);


        }


    }

