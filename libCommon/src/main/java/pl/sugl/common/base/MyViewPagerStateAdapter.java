package pl.sugl.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class MyViewPagerStateAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;

    public MyViewPagerStateAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public void setNewFragments(List<BaseFragment> fragments) {
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public List<BaseFragment> getFragments() {
        return mFragments;
    }
}