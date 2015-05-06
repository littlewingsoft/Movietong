package lwsoft.android.movietong;

import android.os.Bundle;
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

/**
 * Created by gilbert on 2015-04-07.
 */

public class fragment_poster extends Fragment implements View.OnClickListener {

    String url_poster;
    //ImageView mImageView;
    NetworkImageView mImageView;

    public void onClick(View v){
        Log.i("tag_", "oh click! + "  + url_poster);
        Toast.makeText(v.getContext(),url_poster,Toast.LENGTH_SHORT ).show();
    }

    static fragment_poster newInstance(String url ) {
        fragment_poster f = new fragment_poster();

        // Supply num input as an argument.
        Bundle args = new Bundle();
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
        url_poster= getArguments() != null ? getArguments().getString("url_poster") : "__none__";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poster, container, false);
        mImageView = (NetworkImageView)v.findViewById(R.id.imageView);

        View tv = v.findViewById(R.id.textView);
        ((TextView)tv).setText("url : " + url_poster);

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
        Log.i( "tag_", "url:" + url_poster);

    }



}
