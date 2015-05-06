package lwsoft.android.movietong;


import android.app.Activity;
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
    intro_login mParent;
    protected View thisParentView;
    protected ProgressDialog mPd;
    static protected dialog_signup dialogFragment;
    static public dialog_signup  newInstance() {//intro_login _mParent
        if( dialogFragment == null )
        {
            dialogFragment = new dialog_signup();//_mParent
            /*
            Bundle args = new Bundle();
            args.(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            */
            return dialogFragment;
        }

        return dialogFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //intro_login a = (intro_login )getArguments().get("parentActivity");
            //mParent = a;

            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
/*
    public dialog_signup(intro_login m){
        //mParent = m;
    }*/

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
        mParent = (intro_login)getActivity();
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
                mParent.send_signup( email,pw );


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




    }

