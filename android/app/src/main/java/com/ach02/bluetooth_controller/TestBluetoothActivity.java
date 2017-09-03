package com.ach02.bluetooth_controller;


import android.bluetooth.BluetoothDevice;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ach02.bluetooth_controller.adapter.BluetoothDeviceAdapter;
import com.ach02.bluetooth_controller.bluetooth.BTInterface;
import com.ach02.bluetooth_controller.bluetooth.BTInterfaceHandler;

import java.io.UnsupportedEncodingException;

public class TestBluetoothActivity extends AppCompatActivity {

    private static final String TAG = "TestBluetoothActivity";

    BTInterface btInterface = BTInterface.getInstance();

    //List<BluetoothDevice> discoveredDevice = new ArrayList<BluetoothDevice>();
    BluetoothDeviceAdapter listAdapter;

    TestBluetoothActivity self;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bluetooth);
        self = this;

        ListView discoveredDeviceListView = (ListView)findViewById(R.id.listDevice);
        listAdapter = new BluetoothDeviceAdapter(this, R.layout.row_device);
        discoveredDeviceListView.setAdapter(listAdapter); // assign model to view
        discoveredDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) parent.getAdapter().getItem(position);
                Toast.makeText(getBaseContext(), "Device selected : " + device.getName(), Toast.LENGTH_SHORT).show();
                btInterface.connect(device, true);


            }
        });


        btInterface.init();
        btInterface.enable();
        btInterface.startDiscovery(getBaseContext());

        btInterface.setHandler(new BTInterfaceHandler() {
            @Override
            public void requestPermissions(String[] permissions, int value) {
                for (String p:permissions) {
                    Log.e(TAG, "Permission : " + p + " = " + value);
                }
                ActivityCompat.requestPermissions(self, permissions, 1);


            }

            @Override
            public void dataReceive(byte[] data) {
                String readMessage = null;
                try {
                    readMessage = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "Data : "+ readMessage);
            }

            @Override
            public void connectionSuccess(BluetoothDevice device) {
                Log.e(TAG, "Connection success : " + device.getName() + ":" +device.getAddress());
                btInterface.write("Hello");
            }

            @Override
            public void connectionFailed() {
                Log.e(TAG, "Connectionfailed");
            }

            @Override
            public void newDiscoveredDevice(BluetoothDevice device) {
                Log.e(TAG, "New discovered device : " + device.getName() + " " +device.getAddress());
                listAdapter.add(device);
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void error(BTInterface.Error error) {
                Log.e(TAG, "Error : " + error.toString());
            }
        });

    }

    @Override
    public void onStop(){
        super.onStop();
        if(btInterface.isDiscovering())
            btInterface.cancelDiscovery(getBaseContext());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(btInterface.isDiscovering())
            btInterface.cancelDiscovery(getBaseContext());
    }
}



/*

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;


public class TestBluetoothActivity extends Activity {


    protected BluetoothAdapter bluetoothAdapter = null;


    protected BluetoothSocket socket = null;


    BluetoothDevice blueToothDevice = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bluetooth);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Log.e(this.toString(), "Bluetooth Not Available.");
            return;
        }


        blueToothDevice = bluetoothAdapter.getRemoteDevice("C0:EE:FB:D5:6F:A3");

        for (Integer port = 1; port <= 3; port++) {
            simpleComm(Integer.valueOf(port));
        }
    }

    protected void simpleComm(Integer port) {
        // byte [] inputBytes = null;

        // The documents tell us to cancel the discovery process.
        bluetoothAdapter.cancelDiscovery();

        Log.d(this.toString(), "Port = " + port);
        try {
            // This is a hack to access "createRfcommSocket which is does not
            // have public access in the current api.
            // Note: BlueToothDevice.createRfcommSocketToServiceRecord (UUID
            // uuid) does not work in this type of application. .
            Method m = blueToothDevice.getClass().getMethod(
                    "createRfcommSocket", new Class[] { int.class });
            socket = (BluetoothSocket) m.invoke(blueToothDevice, port);

            // debug check to ensure socket was set.
            assert (socket != null) : "Socket is Null";

            // attempt to connect to device
            socket.connect();
            try {
                Log.d(this.toString(),
                        "************ CONNECTION SUCCEES! *************");

                // Grab the outputStream. This stream will send bytes to the
                // external/second device. i.e it will sent it out.
                // Note: this is a Java.io.OutputStream which is used in several
                // types of Java programs such as file io, so you may be
                // familiar with it.
                OutputStream outputStream = socket.getOutputStream();

                // Create the String to send to the second device.
                // Most devices require a '\r' or '\n' or both at the end of the
                // string.
                // @todo set your message
                String message = "Data from Android and tester program!\r";

                // Convert the message to bytes and blast it through the
                // bluetooth
                // to the second device. You may want to use:
                // public byte[] getBytes (Charset charset) for proper String to
                // byte conversion.
                outputStream.write(message.getBytes());

            } finally {
                // close the socket and we are done.
                socket.close();
            }
            // IOExcecption is thrown if connect fails.
        } catch (IOException ex) {
            Log.e(this.toString(), "IOException " + ex.getMessage());
            // NoSuchMethodException IllegalAccessException
            // InvocationTargetException
            // are reflection exceptions.
        } catch (NoSuchMethodException ex) {
            Log.e(this.toString(), "NoSuchMethodException " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            Log.e(this.toString(), "IllegalAccessException " + ex.getMessage());
        } catch (InvocationTargetException ex) {
            Log.e(this.toString(),
                    "InvocationTargetException " + ex.getMessage());
        }
    }

}

*/
