package lwsoft.android.movietong;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dialog_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dialog_login extends DialogFragment {
    private intro_login mParent;
    //private ProgressDialog mPd;
    private View thisParentView;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                JSONObject jo = (JSONObject)msg.obj;
                Log.i("tag_", jo.toString());
                int retVal = jo.getInt("retVal");

                if( retVal == 1){ // success
                    activity_manager.inst.mObjectID = jo.getString("account_objectid");
                    activity_manager.inst.mEmail = jo.getString("account_id");
                    //mPd.dismiss();
                    activity_manager.inst.saveAccount();
                    dismiss();
                    mParent.goMain();

                }
                else //fail
                {
                    ((TextView) thisParentView.findViewById(R.id.login_textView_email_comment)).setText("아이디가 존재하지 않거나 비밀번호가 틀립니다.");
                }

            }catch (Exception e){
                Log.i( "tag_", e.getMessage() );
            }
        }
    };

    static protected dialog_login dialogFragment;
    // TODO: Rename and change types and number of parameters
    public static dialog_login newInstance() {

        if( dialogFragment  == null ) {
            dialogFragment = new dialog_login();
        }
        return dialogFragment  ;
    }

    public dialog_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mParent = (intro_login)getActivity();
        getDialog().hide();// setTitle("My Dialog Title");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_login, container, false);

        thisParentView = v;
        if( activity_manager.inst.hasAccount() )
        {
            activity_manager.inst.restoreAccount();
            ((EditText) thisParentView.findViewById(R.id.login_editText_email)).setText(activity_manager.inst.mEmail);
            ((EditText) thisParentView.findViewById(R.id.login_editText_pw)).setText(activity_manager.inst.mPw);
        }else {
            ((EditText) thisParentView.findViewById(R.id.login_editText_email)).setText("");
            ((EditText) thisParentView.findViewById(R.id.login_editText_pw)).setText("");
        }
        ((TextView) thisParentView.findViewById(R.id.login_textView_email_comment)).setText("");
        ((TextView) thisParentView.findViewById(R.id.login_textView_pw_comment)).setText("");

        Button btn_signup=(Button)v.findViewById(R.id.login_button_signup);
        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mParent.showdialog_signup();
            }
        });

        Button btn_login=(Button)v.findViewById(R.id.login_button_ok);
        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et = (EditText) thisParentView.findViewById(R.id.login_editText_email);
                String email = et.getText().toString();

                et = (EditText) thisParentView.findViewById(R.id.login_editText_pw);
                String pw = et.getText().toString();
                //Log.i("_tag", email + " : " + pw);

                if (email.isEmpty()) {
                    ((TextView) thisParentView.findViewById(R.id.login_textView_email_comment)).setText("이메일을 입력해 주세요");
                    return;
                }

                if (pw.isEmpty()) {
                    ((TextView) thisParentView.findViewById(R.id.login_textView_pw_comment)).setText("비밀번호를 입력해 주세요");
                    return;
                }

                send_login(email, pw);

                //MainActivity.inst.showProgressdlg();
                //send http post
                //mParent.send_login(email, pw);


            }
        });

        return v;
    }

    void send_login(String e, String p ){

        //mPd = ProgressDialog.show( getActivity().getBaseContext() ,"wait" ,"wait wait" );
        activity_manager.inst.mEmail = e;
        activity_manager.inst.mPw = p;
        Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;
        nameValue.add(new BasicNameValuePair("accid", e)) ;
        nameValue.add( new BasicNameValuePair( "pw", p) ) ;
        String url = "http://www.movietong.co.kr/Select_Login.asp"+ "?" + URLEncodedUtils.format(nameValue, null) ;
        String[] params={url , "0" };
        new httpUtil_get( this.getActivity(), mHandler).execute(params);
    }

    void send_accinfo(String account_objectid ){
        Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;
        nameValue.add(new BasicNameValuePair("account_objectid", account_objectid)) ;//"5a80e08c-5b97-4936-8694-cc6912f2e00c"
        String url = "http://www.movietong.co.kr/Select_AccountInfo.asp"+ "?" + URLEncodedUtils.format(nameValue, null) ;
        String[] params={url};
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
    }
    public void onSignup(View v){
        Log.i("tag_", "hey: ");
    }

}
