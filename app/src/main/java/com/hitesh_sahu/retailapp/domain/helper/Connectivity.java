/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.hitesh_sahu.retailapp.domain.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Check device's network connectivity and speed
 *
 * @author emil http://stackoverflow.com/users/220710/emil
 */
public class Connectivity {

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm)
            return cm.getActiveNetworkInfo();
        return null;
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @param type
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @param type
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && Connectivity
                .isConnectionFast(info.getType(), info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                /*
				 * Above API level 7, make sure to set android:targetSdkVersion
				 * to appropriate level to use these
				 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10)
                sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     *
     * @param str
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename)
            throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF
                        && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8")
                    : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
		/*
		 * try { // this is so Linux hack return
		 * loadFileAsString("/sys/class/net/" +interfaceName +
		 * "/address").toUpperCase().trim(); } catch (IOException ex) { return
		 * null; }
		 */
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param ipv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf
                        .getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        // boolean isIPv4 =
                        // InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone
                                // suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr
                                        .substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by 13
                    return "4G";
                default:
                    return "?";
            }
        }
        return "?";
    }

    public static String getBytesRecieved() {

        if (TrafficStats.UNSUPPORTED != TrafficStats.getTotalRxBytes()) {
            return String.valueOf(TrafficStats.getTotalRxBytes()) + "\n ("
                    + bytesHoomanRedable(TrafficStats.getTotalRxBytes(), true)
                    + ")";
        } else {
            return "Not supported by this device";
        }

    }

    public static String getBytesTransmitted() {

        if (TrafficStats.UNSUPPORTED != TrafficStats.getTotalTxBytes()) {
            return String.valueOf(TrafficStats.getTotalTxBytes()) + "\n ("
                    + bytesHoomanRedable(TrafficStats.getTotalTxBytes(), true)
                    + ")";
        } else {
            return "Not supported by this device";
        }
    }

    public static String bytesHoomanRedable(long bytes, boolean showInByte) {

        if (!showInByte) {
            long bits = bytes * 8;

            long Kbit = 1024;
            long Mbit = Kbit * 1024;
            long Gbit = Mbit * 1024;

            if (bits < Kbit)
                return String.valueOf(bits) + " bit";
            if (bits > Kbit && bits < Mbit)
                return String.valueOf(bits / Kbit) + " Kilobit";
            if (bits > Mbit && bits < Gbit)
                return String.valueOf(bits / Mbit) + " Megabit";
            if (bits > Gbit)
                return String.valueOf(bits / Gbit) + " Gigabit";
            return "???";
        } else {

            long bits = bytes;

            long Kbit = 1024;
            long Mbit = Kbit * 1024;
            long Gbit = Mbit * 1024;

            if (bits < Kbit)
                return String.valueOf(bits) + " Byte";
            if (bits > Kbit && bits < Mbit)
                return String.valueOf(bits / Kbit) + " KiloByte";
            if (bits > Mbit && bits < Gbit)
                return String.valueOf(bits / Mbit) + " MegaByte";
            if (bits > Gbit)
                return String.valueOf(bits / Gbit) + " GigaByte";
            return "???";

        }
    }

    public static boolean toggleWifiConnection(Context context,
                                               boolean toggleWIFI) {

        vibrate(context);

        return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .setWifiEnabled(toggleWIFI);
    }

    public static boolean isWifiEnable(Context context) {

        return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .isWifiEnabled();
    }

    public static void vibrate(Context context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE))
                .vibrate(100);
    }

    public static boolean toggleDataConnection(boolean toggleData,
                                               Context context) {

        try {

            vibrate(context);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO)

            {
                Method dataConnSwitchmethod;
                Class<?> telephonyManagerClass;
                Object ITelephonyStub;
                Class<?> ITelephonyClass;

                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                telephonyManagerClass = Class.forName(telephonyManager
                        .getClass().getName());
                Method getITelephonyMethod = telephonyManagerClass
                        .getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
                ITelephonyClass = Class.forName(ITelephonyStub.getClass()
                        .getName());

                if (toggleData) {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("enableDataConnectivity");
                } else {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("disableDataConnectivity");
                }
                dataConnSwitchmethod.setAccessible(true);
                dataConnSwitchmethod.invoke(ITelephonyStub);

            } else {
                // log.i("App running on Ginger bread+");
                final ConnectivityManager conman = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                final Class<?> conmanClass = Class.forName(conman.getClass()
                        .getName());
                final Field iConnectivityManagerField = conmanClass
                        .getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                final Object iConnectivityManager = iConnectivityManagerField
                        .get(conman);
                final Class<?> iConnectivityManagerClass = Class
                        .forName(iConnectivityManager.getClass().getName());
                final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                        .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(iConnectivityManager,
                        toggleData);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

    public static boolean isMobileDataEnabled(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }

}