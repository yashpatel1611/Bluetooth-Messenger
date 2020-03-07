package com.yashpatel.bluetoothmessenger;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {


    public TabPagerAdapter(Context context, FragmentManager fm) {
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
                fragment = new nearby_fragment();
                break;
            case 2:
                fragment = new chats_fragment();
                break;
            case 3:
                fragment = new contacts_fragment();
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
        CharSequence pageTitle = null;

        switch (position) {
            case 0:
                pageTitle = "Everyone";
                break;
            case 1:
                pageTitle = "Nearby";
                break;
            case 2:
                pageTitle = "Chats";
                break;
            case 3:
                pageTitle = "Contacts";
                break;
        }
        return pageTitle;
    }
}
