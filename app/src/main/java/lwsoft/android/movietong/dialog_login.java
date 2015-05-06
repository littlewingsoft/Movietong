package lwsoft.android.movietong;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dialog_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dialog_login extends DialogFragment {
    private intro_login mParent;
    private View thisParentView;


    static protected dialog_login dialogFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dialog_login.
     */
    // TODO: Rename and change types and number of parameters
    public static dialog_login newInstance() {
        //String param1;
        //String param2;
        if( dialogFragment  == null ) {
            dialogFragment = new dialog_login();
        }
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return dialogFragment  ;
    }

    public dialog_login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        Button btn_signup=(Button)v.findViewById(R.id.login_button_ok);
        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et = (EditText) thisParentView.findViewById(R.id.login_editText_email);
                String email = et.getText().toString();

                et = (EditText) thisParentView.findViewById(R.id.login_editText_pw);
                String pw = et.getText().toString();
                Log.i("_tag", email + " : " + pw);
                dialogFragment.dismiss();

                //MainActivity.inst.showProgressdlg();
                //send http post
                mParent.send_login(email, pw);


            }
        });

        return v;
    }



}
