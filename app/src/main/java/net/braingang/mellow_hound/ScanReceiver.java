package net.braingang.mellow_hound;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

public class ScanReceiver extends BroadcastReceiver {
    public static final String LOG_TAG = ScanReceiver.class.getName();

    public ScanReceiver() {
        // empty
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        if (Personality.runMode == ModeEnum.STOPPED) {
            Personality.wifiScanList.clear();
        } else {
            if (Personality.wifiScanList.size() > 3333) {
                Log.i(LOG_TAG, "scan list limit noted");
            } else {
                new ScanListLoader(context).start();
            }
        }
         */
    }

    /*
    class ScanListLoader extends Thread {
        private Context context;

        public ScanListLoader(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "xxx scan list manager start xxx");

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            ArrayList<ScanResult> winners = new ArrayList<ScanResult>();

            for (ScanResult candidate:wifiManager.getScanResults()) {
                int frequency = candidate.frequency;
                String ssid = candidate.SSID;
                String bssid = candidate.BSSID;

                boolean matchFlag = false;

                for (ScanResult temp:Personality.wifiScanList) {
                    if ((temp.frequency == frequency) && (temp.SSID.equals(ssid)) && (temp.BSSID.equals(bssid))) {
                        matchFlag = true;
                        break;
                    }
                }

                if (!matchFlag) {
                    winners.add(candidate);
                }
            }

            Personality.wifiScanList.addAll(winners);

            Log.d(LOG_TAG, "xxx scan list manager end w/length:" + Personality.wifiScanList.size());
        }
    }
    */
}