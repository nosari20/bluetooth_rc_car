package com.ach02.bluetooth_controller.bluetooth.exceptions;

/**
 * Created by ACH02 on 22/08/2017.
 */

public class BTInterfaceStateException extends BTInterfaceException {

    public enum Code{
        UNKNOWN,
        NOT_ENABLED,
        NOT_FOUND,
    }

    private Code code = Code.UNKNOWN;

    public BTInterfaceStateException() {
        super("Bluetooth State Exception");
    }

    public BTInterfaceStateException(Code c) {
        super("Bluetooth State Exception");
        this.code = c;
    }

    public Code getCode(){
        return this.code;
    }
}
