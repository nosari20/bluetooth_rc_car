package com.ach02.bluetooth_controller.bluetooth.exceptions;

/**
 * Created by ACH02 on 22/08/2017.
 */

public abstract class BTInterfaceException extends Exception {

    public BTInterfaceException() {
        super("Bluetooth Exception");
    }
    public BTInterfaceException(String message) {
        super(message);

    }
}
