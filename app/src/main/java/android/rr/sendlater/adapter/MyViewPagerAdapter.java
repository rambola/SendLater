package android.rr.sendlater.adapter;

import android.rr.sendlater.CreateNewFragment;
import android.rr.sendlater.RemainingFragment;
import android.rr.sendlater.SentFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return CreateNewFragment.newInstance();
            case 1: return RemainingFragment.newInstance();
            case 2: return SentFragment.newInstance();
            default: return CreateNewFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}