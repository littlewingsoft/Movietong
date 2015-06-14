package lwsoft.android.movietong;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import static lwsoft.android.movietong.MainActivity.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class dialog_signup extends DialogFragment {
    intro_login mParent;
    private View thisParentView;
    //private  ProgressDialog mPd;
    static protected dialog_signup dialogFragment;
    private String tmpEmail;
    private String tmpPw;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch( msg.what ){
                    case 0: // success
                    {
                        //result 0 or 1
                        JSONObject jo = (JSONObject )msg.obj ;
                        int retVal = jo.getInt("retVal");
                        if( retVal == 1 ){

                            Log.i("tag_", "sign success");
                            dialogFragment.dismiss();
                            //login dialog show.
                            intro_login.inst.showdialog_login();

                            activity_manager.inst.mEmail = tmpEmail;
                            activity_manager.inst.mPw = tmpPw;
                            activity_manager.inst.saveAccount();
                            /*
                            mParent.goMain();

                            */
                            // required object id
                            //save file
                        }else{
                            TextView tv = (TextView)thisParentView.findViewById(R.id.signup_textView_email_comment);
                            tv.setText( "이미 존재하는 이메일입니다. " );
                            Log.i("tag_",  "sign fail ");
                        }

                    }
                    break;
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            //Log.d("tag_", "handler call:" + (String) msg.obj );
        }
    };


    static public dialog_signup  newInstance() {//intro_login _mParent
        if( dialogFragment == null )
        {
            dialogFragment = new dialog_signup();//_mParent
            return dialogFragment;
        }

        return dialogFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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

        ((TextView)thisParentView.findViewById(R.id.signup_textView_email_comment)).setText("");
        ((TextView)thisParentView.findViewById(R.id.signup_textView_pw_comment)).setText("");

        Button btn_signup=(Button)v.findViewById(R.id.dialog_button_signup_ok);
        btn_signup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText et_email = (EditText) thisParentView.findViewById(R.id.signup_editText_email);
                String email = et_email.getText().toString();

                EditText et_pw = (EditText) thisParentView.findViewById(R.id.signup_editText_pw);
                String pw = et_pw .getText().toString();

                EditText et_pw2 = (EditText) thisParentView.findViewById(R.id.signup_editText_pw2);
                String pw2 = et_pw2 .getText().toString();

                if(email.isEmpty())
                {
                    TextView tv = (TextView)thisParentView.findViewById(R.id.signup_textView_email_comment);
                    tv.setText( "이메일을 입력해 주세요" );
                    return;
                }

                if( pw.isEmpty() ){
                    TextView tv = (TextView)thisParentView.findViewById(R.id.signup_textView_pw_comment);
                    tv.setText( "비밀번호를 입력해 주세요" );
                    return;
                }


                if( (pw.compareTo(pw2) != 0) ||  pw2.isEmpty() )
                {
                    TextView tv = (TextView)thisParentView.findViewById(R.id.signup_textView_pw_comment);
                    tv.setText("비밀번호가 다릅니다. 정확히 입력해 주세요");
                    return;
                }

                httpsend_signup(email,pw);

            }
        });

        return v;
    }


    public void httpsend_signup( String email, String pw ){
        Log.i("tag_", "httpsend_signup" + email );
        //url_movie
        tmpEmail = email;
        tmpPw = pw;
        String url_const = "http://www.movietong.co.kr/Insert_Account.asp" ;
        String url = String.format("%s?accid=%s&pw=%s",url_const,email,pw );
        String[] params={ url , "0" };
        new httpUtil_get( this.getActivity(), mHandler).execute( params   );

    }


    }

