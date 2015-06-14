package lwsoft.android.movietong;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class dialog_firstconnect extends DialogFragment {
    static dialog_firstconnect inst = null;
    // TODO: Rename and change types and number of parameters
    public static dialog_firstconnect newInstance() {
        if( inst  == null)
            inst = new dialog_firstconnect();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return inst;
    }

    public dialog_firstconnect() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().hide();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_firstconnect, container, false);
        Button btn_ok=(Button)v.findViewById(R.id.firstconnect_button_yes);
        btn_ok.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                inst.dismiss();
                MainActivity.inst.proc_inputprofile();
            }
        });

        Button btn_no=(Button)v.findViewById(R.id.firstconnect_button_no);
        btn_no.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                inst.dismiss();
                MainActivity.inst.proc_inputprofile();
            }
        });

        return v;
    }


}
