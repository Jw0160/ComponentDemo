package com.common.common_base.utils.system;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/18
 * @desc :
 */

public class AppUtil{

    /**
     * Get traffic data
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    @SuppressLint("NewApi")
    public static String getTrafficInfo() {
        long receive = TrafficStats.getTotalRxBytes() / 1024;
        long send = TrafficStats.getTotalTxBytes() / 1024;
        return "接受" + receive + "kb \n" + "发送" + send + "kb \n" + "总共" + (receive + send) + "kb";
    }

    /**
     * Access to the phone connection
     *
     * @return
     */
    public static String getBrandModel() {
        String brandmodel = Build.BRAND + " " + Build.MODEL;
        if (brandmodel.trim().equals("")){
            brandmodel = "unkown";
        }
        return brandmodel;
    }

    /**
     * Cell phone information
     *
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取设备id
     */
    public final static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.
                getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {

        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void startInstall(@NonNull Context context, @NonNull String filePath) throws Exception {
        context.startActivity(getInstallIntent(context, filePath));
    }

    public static void startInstall(@NonNull Context context, @NonNull File pFile) throws Exception {
        context.startActivity(getInstallIntent(context, pFile));
    }

    public static Intent getInstallIntent(@NonNull Context context, @NonNull String filePath) throws Exception {
        return getInstallIntent(context, new File(filePath));
    }

    public static Intent getInstallIntent(@NonNull Context context, @NonNull File pFile) throws Exception {

        Intent updateIntent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本

        updateIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        updateIntent.setDataAndType(getUriForVersion(context, pFile), "application/vnd.android.package-archive");
        updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return updateIntent;
    }


    public static Uri getUriForVersion(Context mContext, String mFilePath) throws Exception {
        return getUriForVersion(mContext, new File(mFilePath));
    }

    public static Uri getUriForVersion(Context mContext, File mFile) throws Exception {
        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= 25&& mContext.getApplicationInfo().targetSdkVersion >= 25) {
            contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", mFile);
        } else {
            contentUri = Uri.fromFile(mFile);
        }

        return contentUri;
    }


    public static boolean getIsArtInUse() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }

    private static final String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
    private static final String LIB_DALVIK = "libdvm.so";
    private static final String LIB_ART = "libart.so";
    private static final String LIB_ART_D = "libartd.so";

    public static CharSequence getCurrentRuntimeValue() {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get",
                        String.class, String.class);
                if (get == null) {
                    return "WTF?!";
                }
                try {
                    final String value = (String) get.invoke(
                            systemProperties, SELECT_RUNTIME_PROPERTY,
                        /* Assuming default is */"Dalvik");
                    if (LIB_DALVIK.equals(value)) {
                        return "D";
                    } else if (LIB_ART.equals(value)) {
                        return "A";
                    } else if (LIB_ART_D.equals(value)) {
                        return "AD";
                    }

                    return value;
                } catch (IllegalAccessException e) {
                    return "IllegalAccessException";
                } catch (IllegalArgumentException e) {
                    return "IllegalArgumentException";
                } catch (InvocationTargetException e) {
                    return "InvocationTargetException";
                }
            } catch (NoSuchMethodException e) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (ClassNotFoundException e) {
            return "SystemProperties class is not found";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 25) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
