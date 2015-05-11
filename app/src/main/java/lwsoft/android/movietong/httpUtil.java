package lwsoft.android.movietong;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * Created by gilbert on 2015-05-07.
 */
public class httpUtil {
    protected static httpUtil inst;
    public static httpUtil getinst(){
        if(inst == null){
            inst = new httpUtil();
        }
        return inst;
    }



    public  String sendData(String url , String id, String pwd) throws ClientProtocolException, IOException {
        // TODO Auto-generated method stub

        HttpPost request = makeHttpPost( id, pwd, url ) ;//"http://www.shop-wiz.com/android_post.php"


        //HttpGet request = makeHttpGet( id, pwd, "http://www.shop-wiz.com/android_post.php" ) ;

        HttpClient client = new DefaultHttpClient() ;
        ResponseHandler<String> reshandler = new BasicResponseHandler() ;
        String result = client.execute( request, reshandler ) ;
        return result ;
    }

    //post
    private HttpPost makeHttpPost(String user_id, String user_pwd, String url) {
        // TODO Auto-generated method stub

        HttpPost request = new HttpPost( url ) ;
        Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;
        nameValue.add( new BasicNameValuePair( "user_id", user_id ) ) ;
        nameValue.add( new BasicNameValuePair( "user_pwd", user_pwd ) ) ;
        request.setEntity( makeEntity( nameValue ) ) ;
        return request ;
    }

    //get
    private HttpGet makeHttpGet(String user_id, String user_pwd, String url) {
        // TODO Auto-generated method stub
        Vector<NameValuePair> nameValue = new Vector<NameValuePair>() ;
        nameValue.add( new BasicNameValuePair( "user_id", user_id ) ) ;
        nameValue.add( new BasicNameValuePair( "user_pwd", user_pwd ) ) ;

        String my_url = url + "?" + URLEncodedUtils.format(nameValue, null) ;
        HttpGet request = new HttpGet( my_url ) ;
        return request ;
    }

    private   HttpEntity makeEntity( Vector<NameValuePair> nameValue ) {
        HttpEntity result = null ;
        try {
            result = new UrlEncodedFormEntity( nameValue ) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result ;
    }

}
