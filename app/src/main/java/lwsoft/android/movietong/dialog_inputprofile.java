package lwsoft.android.movietong;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class dialog_inputprofile extends DialogFragment {
    static dialog_inputprofile inst = null;

    public static dialog_inputprofile newInstance() {
        if( inst == null )
            inst= new dialog_inputprofile();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return inst;
    }

    public dialog_inputprofile() {
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
        View v = inflater.inflate(R.layout.dialog_inputprofile, container, false);
        Button btn_ok=(Button)v.findViewById(R.id.inputprofile_button_yes);
        btn_ok.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                inst.dismiss();
                //show profile dialog
                //write profile save.
                Toast.makeText(v.getContext(),"profile open!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_no=(Button)v.findViewById(R.id.inputprofile_button_no);
        btn_no.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                inst.dismiss();
                MainActivity.inst.proc_adpopup();
            }
        });

        return v;
    }


}
