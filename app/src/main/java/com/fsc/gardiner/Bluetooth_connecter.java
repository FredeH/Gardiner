package com.fsc.gardiner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.view.View.OnClickListener;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bluetooth_connecter extends AppCompatActivity {

    Button btnConnect;
    ListView devicelist;

    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> connectedDevices;
    private OutputStream outputStream = null;
    public static String EXSTRA_ADRESS = "device_adress";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connecter);

        btnConnect = (Button) findViewById(R.id.button3);
        devicelist = (ListView) findViewById(R.id.listView);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectedDevicesList();
            }
        });

    }

    private void connectedDevicesList() {
        connectedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (connectedDevices.size() > 0) {
            for (BluetoothDevice bt : connectedDevices) {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);
    }

    private AdapterView.OnItemClickListener myListClickListener = new
            AdapterView.OnClickListener()
            {
                public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3) {
                    String info = ((TextView) v).getText().toString();
                    String adress = info.substring(info.length() - 17);

                    Intent i = new Intent(DeviceList.this, Gardiner_main.class);

                    i.putExtra(EXSTRA_ADRESS, adress);
                    startActivity(i);
                }
                };

    }
