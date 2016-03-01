package com.fsc.gardiner;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Gardiner_main extends AppCompatActivity {

    Button btnOp, btnNed, btnDis;
    TextView lumn;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gardiner_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent newint = getIntent();
        address = newint.getStringExtra(Bluetooth_connecter.EXSTRA_ADDRESS);

        btnOp = (Button)findViewById(R.id.button);
        btnNed = (Button)findViewById(R.id.button2);

        new ConnectBT().execute();

        /*
        //Lille mail knap

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        */
    }

    private void txt(String t)
    {
        Toast.makeText(getApplicationContext(),t,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gardiner_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Gardiner_main.this, "Connecting", "Please wait");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice negative = myBluetooth.getRemoteDevice(address);
                    btSocket = negative.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                txt("Connection failed");
                finish();
            }
            else
            {
                txt("Connected");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
