package lwsoft.android.movietong;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gilbert on 2015-04-07.
 */

public class fragment_poster extends Fragment implements View.OnClickListener {

    String url_poster;
    String objectid ;
    //ImageView mImageView;
    NetworkImageView mImageView;

   /* private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Log.i( "tag_", "url_poster:" + url_poster );
            View v = (View )msg.obj;
            mImageView = (NetworkImageView)v.findViewById(R.id.imageView);
            ImageLoader imageLoader = util_volley.getInstance().getImageLoader();
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setImageUrl(url_poster, imageLoader);

            //mImageView.setDefaultImageResId( R.drawable.loading );
            switch( msg.what ){
                case 0:

                    break;
            }
        }

    };*/

    public void onClick(View v){
        Log.i("tag_", "oh click! + "  + objectid);
        Toast.makeText(v.getContext(),objectid,Toast.LENGTH_SHORT ).show();
    }

    static fragment_poster newInstance( JSONObject jo) throws JSONException
    {
        fragment_poster f = new fragment_poster();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        String url = jo.getString("movie_photo1");
        String objectid = jo.getString("movie_objectid");
        args.putString("objectid", objectid );
        args.putString("url_poster", url);
        f.setArguments(args);
        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if( b != null )
        {
            url_poster= b.getString("url_poster") ;
            objectid = b.getString("objectid");
            //Log.i("tag_", "fragment_poster newInstance : "  + url_poster );
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("tag_", "onCreateView : "  + url_poster);
        View v = inflater.inflate(R.layout.fragment_poster, container, false);
        mImageView = (NetworkImageView)v.findViewById(R.id.imageView);
        ImageLoader imageLoader = util_volley.getInstance().getImageLoader();
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageUrl(url_poster, imageLoader);
        v.setOnClickListener(this);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            for(String key : savedInstanceState.keySet()) {
                Log.i( "tag_", "savedInstanceState key : " + key);
            }
        }

        super.onActivityCreated(savedInstanceState);

        // 뷰에 데이터를 넣는 작업 등을 할 추가할 수 있음
        //download http url, and set image!!!
        Log.i("tag_", "fragment_poster onActivityCreated: "  );


/*
        imageLoader.get( url_poster, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "tag_", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    mImageView.setImageBitmap(response.getBitmap());
                }
            }
        });
        */
/*
// If you are using normal ImageView
        imageLoader.get( url_poster, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag_", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    mImageView.setImageBitmap(response.getBitmap());
                }
            }
        });*/


    }



}
