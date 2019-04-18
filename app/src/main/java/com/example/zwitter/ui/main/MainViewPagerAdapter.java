package com.example.zwitter.ui.main;

import com.example.zwitter.ui.main.feed.FeedFragment;
import com.example.zwitter.ui.main.users.UserListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MainViewPagerAdapter extends FragmentPagerAdapter {

    MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return FeedFragment.newInstance();
            case 1 :
                return new UserListFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    
}
