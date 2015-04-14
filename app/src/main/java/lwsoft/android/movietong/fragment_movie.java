package lwsoft.android.movietong;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbert on 2015-04-07.
 */
public class fragment_movie extends Fragment {

    private FragmentActivity myContext;
    private MyAdapter mAdapter;
    private ViewPager mPager;
    public static final String URL_IMAGE = "http://api.androidhive.info/volley/volley-image.jpg";


    public static class MyAdapter extends FragmentPagerAdapter {

        List<fragment_poster> list_fragment;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            list_fragment = new ArrayList();

            list_fragment.add(fragment_poster.newInstance(URL_IMAGE));
            list_fragment.add(fragment_poster.newInstance("http://cafelog.zz.mu/poster/avengers.jpg"));
            list_fragment.add( fragment_poster.newInstance("http://cafelog.zz.mu/poster/%EB%A0%88%EB%AF%B8%EC%A0%9C%EB%9D%BC%EB%B8%94.jpg") );
            list_fragment.add(fragment_poster.newInstance("http://cafelog.zz.mu/poster/%EA%B0%90%EC%8B%9C%EC%9E%90%EB%93%A4.jpg"));
            list_fragment.add( fragment_poster.newInstance("http://cafelog.zz.mu/poster/%EC%8B%A0%EC%84%B8%EA%B3%84.jpg") );
        }

        @Override
        public int getCount() {
            return list_fragment.size()  ;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        mAdapter = new MyAdapter( fragManager );
        mPager = (ViewPager) v.findViewById(R.id.ViewPager_poster);
        mPager.setAdapter(mAdapter);

        ImageButton left = (ImageButton)v.findViewById(R.id.imageButton_left);
        left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int pos = mPager.getCurrentItem()-1;
                if( pos < 0 )
                    pos = mAdapter.getCount()-1;
                mPager.setCurrentItem( pos, true );
            }
        });

        ImageButton right = (ImageButton )v.findViewById(R.id.imageButton_right);
        right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int pos = mPager.getCurrentItem()+1;
                if( pos >= mAdapter.getCount() )
                    pos = 0;
                mPager.setCurrentItem( pos, true );
            }
        });


        return v;
    }
}
