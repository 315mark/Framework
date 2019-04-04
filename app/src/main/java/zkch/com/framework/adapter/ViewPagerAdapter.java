package zkch.com.framework.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import zkch.com.framework.bean.FragmentInfo;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments;
    public ViewPagerAdapter(FragmentManager fm , List<FragmentInfo> fragmentInfos) {
        super(fm);
        this.mFragments = fragmentInfos;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        try {
            fragment = (Fragment) mFragments.get(position).getFragment().newInstance();
            return fragment;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
