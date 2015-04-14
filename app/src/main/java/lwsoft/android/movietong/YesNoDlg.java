package lwsoft.android.movietong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by gilbert on 2015-04-13.
 */
public  class YesNoDlg extends DialogFragment {

    static YesNoDlg  newInstance() {
        return new YesNoDlg();
    }

    public void showDialog() {
        // Create the fragment and show it as a dialog.
        DialogFragment newFragment = YesNoDlg.newInstance();
        newFragment.setShowsDialog(true);
        newFragment.show(getFragmentManager(), "dialog" );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("My Dialog Title");

        View v = inflater.inflate(R.layout.custom_dialog, container, false);

        View tv = v.findViewById(R.id.dlg_text);
        ((TextView)tv).setText("This is an instance of MyDialogFragment");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        WebView wv =(WebView ) v.findViewById(R.id.webView_dlg);
        wv.loadUrl("http://m.naver.com");
        //setCancelable();
        return v;
    }
}

