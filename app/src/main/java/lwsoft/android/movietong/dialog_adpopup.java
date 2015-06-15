package lwsoft.android.movietong;


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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dialog_adpopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dialog_adpopup extends DialogFragment {
    private View thisParentView;
    private Handler mHandler;
    static protected dialog_adpopup dialogFragment;


    // TODO: Rename and change types and number of parameters
    public static dialog_adpopup newInstance() {
        //String param1;
        //String param2;
        if( dialogFragment  == null ) {
            dialogFragment = new dialog_adpopup();
        }
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return dialogFragment  ;
    }

    public dialog_adpopup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mHandler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    JSONObject jo = (JSONObject) msg.obj;
                    Log.i("tag_", jo.toString());
                    int adType = jo.getInt("adType");

                    if (adType == 1) { // add count 2
                        //show 2
                        //displayadimg01 ,displayadlink01

                        thisParentView.findViewById(R.id.linear_adtype_one).setVisibility(View.VISIBLE);
                        thisParentView.findViewById(R.id.linear_adtype_two).setVisibility(View.GONE);

                        NetworkImageView nv0 =(NetworkImageView)
                                thisParentView.findViewById(R.id.adpopup_imageView_one_0);

                        ImageLoader imageLoader = util_volley.getInstance().getImageLoader();

                        String displayadimg01= jo.getString("displayadimg01");
                        String url1 = "http://www.movietong.co.kr" + displayadimg01;
                        nv0.setImageUrl( url1, imageLoader);

                        NetworkImageView nv1 =(NetworkImageView)
                                thisParentView.findViewById(R.id.adpopup_imageView_one_1);

                        String displayadimg02= jo.getString("displayadimg02");
                        String url2 = "http://www.movietong.co.kr" + displayadimg02;
                        nv1.setImageUrl( url2, imageLoader);

                        //displayadimg02, displayadlink02

                    } else if (adType == 2) { // add count 2
                        thisParentView.findViewById(R.id.linear_adtype_one).setVisibility(View.GONE);
                        thisParentView.findViewById(R.id.linear_adtype_two).setVisibility(View.VISIBLE);

                        NetworkImageView nv0 =(NetworkImageView)
                                thisParentView.findViewById(R.id.adpopup_imageView_two );

                        ImageLoader imageLoader = util_volley.getInstance().getImageLoader();

                        String displayadimg01= jo.getString("displayadimg01");
                        String url1 = "http://www.movietong.co.kr" + displayadimg01;
                        nv0.setImageUrl(url1, imageLoader);
                        //displayadimg01
                        //displayadlink01
                    }
                } catch (Exception e) {
                    Log.i("tag_", e.getMessage());
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getDialog().hide();// setTitle("My Dialog Title");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_adpopup, container, false);

        thisParentView = v;

        Button btn_signup=(Button)v.findViewById(R.id.adpopup_button_ok);
        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.dismiss();
            }
        });

        CheckBox cb = (CheckBox)v.findViewById(R.id.adpopup_checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (buttonView.getId() == R.id.adpopup_checkBox)
                {
                    if (isChecked)
                    {

                    }
                    else
                    {

                    }

                }
                }
        } );

        send_req_ad();
        return v;
    }

    private void resetlayer(){

    }

    private void proc_popup(){
        resetlayer();
    }

    private void send_req_ad(){

        String url = "http://www.movietong.co.kr/Select_MovieTongPopup.asp" ;
        String[] params={url , "0" };
        new httpUtil_get( this.getActivity(), mHandler).execute(params);
    }


}
