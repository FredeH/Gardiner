package com.fsc.gardiner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Gardiner_main extends FragmentActivity implements ActionBar.TabListener {


    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;
    private static Switch BTswitch;
    private static Button BTconnect;
    private static Button rulop;
    private static Button rulned;
    private static ListView devicelist;
    private static Set<BluetoothDevice> pairedDevices;
    private static Context context;
    private static ArrayAdapter<String> BTArrayAdapter;

    private static BluetoothSocket btSocket = null;
    private static String address = "XX:XX:XX:XX:XX:XX";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    final static BroadcastReceiver btReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    public static BluetoothAdapter myBluetooth;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gardiner_main);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        Gardiner_main.context = getApplicationContext();

        //final ActionBar actionBar = getActionBar();


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
                    //Vis BluetoothFragment efter swipe
                    return new BluetoothFragment();
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

        public void onViewCreated(final View rootView, Bundle savedInstanceState){

            rulop = (Button)rootView.findViewById(R.id.button);
            rulop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DO STUFF
                }
            });

            rulned = (Button)rootView.findViewById(R.id.button2);
            rulned.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DO STUFF
                }
            });
        }

    }

    public static class BluetoothFragment extends Fragment {

        //public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_bluetooth_connecter, container, false);

            devicelist = (ListView)rootView.findViewById(R.id.listView);

            BTArrayAdapter = new ArrayAdapter<>(getAppContext(),android.R.layout.simple_list_item_1);

            devicelist.setAdapter(BTArrayAdapter);
            devicelist.setOnItemClickListener(myDeviceClickListener);

            return rootView;

        }



        public void Connect() {
            BluetoothDevice device = myBluetooth.getRemoteDevice(address);
            Log.d("", "Connecting to ... " + device);
            myBluetooth.cancelDiscovery();
            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
/* Here is the part the connection is made, by asking the device to create a RfcommSocket (Unsecure socket I guess), It map a port for us or something like that */
                btSocket.connect();
                Log.d("", "Connection made.");
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    Log.d("", "Unable to end the connection");
                }
                Log.d("", "Socket creation failed");
            }
        }


        private AdapterView.OnItemClickListener myDeviceClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                // Cancel discovery because it's costly and we're about to connect
                if (myBluetooth.isDiscovering()) {
                    myBluetooth.cancelDiscovery();
                }
                // Get the device MAC address, which is the last 17 chars in the View
                String info = ((TextView) v).getText().toString();
                String address = info.substring(info.length() - 17);
                // Create the result Intent and include the MAC address
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                // Set result and finish this Activity
                getActivity().finish();

            }
        };


        public static Context getAppContext() {
            return Gardiner_main.context;
        }

        public void list(View rootView){

            pairedDevices = myBluetooth.getBondedDevices();

            for(BluetoothDevice device : pairedDevices)
                BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());

            Toast.makeText(getAppContext(),"Vis stuff", Toast.LENGTH_LONG).show();
        }





        public void onViewCreated(final View rootView, Bundle savedInstanceState){

            BTconnect = (Button)rootView.findViewById(R.id.button3);
            BTconnect.setOnClickListener(new View.OnClickListener() {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                @Override
                public void onClick(View v) {
                    BTArrayAdapter.clear();
                    getActivity().registerReceiver(btReceiver, filter);
                    myBluetooth.startDiscovery();
                    list(v);
                }
            });




            BTswitch = (Switch)rootView.findViewById(R.id.switch1);
            BTswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton rootView, boolean isChecked) {
                    if (isChecked) {
                        myBluetooth.enable();
                    } else {
                        myBluetooth.disable();
                    }
                }
            });
        }

        @Override
        public void onDestroy() {
            getAppContext().unregisterReceiver(btReceiver);
            if (btSocket != null) {
                try {
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            super.onDestroy();
        }


    }
}