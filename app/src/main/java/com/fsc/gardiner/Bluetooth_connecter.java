package com.fsc.gardiner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth_connecter extends AppCompatActivity {

    Button btnConnect;
    ListView devicelist;
    Switch bluetoothswitch;

    private BluetoothAdapter myBluetooth = null;
    public static String EXSTRA_ADDRESS = "device_address";





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connecter);

        btnConnect = (Button) findViewById(R.id.button3);
        devicelist = (ListView) findViewById(R.id.listView);
        bluetoothswitch = (Switch) findViewById(R.id.switch1);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        bluetoothswitch.setChecked(true);
        bluetoothswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    myBluetooth.enable();
                }
                else
                {
                    myBluetooth.disable();
                }
            }
        });
/*
        @Override

        if(myBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth blev ikke fundet på enheden", Toast.LENGTH_LONG).show();

            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }

        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v)
            {
                connectedDevicesList();
            }
        });
*/
    }

    private void connectedDevicesList() {
        Set<BluetoothDevice> connectedDevices = myBluetooth.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();

        if (connectedDevices.size() > 0)
        {
            for (BluetoothDevice bt : connectedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        } else
        {
            Toast.makeText(getApplicationContext(), "Ingen Bluetooth enheder fundet", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);
    }


    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
                    String info = ((TextView) v).getText().toString();
                    String address = info.substring(info.length() - 17);

                    Intent i = new Intent(Bluetooth_connecter.this, Gardiner_main.class);

                    i.putExtra(EXSTRA_ADDRESS, address);
                    startActivity(i);
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_gardiner_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    }

