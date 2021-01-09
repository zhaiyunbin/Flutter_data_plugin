
package com.ecook.flutterdataplugin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


public class MachineManger {
    private static final String DEFAULT_ANDROID_ID = "9774d56d682e549c";
    private static final String MACHINE_ID = "MACHINE_ID";
    public static final String SEX_TYPE = "SEX_TYPE";
    public static final String USER_SESSION = "USER_SESSION";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_COVER = "USER_COVER";
    public static final String FEEDBACK_APP_ID= "FEEDBACK_APP_ID";
    public static final String FEEDBACK_QQ_GROUP= "FEEDBACK_QQ_GROUP";
    private static MachineManger instance;
    private String machine;

    public MachineManger() {
    }

    public static MachineManger instance() {
        if (instance == null) {
            synchronized (MachineManger.class) {
                if (instance == null) {
                    instance = new MachineManger();
                }
            }
        }
        return instance;
    }

    /**
     * 获取Machine
     */
    public String getMachine( Context context) {
        // 如果内存中的值不为空，则获取内存中的值
        if (!TextUtils.isEmpty(machine)) {
            return machine;
        }
        // 如果内存中的值为空，则先获取SP持久化的值
        machine = getSpMachine(context);
        if (!TextUtils.isEmpty(machine)) {
            return machine;
        }
        // 如果本地持久化都为空，则获取新的值并本地持久化
        machine = getNewMachine(context);
        putSpMachine(context, machine);
        return machine;
    }

    private SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public String getSexType(Context context) {
        try {
            return getSP(context).getString(SEX_TYPE, "0");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }


    public String getAppId(Context context) {
        try {
            return getSP(context).getString(FEEDBACK_APP_ID, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getQQGroup(Context context) {
        try {
            return getSP(context).getString(FEEDBACK_QQ_GROUP, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSession(Context context) {
        try {
            return getSP(context).getString(USER_SESSION, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getUserName(Context context) {
        try {
            return getSP(context).getString(USER_NAME, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getUserCover(Context context) {
        try {
            return getSP(context).getString(USER_COVER, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 键值对类型持久化字符串
     *
     * @param value ：值
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void putSpMachine(Context context, String value) {
        try {
            getSP(context).edit().putString(MACHINE_ID, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据键获取持久化的值
     *
     * @return ：值
     */
    public String getSpMachine(Context context) {
        try {
            return getSP(context).getString(MACHINE_ID, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取新的Machine值
     *
     * @param context
     * @return
     */
    public String getNewMachine( Context context) {
        Context applicationContext = context.getApplicationContext();

        String imei = getIMEI(applicationContext);
        String mac = getMAC(applicationContext);
        String deviceId = getAndroidId(applicationContext);

        String id;
        if (imei != null) {
            if (deviceId != null) {
                id = imei + deviceId;
            } else {
                id = imei;
            }
        } else if (deviceId != null) {
            id = deviceId;
        } else {
            id = getUUID();
        }
        id = md5(id);
        return id;
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

    public String getIMEI( Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMAC( Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public String getAndroidId(Context context) {
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!DEFAULT_ANDROID_ID.equals(androidId)) {
                return androidId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String md5(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte[] encodedValue = md5.digest();
            int j = encodedValue.length;
            char[] finalValue = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte encoded = encodedValue[i];
                finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
                finalValue[k++] = hexDigits[encoded & 0xf];
            }
            return new String(finalValue);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }
}

