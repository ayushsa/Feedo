package com.feed.feedo.network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtils
{

    public static boolean isNetworkAvailable(Context context)
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network


            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                // do something
              //  Toast.makeText(context, "Connected through WIFI", Toast.LENGTH_SHORT).show();

            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                // check NetworkInfo subtype
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        // Bandwidth between 100 kbps and below
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_GPRS", Toast.LENGTH_SHORT).show();
                        break;


                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        // Bandwidth between 50-100 kbps
                     //   Toast.makeText(context, "Connected through NETWORK_TYPE_EDGE", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        // Bandwidth between 400-1000 kbps
                     //   Toast.makeText(context, "Connected through NETWORK_TYPE_EVDO_0", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        // Bandwidth between 600-1400 kbps
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_EVDO_A", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        // Bandwidth between 50-100 kbps
                     //   Toast.makeText(context, "Connected through NETWORK_TYPE_1xRTT", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        // Bandwidth between 14-64 kbps
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_CDMA", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        // Bandwidth between 700-1700 kbps
                     //   Toast.makeText(context, "Connected through NETWORK_TYPE_HSDPA", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        // Bandwidth between 2-14 Mbps
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_HSPA", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        // Bandwidth between 1-23 Mbps
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_HSUPA", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        // Bandwidth between 400-7000 kbps
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_UMTS", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        // Bandwidth between Unknown
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_UNKNOWN", Toast.LENGTH_SHORT).show();
                        break;

                    case TelephonyManager.NETWORK_TYPE_LTE:
                        // Bandwidth between Unknown
                      //  Toast.makeText(context, "Connected through NETWORK_TYPE_LTE", Toast.LENGTH_SHORT).show();
                        break;


                }

/*

                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {

                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {

                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_0) {


                } else if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                    // Bandwidth between 600-1400 kbps
                    Toast.makeText(context, "Connected through NETWORK_TYPE_EVDO_A", Toast.LENGTH_SHORT).show();
                }
*/

                // Other list of various subtypes you can check for and their bandwidth limits
                // TelephonyManager.NETWORK_TYPE_1xRTT       ~ 50-100 kbps
                // TelephonyManager.NETWORK_TYPE_CDMA        ~ 14-64 kbps
                // TelephonyManager.NETWORK_TYPE_HSDPA       ~ 2-14 Mbps
                // TelephonyManager.NETWORK_TYPE_HSPA        ~ 700-1700 kbps
                // TelephonyManager.NETWORK_TYPE_HSUPA       ~ 1-23 Mbps
                // TelephonyManager.NETWORK_TYPE_UMTS        ~ 400-7000 kbps
                // TelephonyManager.NETWORK_TYPE_UNKNOWN     ~ Unknown

            }


            connected = true;
        } else
            connected = false;
        return connected;
    }

    public static void showInternetDialog(Context context) {

        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            alertDialog.setTitle("Info");
            alertDialog
                    .setMessage("Internet not available, check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
