package com.ach02.bluetooth_controller.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.ParcelUuid;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ach02.bluetooth_controller.R;
import com.ach02.bluetooth_controller.TestBluetoothActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ACH02 on 23/08/2017.
 */

public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    private static final Map<String, String> uuidsDescriptions = new HashMap<String, String>();

    static {
        uuidsDescriptions.put("0001", "SDP");
        uuidsDescriptions.put("0002", "UDP");
        uuidsDescriptions.put("0003", "RFCOMM");
        uuidsDescriptions.put("0004", "TCP");
        uuidsDescriptions.put("0005", "TCS-BIN");
        uuidsDescriptions.put("0006", "TCS-AT");
        uuidsDescriptions.put("0007", "ATT");
        uuidsDescriptions.put("0008", "OBEX");
        uuidsDescriptions.put("0009", "IP");
        uuidsDescriptions.put("000A", "FTP");
        uuidsDescriptions.put("000C", "HTTP");
        uuidsDescriptions.put("000E", "WSP");
        uuidsDescriptions.put("000F", "BNEP");

        uuidsDescriptions.put("0010", "UPNP");
        uuidsDescriptions.put("0011", "HIDP");
        uuidsDescriptions.put("0012", "HardcopyControlChannel");
        uuidsDescriptions.put("0014", "HardcopyDataChannel");
        uuidsDescriptions.put("0016", "HardcopyNotification");
        uuidsDescriptions.put("0017", "AVCTP");
        uuidsDescriptions.put("0019", "AVDTP");
        uuidsDescriptions.put("001B", "CMTP");
        uuidsDescriptions.put("001E", "MCAPControlChannel");
        uuidsDescriptions.put("001F", "MCAPDataChannel");
        uuidsDescriptions.put("0100", "L2CAP");

        uuidsDescriptions.put("1000", "ServiceDiscoveryServerService");
        uuidsDescriptions.put("1001", "BrowseGroupDescriptorService");
        uuidsDescriptions.put("1002", "PublicBrowseGroupService");
        uuidsDescriptions.put("1101", "SerialPortService");
        uuidsDescriptions.put("1102", "LANAccessUsingPPPService");
        uuidsDescriptions.put("1103", "DialupNetworkingService");
        uuidsDescriptions.put("1104", "IrMCSyncService");
        uuidsDescriptions.put("1105", "OBEXObjectPushService");
        uuidsDescriptions.put("1106", "OBEXFileTransferService");
        uuidsDescriptions.put("1107", "IrMCSyncCommandService");
        uuidsDescriptions.put("1108", "HeadsetService");
        uuidsDescriptions.put("1109", "CordlessTelephonyService");
        uuidsDescriptions.put("110A", "AudioSourceService");
        uuidsDescriptions.put("110B", "AudioSinkService");
        uuidsDescriptions.put("110C", "AVRemoteControlTargetService");
        uuidsDescriptions.put("110D", "AdvancedAudioDistributionService");
        uuidsDescriptions.put("110E", "AVRemoteControlService");
        uuidsDescriptions.put("110F", "VideoConferencingService");

        uuidsDescriptions.put("1110", "IntercomService");
        uuidsDescriptions.put("1111", "FaxService");
        uuidsDescriptions.put("1112", "HeadsetAudioGatewayService");
        uuidsDescriptions.put("1113", "WAPService");
        uuidsDescriptions.put("1114", "WAPClientService");
        uuidsDescriptions.put("1115", "PANUService");
        uuidsDescriptions.put("1116", "NAPService");
        uuidsDescriptions.put("1117", "GNService");
        uuidsDescriptions.put("1118", "DirectPrintingService");
        uuidsDescriptions.put("1119", "ReferencePrintingService");
        uuidsDescriptions.put("111A", "ImagingService");
        uuidsDescriptions.put("111B", "ImagingResponderService");
        uuidsDescriptions.put("111C", "ImagingAutomaticArchiveService");
        uuidsDescriptions.put("111D", "ImagingReferenceObjectsService");
        uuidsDescriptions.put("111E", "HandsfreeService");
        uuidsDescriptions.put("111F", "HandsfreeAudioGatewayService");

        uuidsDescriptions.put("1120", "DirectPrintingReferenceObjectsService");
        uuidsDescriptions.put("1121", "ReflectedUIService");
        uuidsDescriptions.put("1122", "BasicPringingService");
        uuidsDescriptions.put("1123", "PrintingStatusService");
        uuidsDescriptions.put("1124", "HumanInterfaceDeviceService");
        uuidsDescriptions.put("1125", "HardcopyCableReplacementService");
        uuidsDescriptions.put("1126", "HCRPrintService");
        uuidsDescriptions.put("1127", "HCRScanService");
        uuidsDescriptions.put("1128", "CommonISDNAccessService");
        uuidsDescriptions.put("1129", "VideoConferencingGWService");
        uuidsDescriptions.put("112A", "UDIMTService");
        uuidsDescriptions.put("112B", "UDITAService");
        uuidsDescriptions.put("112C", "AudioVideoService");
        uuidsDescriptions.put("112D", "SIMAccessService");
        uuidsDescriptions.put("112E", "Phonebook Access - PCE");
        uuidsDescriptions.put("112F", "Phonebook Access - PSE");

        uuidsDescriptions.put("1130", "Phonebook Access");
        uuidsDescriptions.put("1131", "Headset - HS");
        uuidsDescriptions.put("1132", "Message Access Server");
        uuidsDescriptions.put("1133", "Message Notification Server");
        uuidsDescriptions.put("1134", "Message Access Profile");
        uuidsDescriptions.put("1135", "GNSS");
        uuidsDescriptions.put("1136", "GNSS_Server");

        uuidsDescriptions.put("1200", "PnPInformationService");
        uuidsDescriptions.put("1201", "GenericNetworkingService");
        uuidsDescriptions.put("1202", "GenericFileTransferService");
        uuidsDescriptions.put("1203", "GenericAudioService");
        uuidsDescriptions.put("1204", "GenericTelephonyService");
    }

    private @LayoutRes int layout;


    public BluetoothDeviceAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        layout = resource;
    }

    public BluetoothDeviceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BluetoothDevice> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(this.layout,parent, false);
        }

        BluetoothDeviceViewHolder viewHolder = (BluetoothDeviceViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new BluetoothDeviceViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.services = (TextView) convertView.findViewById(R.id.services);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        BluetoothDevice device = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(device.getName());
        viewHolder.address.setText(device.getAddress());
        viewHolder.services.setText(getDeviceServices(device).toString());

        return convertView;
    }

    private class BluetoothDeviceViewHolder{
        public TextView name;
        public TextView address;
        public TextView services;
    }





    public static ArrayList<ParcelUuid> getDeviceUuids(BluetoothDevice device) {
        ArrayList<ParcelUuid> result = new ArrayList<ParcelUuid>();

        try {
            Method method = device.getClass().getMethod("getUuids", null);
            ParcelUuid[] phoneUuids = (ParcelUuid[]) method.invoke(device, null);
            if (phoneUuids != null) {
                for (ParcelUuid uuid : phoneUuids) {
                    result.add(uuid);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }


    private static ArrayList<String> getDeviceServices(ArrayList<ParcelUuid> uuids) {
        ArrayList<String> result = new ArrayList<String>();
        for (ParcelUuid uuid : uuids) {
            String s = uuid.toString().toUpperCase();
            boolean found = false;
            for (Map.Entry<String, String> entry : uuidsDescriptions.entrySet()) {
                String key = entry.getKey().toUpperCase();
                String value = entry.getValue();

                if (s.startsWith("0000" + key)) {
                    found = true;
                    result.add(value);
                    break;
                }
            }
            if (!found) {
                String desc = "Unknown service UUID 0x" + s.substring(4, 8);
                result.add(desc);
            }
        }
        return result;
    }


    public static ArrayList<String> getDeviceServices(BluetoothDevice device) {
        ArrayList<ParcelUuid> uuids = getDeviceUuids(device);
        return getDeviceServices(uuids);
    }





}
