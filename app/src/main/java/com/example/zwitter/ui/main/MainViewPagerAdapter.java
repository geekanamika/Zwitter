package com.example.zwitter.ui.main;

import com.example.zwitter.ui.main.feed.FeedFragment;
import com.example.zwitter.ui.main.messages.MessageListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                FeedFragment fragment = FeedFragment.newInstance();
                return fragment;
            case 1 :
                MessageListFragment listFragment = MessageListFragment.newInstance();
                return listFragment;
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
