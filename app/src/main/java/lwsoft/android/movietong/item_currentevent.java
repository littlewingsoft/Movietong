package lwsoft.android.movietong;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
public class item_currentevent extends LinearLayout {
    Context mContext;
    NetworkImageView iv_poster;
    TextView tv_title;
    TextView tv_day;
    TextView tv_grade;
    TextView tv_genre;

    public item_currentevent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMarbleView(context);

    }
    public item_currentevent(Context context,JSONObject jo) {
        super(context);
        try {
            View v = initMarbleView(context);
            tv_title = (TextView )v.findViewById(R.id.currentMovie_textView_title);
            tv_title.setText( jo.getString("movie_title") );

            tv_day = (TextView )v.findViewById(R.id.currentMovie_textView_day);
            tv_day.setText(  ""+jo.getInt("movie_viewingtime")  );

            tv_genre = (TextView )v.findViewById(R.id.currentMovie_textView_genre);
            tv_genre.setText( jo.getString("movie_genre") );

            tv_grade = (TextView )v.findViewById(R.id.currentMovie_textView_grade);
            tv_grade.setText("" + jo.getInt("movie_rating"));

            iv_poster = (NetworkImageView)v.findViewById(R.id.currentMovie_imageView_poster);
            ImageLoader imageLoader = util_volley.getInstance().getImageLoader();
            iv_poster.setScaleType(ImageView.ScaleType.FIT_XY);
            iv_poster.setImageUrl( jo.getString("movie_thumbnail"), imageLoader);

            //
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    View initMarbleView(Context context)
    {

        mContext = context;

        String infService = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);

        View v = li.inflate(R.layout.item_currentevent, this, false);

        addView(v);

        return v;
    }

}
