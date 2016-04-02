package com.fsc.gardiner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class Gardiner_main extends FragmentActivity implements ActionBar.TabListener {


    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    Switch bluetoothswitch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gardiner_main);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();


        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                //actionBar.setSelectedNavigationItem(position);
                //invalidateOptionsMenu();
            }
        });

        /*
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            if (actionBar != null) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
            }
        } */
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }



    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return new ForsideFragment();

                case 1:
                    // The other sections of the app are dummy placeholders.
                 //   Fragment fragment = new DummySectionFragment();
                 //   Bundle args = new Bundle();
                 //   args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                 //   fragment.setArguments(args);
                    return new DummySectionFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


    public static class ForsideFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.content_gardiner_main, container, false);

            return rootView;


        }

    }

    public static class DummySectionFragment extends Fragment {

        //public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_bluetooth_connecter, container, false);

            return rootView;

        }

        public void onViewCreated(View rootView, Bundle savedInstanceState){

        Bluetooth_connecter myBluetooth_connecter = new Bluetooth_connecter();
        myBluetooth_connecter.bluetooth_switch();
        }


    }
}