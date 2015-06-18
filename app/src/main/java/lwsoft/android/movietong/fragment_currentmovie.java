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
    private JSONObject  mCurrentMovieList;
    private int mType = 0 ;
    // TODO: Rename and change types and number of parameters
    public static fragment_currentmovie newInstance(int type) {
        testJson();
        fragment_currentmovie inst = new fragment_currentmovie();
        Bundle args = new Bundle();
        args.putInt("mType", type);
        inst.setArguments(args);
        return inst;
    }

    private static void testJson(){
        String a = "{ \"key\" : null }";
        try{
            JSONObject jo = new JSONObject(a);
            boolean b = jo.isNull("key");
            if( b )
                Log.i( "tag_", "key is null" );
                else
            Log.i( "tag_", "key except" );
        }catch(JSONException je){
            Log.e( "tag_", je.getMessage() );
        }


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
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if( myContext !=null )
        {

            myContext = null;
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
                                //모든 영화 리스트 받고
                                //하나하나 마다 디테일 정보 요청함.
                                mCurrentMovieList =(JSONObject) msg.obj;
                                rqst_detail(mCurrentMovieList);

//                                //영화 리스트 아이템에다가 detail 값을 추가.
//                                Log.i("tag_", "current movie list");
//
//                                try {
//                                    setupRootCurrentMovie(mViewMovie, (JSONObject) msg.obj);
//                                } catch (JSONException je) {
//                                    Log.i("tag_", je.getMessage());
//                                    je.printStackTrace();
//                                }
                            }break;
                            case 2:{
                                try {
                                    //movie_object id를 찾아서 detail 키로 add 시켜주기.
                                    movieList_addDetail((JSONObject) msg.obj);
                                }
                                catch(Exception e){
                                    Log.e("tag_", e.getMessage());
                                }



                                    if(IsDone_MovieList(mCurrentMovieList))
                                    {
                                        setupRootCurrentEventList(mViewEvent,mCurrentMovieList);
                                        setupRootCurrentMovieList(mViewMovie, mCurrentMovieList);
                                    }



                            }break;
                            case 3:{ //detail info, check event
                                //is there any event ?
//                                try {
//                                    Log.i("tag_", "current event movie list");
//
//                                    //setupRootCurrentEvent(mViewEvent, (JSONObject) msg.obj);
//                                } catch (JSONException je) {
//                                    Log.e("tag_", je.getMessage());
//                                }


                            }break;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        if(savedInstanceState != null) {
            for(String key : savedInstanceState.keySet()) {
                //Log.v( "tag_", "savedInstanceState key : " + key );
            }
        }
        super.onActivityCreated(  savedInstanceState );

        List<Fragment> fragments = myContext.getSupportFragmentManager().getFragments();
        if (fragments != null) {
            FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
            for (Fragment f : fragments) {
                //You can perform additional check to remove some (not all) fragments:
                if (f instanceof fragment_poster) {
                    FragmentManager manager =  f.getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(f);
                    trans.commit();
                }
            }

        }

        retriveNewMoviePoster();
        retriveNewMovieList();

    }
//    private void setupRootCurrentEvent(LinearLayout rootView, JSONObject jo) throws JSONException {
//
//        LinearLayout container = rootView;
//        //container= (LinearLayout)rootView.findViewById(R.id.root_currentEvent);
//
//        View item = container.findViewById(R.id.currentEvent_item_0 );
//        //JSONArray ja = jo.getJSONArray("postercnt");
//        //for (int i = 0; i < ja.length(); i++) {
//            //JSONObject tmp = ja.getJSONObject(i);
//            citem_currentevent ic = new citem_currentevent(this.getActivity());
//            ic.setData(jo);
//            android.view.ViewGroup.LayoutParams params = item.getLayoutParams();
//
//            ic.setLayoutParams(params);
//            ic.setPadding(0, 0, 0, 10);
//            //ic.setId(item.getId()+1000 + i );
//            container.addView( ic );//, item.getLayoutParams()
//
//        //}
//        //container.removeView(item);
//        container.invalidate();
//    }

    private void setupRootCurrentEventList(LinearLayout rootView, JSONObject jo)  {
        LinearLayout container = rootView;
        //container= (LinearLayout)rootView.findViewById(R.id.root_currentEvent);

        View item = container.findViewById(R.id.currentEvent_item_0);
        try{
            JSONArray ja = jo.getJSONArray("postercnt");

            final int cnt = ja.length();
            int index=0;
            for (int i = 0; i < cnt ; i++) {
                JSONObject tmp = ja.getJSONObject(i);
                JSONObject detail = tmp.getJSONObject("detail");
                if(detail != null &&
                        detail.has("event_objectid")  &&
                        detail.isNull("event_objectid") )
                    continue;

                citem_currentevent ic = new citem_currentevent(this.getActivity());
                ic.setData(tmp);
                android.view.ViewGroup.LayoutParams params = item.getLayoutParams();

                ic.setLayoutParams(params);
                ic.setPadding(0, 0, 0, 10);
                ic.setId(item.getId() + index);
                container.addView(ic, index);//, item.getLayoutParams()
                index++;
            }
            container.removeView(item);
        }catch(JSONException je)
        {
            Log.e("tag_", je.getMessage());
        }

        //container.invalidate();
    }

    private void setupRootCurrentMovieList(LinearLayout rootView, JSONObject jo)  {
        try {
            LinearLayout container = rootView;
            //container= (LinearLayout)rootView.findViewById(R.id.root_currentMovie);

            View item = container.findViewById(R.id.currentMovie_item_0);
            //LinearLayout container = (LinearLayout)item.getParent();
            JSONArray ja = jo.getJSONArray("postercnt");
            final int cnt = ja.length();
            for (int i = 0; i < cnt; i++) {
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
            //container.invalidate();
        }catch( JSONException je){
            Log.e("tag_", je.getMessage());
        }
    }

    public void retriveNewMoviePoster() {
        final String url_movieposter = "http://www.movietong.co.kr/Select_MovieTongposter.asp";
        String[] params = {url_movieposter, "0"};
        //new http_get_poster().execute( params);
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
    }

    public void retriveNewMovieList() {

        String url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=1&topcnt=7";
        if( mType == 1)
            url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=2&topcnt=7";

        String[] params = {url_listitem, "1"};
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
        //new http_get_movie().execute(params);
    }

    public void retriveMovieDetail(String movie_objectid) {
        String url = "http://www.movietong.co.kr/Select_MovieTongMovieDetail.asp?movie_objectid=" + movie_objectid ;

        String[] params = {url, "2" };
        new httpUtil_get(this.getActivity(), mHandler).execute(params);
    }

    public void rqst_detail(JSONObject joList){
        try{
            JSONArray ja =  joList.getJSONArray("postercnt");
            for( int n=0; n< ja.length(); n++)
            {
                JSONObject tmp = ja.getJSONObject( n );
                String movie_objectid = tmp.getString("movie_objectid");
                retriveMovieDetail( movie_objectid );
//                String url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=1&topcnt=7";
//                String[] params = {url_listitem, "1"};
//                new httpUtil_get(this.getActivity(), mHandler).execute(params);
            }

        }catch(Exception e){

        }

    }

    public boolean  IsDone_MovieList(JSONObject jo){
        try{
            JSONArray ja = mCurrentMovieList.getJSONArray("postercnt");
            int cnt=0;
            for( int n=0; n<ja.length(); n++)
            {
                JSONObject tmp = ja.getJSONObject(n);
                if( tmp.has("detail") )
                {
                   cnt++;
                }
            }
            if( cnt == ja.length())
                return true;
        }catch(Exception e){
            Log.e("tag_",e.getMessage() );
        }

        return false;
    }
    public void movieList_addDetail(JSONObject jo){

        try{
            JSONArray ja = mCurrentMovieList.getJSONArray("postercnt");
            int cnt=0;
            for( int n=0; n<ja.length(); n++)
            {
                JSONObject tmp = ja.getJSONObject(n);
                if( tmp.getString("movie_objectid").contains( jo.getString("movie_objectid") ) )
                {
                    tmp.put("detail", jo);
                    return ;
                }
            }
        }catch(Exception e){
            Log.e("tag_",e.getMessage() );
        }


    }

//    public void retriveNewEventList(String event_objectid) {
//
//        String url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=1&topcnt=7";
//
//        if( mType == 1)
//            url_listitem = "http://www.movietong.co.kr/Select_MovieTongMovieList.asp?movietype=2&topcnt=7";
//
//        String[] params = {url_listitem, "1"};
//        new httpUtil_get(this.getActivity(), mHandler).execute(params);
//        //new http_get_event().execute(params);
//    }
}
