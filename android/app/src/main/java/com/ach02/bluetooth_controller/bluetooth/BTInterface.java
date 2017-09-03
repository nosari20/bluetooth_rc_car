package com.ach02.bluetooth_controller.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ACH02 on 22/08/2017.
 */

public class BTInterface {

    private static UUID BTMODULEUUID = /*UUID.randomUUID()*/ UUID.fromString("00000000-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "BTInterface";

    public enum Error{
        UNKNOWN,
        NOT_ENABLED,
        NOT_FOUND,
        CANNOT_CREATE_SOCKET,
        CONNECTION_LOST
    }

    private enum Message {
        MESSAGE_READ,
        CONNECTING_STATUS
    }

    private BluetoothAdapter mBTAdapter;
    private BluetoothSocket mBTSocket;
    private BTInterface.ConnectedThread mConnectedThread;


    private Handler mHandler;
    private BroadcastReceiver blReceiver;


    private BTInterfaceHandler handler;
    
    
    private static BTInterface instance;


    private BTInterface(){
        this.handler = new BTInterfaceHandler() {
            @Override
            public void requestPermissions(String[] permissions, int value) {
                for (String p:permissions) {
                    Log.e(TAG, "Permission : " + p + " = " + value); 
                }
                
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
            }

            @Override
            public void connectionFailed() {
                Log.e(TAG, "Connectionfailed");
            }

            @Override
            public void newDiscoveredDevice(BluetoothDevice device) {
                Log.e(TAG, "New discovered device : " + device.getName() + " " +device.getAddress());
            }

            @Override
            public void error(Error error) {
                Log.e(TAG, "Error : " + error.toString());
            }
        };
    }
    
    public static BTInterface getInstance(){
        if(instance == null) instance = new BTInterface();
        return instance;
    }
    
    public void setHandler(BTInterfaceHandler h){
        this.handler = h;
    }

    public void init(){
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }

        handler.requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION }, 1);


        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == Message.MESSAGE_READ.ordinal()){
                   handler.dataReceive((byte[]) msg.obj);
                }
                if(msg.what == Message.CONNECTING_STATUS.ordinal()){
                    if(msg.arg1 == 1){
                        handler.connectionSuccess((BluetoothDevice) msg.obj);
                    }else{
                        handler.connectionFailed();
                    }
                }
            }
        };


        this.blReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    handler.newDiscoveredDevice(device);
                }
            }
        };

    }

    public void enable() {
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }
        mBTAdapter.enable();
    }

    public void disable() {
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }
        mBTAdapter.disable();
    }

    public void startDiscovery(Context context){
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }
        if(!mBTAdapter.isEnabled()){
            handler.error(Error.NOT_ENABLED);
            return;
        }

        if(!mBTAdapter.isDiscovering()){
            mBTAdapter.startDiscovery();
            context.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    public boolean isDiscovering(){
        return mBTAdapter.isDiscovering();
    }

    public void cancelDiscovery(Context context){
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }
        if(!mBTAdapter.isEnabled()){
            handler.error(Error.NOT_ENABLED);
            return;
        }
        if(mBTAdapter.isDiscovering()){
            context.unregisterReceiver(blReceiver);
            mBTAdapter.cancelDiscovery();
        }
    }

    public Set<BluetoothDevice> getPairedDevices(){
        return mBTAdapter.getBondedDevices();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device, int port){
        /*
        if(secure){
             return (BluetoothSocket) mBTAdapter.getClass().getMethod("createRfcommSocketToServiceRecord", new Class[] {int.class}).invoke(device,1);
            //return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        }else{
            return device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);
        }
        */
        /*
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        */
        //return  device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);


        BluetoothSocket tmp = null;
        try {

            Method method = device.getClass().getMethod(
                    "createInsecureRfcommSocket", new Class[] { int.class });

            tmp = (BluetoothSocket) method.invoke(device, port);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "createRfcommSocket() failed", e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.e(TAG, "createRfcommSocket() failed", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "createRfcommSocket() failed", e);
        }
        return tmp;

    }

    public void connect(final BluetoothDevice btdevice, final boolean secure){
        if(mBTAdapter == null){
            handler.error(Error.NOT_FOUND);
            return;
        }
        if(!mBTAdapter.isEnabled()){
            handler.error(Error.NOT_ENABLED);
            return;
        }

        new Thread()
        {
            public void run() {

                boolean fail = true;

                System.out.println(btdevice);

                mBTAdapter.cancelDiscovery();

                int port  = 1;

                mBTSocket = createBluetoothSocket(btdevice, port);


                // Establish the Bluetooth socket connection.
                while(fail && port < 4){
                    System.out.println(port);
                    try {
                        mBTSocket.connect();
                        fail = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                        port++;
                        mBTSocket = createBluetoothSocket(btdevice, port);
                        try {
                            mBTSocket.close();
                            handler.connectionFailed();
                            //return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            handler.error(Error.CANNOT_CREATE_SOCKET);
                        }
                    }
                }

                if(!fail) {
                    mConnectedThread = new ConnectedThread(mBTSocket);
                    mConnectedThread.start();
                    handler.connectionSuccess(btdevice);
                }
            }
        }.start();
    }

    public void write(String message){
        mConnectedThread.write(message);
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(Message.MESSAGE_READ.ordinal(), bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    handler.error(Error.CONNECTION_LOST);
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void close() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }




}
