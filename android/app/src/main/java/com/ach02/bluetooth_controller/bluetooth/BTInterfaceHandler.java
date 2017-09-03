package com.ach02.bluetooth_controller.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by ACH02 on 22/08/2017.
 */

public interface BTInterfaceHandler {

    public void requestPermissions(String[] permissions, int value);

    public void dataReceive(byte[] data);

    public void connectionSuccess(BluetoothDevice device);

    public void connectionFailed();

    public void newDiscoveredDevice(BluetoothDevice device);

    public void error(BTInterface.Error error);
}
