package com.example.rakesh.rssi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifi;
    TextView tv1,tv2,tv3,tv4;
   /* int rssi0,rssi1,rssi2;
    String rssiString0, rssiString1,rssiString2;
    String ssid0,ssid1,ssid2;
    ScanResult result0,result1,result2;*/

   int rssiE1,rssiE2,rssiE3;
   double j1,k1,l1,m1,j2,k2,l2,m2,j3,k3,l3,m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
/*
        List<ScanResult> scanResults = wifi.getScanResults();
        Log.e("size",scanResults.size()+"");
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        result0 = wifi.getScanResults().get(0);
        ssid0 = result0.SSID;
        rssi0 = result0.level;
        rssiString0 = String.valueOf(rssi0);

        result1 = wifi.getScanResults().get(1);
        ssid1 = result0.SSID;
        rssi1 = result0.level;
        rssiString1 = String.valueOf(rssi1);

        result2 = wifi.getScanResults().get(2);
        ssid2 = result0.SSID;
        rssi2 = result0.level;
        rssiString2 = String.valueOf(rssi2);

        tv.setText("\n" + ssid0 + "   " + rssiString0 + "\n" + ssid1 + " " + rssiString1 + "\n" + ssid2 + " " + rssiString2);


        Log.e("size",scanResults.size()+"");
        int i;
        for(i=0;i<scanResults.size();i++)
        {
            Log.e("rssi", scanResults.get(i).SSID + " " + scanResults.get(i).level);
        }*/
    }


    private BroadcastReceiver myRssiChangeReceiver
            = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            WifiManager wifiMan=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiMan.startScan();
            List<ScanResult> scanResults = wifiMan.getScanResults();


            int i;

            for(i=0;i<scanResults.size();i++)
            {
                if(scanResults.get(i).SSID.equals("E3"))
                {
                    rssiE3 = scanResults.get(i).level;

                    j3 = (((double) rssiE3*(-1)) - 45)/38;
                    k3 = Math.pow(10,j3);

//                    l3 = Math.pow(k3,2) - Math.pow(1.15,2);
//                    m3 = Math.sqrt(l3);

                    tv3.setText(scanResults.get(i).level + " " + scanResults.get(i).SSID + "( " + k3 + " )");

                }

                if(scanResults.get(i).SSID.equals("E2"))
                {
                    rssiE2 = scanResults.get(i).level;

                    j2 = (((double) rssiE2*(-1)) - 45)/38;
                    k2 = Math.pow(10,j2);

//                    l2 = Math.pow(k2,2) - Math.pow(1.15,2);
//                    m2 = Math.sqrt(l2);
                    tv2.setText(scanResults.get(i).level + " " + scanResults.get(i).SSID + "( " + k2 + " )");


                }

                if(scanResults.get(i).SSID.equals("E1"))
                {

                    rssiE1 = scanResults.get(i).level;

                    j1 = (((double) rssiE1*(-1)) - 45)/38;
                    k1 = Math.pow(10,j1);
//
//                    l1 = Math.pow(k1,2) - Math.pow(1.15,2);
//                    m1 = Math.sqrt(l1);

                    tv1.setText(scanResults.get(i).level + " " + scanResults.get(i).SSID + "( " + k1 + " )");
                }
            }

            double x = (Math.pow(3.73,2) + Math.pow(k1,2) - Math.pow(k2,2))/(2*3.73);
            double y = (Math.pow(3.5,2) + Math.pow(k1,2) - Math.pow(k3,2))/(2*3.5);
            tv4.setText(x + "," + y);

            //Log.e("rssi",newRssi+""+name);
            //tv.setText(newRssi + name + "\n" + newRssi1 + name1 + "\n" + newRssi2 + name2+ "\n" + newRssi3+name3);

        }};

    @Override
    public void onResume() {
        super.onResume();
        //Note: Not using RSSI_CHANGED_ACTION because it never calls me back.
        IntentFilter rssiFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(myRssiChangeReceiver, rssiFilter);

        WifiManager wifiMan=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiMan.startScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(myRssiChangeReceiver);
    }
}
