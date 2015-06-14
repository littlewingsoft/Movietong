package lwsoft.android.movietong;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Created by gilbert on 2015-05-25.
 */

public class httpUtil_get extends AsyncTask<String,Void,JSONObject> {
    private Context mCon;
    private Handler mTargetHandler;
    private int msgId = 0;

    public httpUtil_get (Context con, Handler h){
        mTargetHandler= h;
        mPd = null;
        mCon = con;

    }
    private ProgressDialog mPd;
    @Override
    protected void onPreExecute(){
        mPd = ProgressDialog.show( mCon, "wait", "request.." );
    }
    @Override
    protected JSONObject doInBackground(String ... params) {
        JSONObject jo=null;

        try
        {

            String my_url = params[0];
            msgId = Integer.parseInt(params[1]);
            HttpGet request = new HttpGet( my_url ) ;
            HttpClient client = new DefaultHttpClient() ;
            ResponseHandler<String> reshandler = new BasicResponseHandler() ;
            String jsonData = client.execute(request, reshandler);
            jo=new JSONObject(jsonData);
            Log.i("tag_", "send " + my_url);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jo;
    }

    @Override
    protected void onPostExecute(JSONObject jo) {
        super.onPostExecute(jo);
        if(jo!= null){
            //Log.i("tag_", "onPostExcute:  " + jo.toString()  );
            Message msg = mTargetHandler.obtainMessage();
            msg.what = msgId ;
            msg.obj = jo;
            mTargetHandler.sendMessage(msg);
            mPd.dismiss();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}