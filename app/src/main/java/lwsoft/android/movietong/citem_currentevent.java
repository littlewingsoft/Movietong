package lwsoft.android.movietong;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gilbert on 2015-04-22.
 */
public class citem_currentevent extends LinearLayout {
    Context mContext;
    View mView;
    NetworkImageView iv_poster;
    TextView tv_title;
    TextView tv_day;
    TextView tv_grade;
    TextView tv_genre;

    public citem_currentevent(Context context)
    {
        super(context);
        init(context);
    }

    public citem_currentevent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context);

    }

        private void init(Context context)
    {
        mContext = context;
        /*String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.citem_currentevent,  this, true);
        addView(v);
        mView = v;
        return v;
        */
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView  =inflater.inflate(R.layout.citem_currentevent, this, true);

        //mView  =inflate( context, R.layout.citem_currentevent, this);

        tv_title = (TextView )mView.findViewById(R.id.currentEvent_textView_title);
        tv_day = (TextView )mView.findViewById(R.id.currentEvent_textView_day);
        tv_genre = (TextView )mView.findViewById(R.id.currentEvent_textView_genre);
        tv_grade = (TextView )mView.findViewById(R.id.currentEvent_textView_grade);

        iv_poster = (NetworkImageView)mView.findViewById(R.id.currentEvent_imageView_poster);
        iv_poster.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    public void setData(JSONObject jo){
        try {
            tv_title.setText( jo.getString("movie_title") );
            tv_day.setText(  ""+jo.getInt("movie_viewingtime")  );
            tv_genre.setText( jo.getString("movie_genre") );
            tv_grade.setText("" + jo.getInt("movie_rating"));
            ImageLoader imageLoader = util_volley.getInstance().getImageLoader();
            iv_poster.setImageUrl( jo.getString("movie_thumbnail"), imageLoader);

            //
        }catch (JSONException je){
            je.printStackTrace();
        }
    }
/*
    public citem_currentevent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }*/
/*
    public citem_currentevent(Context context, JSONObject jo) {
        super(context);
        try {
            mView = initMarbleView( context );
            tv_title = (TextView )mView.findViewById(R.id.currentEvent_textView_title);
            tv_title.setText( jo.getString("movie_title") );

            tv_day = (TextView )mView.findViewById(R.id.currentEvent_textView_day);
            tv_day.setText(  ""+jo.getInt("movie_viewingtime")  );

            tv_genre = (TextView )mView.findViewById(R.id.currentEvent_textView_genre);
            tv_genre.setText( jo.getString("movie_genre") );

            tv_grade = (TextView )mView.findViewById(R.id.currentEvent_textView_grade);
            tv_grade.setText("" + jo.getInt("movie_rating"));

            iv_poster = (NetworkImageView)mView.findViewById(R.id.currentEvent_imageView_poster);
            ImageLoader imageLoader = util_volley.getInstance().getImageLoader();
            iv_poster.setScaleType(ImageView.ScaleType.FIT_XY);
            iv_poster.setImageUrl( jo.getString("movie_thumbnail"), imageLoader);

            //
        }catch (JSONException je){
            je.printStackTrace();
        }
    }
    */

   

}
