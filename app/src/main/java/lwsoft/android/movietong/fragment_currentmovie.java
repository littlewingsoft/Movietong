package lwsoft.android.movietong;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class fragment_currentmovie extends Fragment {
    private View mView;
    private LinearLayout mViewEvent;
    private LinearLayout mViewMovie;
    private FragmentActivity myContext;
    private adapter_poster mAdapter;
    private ViewPager mPager;
    private Handler mHandler;

    public int mType = 0 ;
    // TODO: Rename and change types and number of parameters
    public static fragment_currentmovie newInstance(int type) {
        fragment_currentmovie inst = new fragment_currentmovie();
        Bundle args = new Bundle();
        args.putInt("mType", type);
        inst.setArguments(args);
        return inst;
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
            myContext = (FragmentActivity) activity;
            // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        List<Fragment> fragments = myContext.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
            for (Fragment f : fragments) {
                //You can perform additional check to remove some (not all) fragments:
                if (f instanceof fragment_poster) {
                    ft.remove(f);
                }
            }
            ft.commitAllowingStateLoss();
        }
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
        void onFragmentInteraction(Uri uri);
    }


    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt("mType");
        }
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    try {
                        switch (msg.what) {
                            case 0: {
                                FragmentManager fragManager = myContext.getSupportFragmentManager();
                                mAdapter = new adapter_poster(fragManager, (JSONObject) msg.obj);
                                mPager = (ViewPager) mView.findViewById(R.id.ViewPager_poster);
                                mPager.setAdapter(mAdapter);
                            }
                            break;

                            case 1: //current movie list
                            {
                                Log.i("tag_", "current event movie list");
                                setupRootCurrentEvent(mViewEvent, (JSONObject) msg.obj);
                            }
                            break;

                            case 2: {
                                Log.i("tag_", "current movie list");

                                try {
                                    setupRootCurrentMovie(mViewMovie, (JSONObject) msg.obj);
                                } catch (JSONException je) {
                                    Log.i("tag_", je.getMessage());
                                    je.printStackTrace();
                                }
                            }
                        }

                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsStrting = sw.toString();

                        Log.e("tag_", exceptionAsStrting);
                    }

                    //Log.d("tag_", "handler call:" + (String) msg.obj );
                }
            };

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutid = R.layout.fragment_currentmovie;
        if( mType == 1)
            layoutid= R.layout.fragment_soonmovie;
        View v = inflater.inflate(layoutid, container, false);
        mView = v;
        mViewEvent = (LinearLayout) mView.findViewById(R.id.root_currentEvent);
        mViewMovie = (LinearLayout) mView.findViewById(R.id.root_currentMovie);
/*        FragmentManager fragManager = myContext.getSupportFragmentManager();
        mAdapter = new MyAdapter( fragManager );
        mPager = (ViewPager) v.findViewById(R.id.ViewPager_poster);
        mPager.setAdapter(mAdapter);*/

        retriveNewMoviePoster();
        retriveNewEventList();
        retriveNewMovieList();

        ImageButton left = (ImageButton) v.findViewById(R.id.imageButton_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mPager.getCurrentItem() - 1;
                if (pos < 0)
                    pos = mAdapter.getCount() - 1;
                mPager.setCurrentItem(pos, true);
            }
        });

        ImageButton right = (ImageButton) v.findViewById(R.id.imageButton_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mPager.getCurrentItem() + 1;
                if (pos >= mAdapter.getCount())
                    pos = 0;
                mPager.setCurrentItem(pos, true);
            }
        });


        return v;
    }

    private void setupRootCurrentEvent(LinearLayout rootView, JSONObject jo) throws JSONException {
        LinearLayout container = rootView;
        //container= (LinearLayout)rootView.findViewById(R.id.root_currentEvent);

        View item = container.findViewById(R.id.currentEvent_item_0);
        JSONArray ja = jo.getJSONArray("postercnt");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject tmp = ja.getJSONObject(i);
            citem_currentevent ic = new citem_currentevent(this.getActivity());
            ic.setData(tmp);
            android.view.ViewGroup.LayoutParams params = item.getLayoutParams();

            ic.setLayoutParams(params);
            ic.setPadding(0, 0, 0, 10);
            ic.setId(item.getId() + i);
            container.addView(ic, i);//, item.getLayoutParams()

        }
        container.removeView(item);
        container.invalidate();
    }

    private void setupRootCurrentMovie(LinearLayout rootView, JSONObject jo) throws JSONException {
        LinearLayout container = rootView;
        //container= (LinearLayout)rootView.findViewById(R.id.root_currentMovie);

        View item = container.findViewById(R.id.currentMovie_item_0);
        //LinearLayout container = (LinearLayout)item.getParent();
        JSONArray ja = jo.getJSONArray("postercnt");
        for (int i = 0; i < ja.length(); i++) {
            JSONObject tmp = ja.getJSONObject(i);
            citem_currentmovie ic = new citem_currentmovie(this.getActivity());
            ic.setData(tmp);
            android.view.ViewGroup.LayoutParams params = item.getLayoutParams();

            ic.setLayoutParams(params);
            ic.setPadding(0, 0, 0, 10);
            ic.setId(item.getId() + i);
            container.addView(ic, i);//, item.getLayoutParams()

        }
        container.removeView(item);
        container.invalidate();
    }

    public void retriveNewMoviePoster() {
        final String url_movieposter = "http://www.movietong.co.kr/Select_MovieTongposter.asp";
        String[] params = {url_movieposter, "0"};
        //new http_get_poster().execute( params);
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
    }

    public void retriveNewEventList() {
        final String url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=1&topcnt=7";
        String[] params = {url_listitem, "1"};
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
        //new http_get_event().execute(params);
    }

    public void retriveNewMovieList() {
        final String url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=2&topcnt=7";
        String[] params = {url_listitem, "2"};
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
        //new http_get_movie().execute(params);
    }

}
