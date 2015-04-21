package lwsoft.android.movietong;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by gilbert on 2015-04-22.
 */
public class item_currentevent extends LinearLayout {
    Context mContext;

    public item_currentevent(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMarbleView(context);

    }
    public item_currentevent(Context context) {
        super(context);
        initMarbleView(context);
    }

    void initMarbleView(Context context) {

        mContext = context;

        String infService = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);

        View v = li.inflate(R.layout.item_currentevent, this, false);

        addView(v);

        //mTitle = (TextView) findViewById(R.id.gridtitle);
        //mImage = (ImageView) findViewById(R.id.img);
    }

}
