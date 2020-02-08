package com.yashpatel.bluetoothmessenger;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {


    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new everyone_fragment();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = null;

        switch (position) {
            case 0:
                title = "Everyone";
                break;
            case 1:
                title = "Nearby";
                break;
            case 2:
                title = "Chats";
                break;
            case 3:
                title = "Contacts";
                break;
        }
        return title;
    }
}
