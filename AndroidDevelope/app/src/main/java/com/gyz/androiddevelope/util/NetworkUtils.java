package com.gyz.androiddevelope.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

public class NetworkUtils {
    public static final String WIFI = "Wi-Fi";
    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
    private static final String TAG = "NetworkUtils";
    private static ConnectivityManager sConnManager = null;
    private static final int[] WEAK_NETWORK_GROUP = new int[]{4, 7, 2, 1};

    public NetworkUtils() {
    }

    public static boolean isConnected(Context var0) {
        ConnectivityManager var1 = getConnManager(var0);
        if(var1 != null) {
            try {
                NetworkInfo var2 = var1.getActiveNetworkInfo();
                if(var2 != null) {
                    return var2.isConnected();
                }
            } catch (Exception var3) {
                Log.e("NetworkUtils", var3.toString());
            }
        } else {
            Log.e("NetworkUtils", "connManager is null!");
        }

        return false;
    }

    public static boolean isConnectedToWeakNetwork(Context var0) {
        ConnectivityManager var1 = getConnManager(var0);
        if(var1 != null) {
            try {
                NetworkInfo var2 = var1.getActiveNetworkInfo();
                if(var2 != null) {
                    int var3 = var2.getSubtype();

                        Log.d("NetworkUtils", "subType:" + var3 + ": name:" + var2.getSubtypeName());


                    int[] var4 = WEAK_NETWORK_GROUP;
                    int var5 = var4.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        int var7 = var4[var6];
                        if(var7 == var3) {
                            return true;
                        }
                    }
                } else {
                    Log.e("NetworkUtils", "networkInfo is null!");
                }
            } catch (Exception var8) {
                Log.e("NetworkUtils", var8.toString());
            }
        } else {
            Log.e("NetworkUtils", "connManager is null!");
        }

        return false;
    }

    public static ConnectivityManager getConnManager(Context var0) {
        if(var0 == null) {
            Log.e("NetworkUtils", "context is null!");
            return null;
        } else {
            if(sConnManager == null) {
                sConnManager = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
            }

            return sConnManager;
        }
    }

    public static String[] getNetworkState(Context var0) {
        String[] var1 = new String[]{"Unknown", "Unknown"};

        try {
            PackageManager var2 = var0.getPackageManager();
            if(var2.checkPermission("android.permission.ACCESS_NETWORK_STATE", var0.getPackageName()) != 0) {
                var1[0] = "Unknown";
                return var1;
            }

            ConnectivityManager var3 = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(var3 == null) {
                var1[0] = "Unknown";
                return var1;
            }

            NetworkInfo var4 = var3.getNetworkInfo(1);
            if(var4 != null && var4.getState() == NetworkInfo.State.CONNECTED) {
                var1[0] = "Wi-Fi";
                return var1;
            }

            NetworkInfo var5 = var3.getNetworkInfo(0);
            if(var5 != null && var5.getState() == NetworkInfo.State.CONNECTED) {
                var1[0] = "2G/3G";
                var1[1] = var5.getSubtypeName();
                return var1;
            }
        } catch (Exception var6) {
            ;
        }

        return var1;
    }

    public static String getWifiAddress(Context var0) {
        if(var0 != null) {
            WifiManager var1 = (WifiManager)var0.getSystemService(Context.WIFI_SERVICE);
            WifiInfo var2 = var1.getConnectionInfo();
            if(var2 != null) {
                String var3 = var2.getMacAddress();
                if(TextUtils.isEmpty(var3)) {
                    var3 = "00-00-00-00-00-00";
                }

                return var3;
            } else {
                return "00-00-00-00-00-00";
            }
        } else {
            return "00-00-00-00-00-00";
        }
    }

    private static String _convertIntToIp(int var0) {
        return (var0 & 255) + "." + (var0 >> 8 & 255) + "." + (var0 >> 16 & 255) + "." + (var0 >> 24 & 255);
    }

    public static String getWifiIpAddress(Context var0) {
        if(var0 != null) {
            try {
                WifiManager var1 = (WifiManager)var0.getSystemService(Context.WIFI_SERVICE);
                WifiInfo var2 = var1.getConnectionInfo();
                if(var2 != null) {
                    return _convertIntToIp(var2.getIpAddress());
                }

                return null;
            } catch (Exception var3) {
                ;
            }
        }

        return null;
    }

    public static boolean isWifi(Context var0) {
        if(var0 != null) {
            try {
                if(getNetworkState(var0)[0].equals("Wi-Fi")) {
                    return true;
                }
            } catch (Exception var2) {
                ;
            }
        }

        return false;
    }
}
