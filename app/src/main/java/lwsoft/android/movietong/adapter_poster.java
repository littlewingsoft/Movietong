package lwsoft.android.movietong;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbert on 2015-06-18.
 */
public class adapter_poster extends FragmentPagerAdapter {
    private Context mContext;
    List<fragment_poster> list_fragment = null;
    //fragment_currentmovie mParent;

    public void refresh(JSONObject jo) {
        list_fragment.clear();

        try {
            JSONArray ja = jo.getJSONArray("postercnt");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject tmpJo = ja.getJSONObject(i);
                add(tmpJo);
            }
        } catch (Exception e) {
            Log.i("tag_", e.getMessage());
        }
    }

    public adapter_poster(FragmentManager fm, JSONObject jo) {
        super(fm);

        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            //FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : fragments) {
                //You can perform additional check to remove some (not all) fragments:
                if (f instanceof fragment_poster) {
                    //ft.remove(f);
                    FragmentManager manager =  f.getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(f);
                    trans.commit();

                }
            }
            //ft.commitAllowingStateLoss();
        }

        list_fragment = new ArrayList();
        refresh(jo);
    }

    public void del(int i) {
        list_fragment.remove(i);
    }

    public void add(JSONObject jo) throws JSONException {
        Log.d("tag_", "this is dbg: " + jo.toString());
        list_fragment.add(fragment_poster.newInstance(jo));
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }
}
