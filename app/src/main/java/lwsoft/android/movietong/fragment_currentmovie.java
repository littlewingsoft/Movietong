package lwsoft.android.movietong;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_currentmovie.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_currentmovie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_currentmovie extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_currentmovie.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_currentmovie newInstance(String param1, String param2) {
        fragment_currentmovie fragment = new fragment_currentmovie();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_currentmovie() {
        // Required empty public constructor
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myContext=(FragmentActivity) activity;
           // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
        View v = inflater.inflate(R.layout.fragment_currentmovie, container, false);
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

        setupRootCurrentEvent(v);

        return v;
    }
    private void setupRootCurrentEvent(View rootView){
        LinearLayout container;
        container= (LinearLayout)rootView.findViewById(R.id.root_currentEvent);

        View item = container.findViewById( R.id.current_item_0 );
        for (int i = 0; i < 5; i++) {
            item_currentevent ic = new item_currentevent(this.getActivity());
            android.view.ViewGroup.LayoutParams params = item.getLayoutParams();

            ic.setLayoutParams(params);
            ic.setPadding(0,0,0,10 );
            ic.setId(item.getId() + i );
            container.addView(ic, i);//, item.getLayoutParams()

        }
        container.removeView(item);


    }
}
