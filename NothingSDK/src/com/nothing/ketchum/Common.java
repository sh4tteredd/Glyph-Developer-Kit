package com.nothing.ketchum;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

public class Common {
    private static final String TAG = "GlyphManager";

    private static final int NOTHING_SDK_VERSION = 140101;

    private static ArrayList mVerifyTragetDevice = new ArrayList(Arrays.asList((Object[])new String[] { "A063", "A065", "AIN065" }));

    public static String DEVICE_20111 = "A063";

    public static String DEVICE_22111 = "A065";

    public static boolean is20111() {
        if (Glyph.DEVICE_20111.equals(Build.MODEL))
            return true;
        return false;
    }

    public static boolean is22111() {
        if (Glyph.DEVICE_22111.equals(Build.MODEL) || Glyph.DEVICE_22111I.equals(Build.MODEL))
            return true;
        return false;
    }

    static boolean isSDKAvaliable(Context context) {
        int appTargetSdkVerion = getAppTargetSDKVersion(context);
        return !(appTargetSdkVerion >= 140101);
    }

    static int getAppTargetSDKVersion(Context context) {
        return (context.getApplicationInfo()).metaData.getInt("targetNothingSDKVersion");
    }

    static String getAppKey(Context context) {
        String result = null;
        try {
            result = (context.getPackageManager().getApplicationInfo(context.getPackageName(), 128)).metaData.getString("NothingKey");
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            Log.e("GlyphManager", e.getMessage());
        }
        return result;
    }

    static String getTargetDevice(Context context) {
        String result = null;
        try {
            result = (context.getPackageManager().getApplicationInfo(context.getPackageName(), 128)).metaData.getString("TargetDevice");
            if (!isCorrectTargetDevice(result))
                result = null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            Log.e("GlyphManager", e.getMessage());
        }
        return result;
    }

    static int getSDKVersion() {
        return 140101;
    }

    private static boolean isCorrectTargetDevice(String value) {
        if (mVerifyTragetDevice.contains(value))
            return true;
        return false;
    }

    static int getTargetDeviceGlyphChannelSize(String targetDevice) {
        if (isTargetDevice20111(targetDevice))
            return Glyph.DEVICE_20111_SIZE;
        if (isTargetDevice22111(targetDevice))
            return Glyph.DEVICE_22111_SIZE;
        return Glyph.DEVICE_22111_SIZE;
    }

    static boolean isTargetDevice20111(String targetDevice) {
        if (Glyph.DEVICE_20111.equals(targetDevice))
            return true;
        return false;
    }

    static boolean isTargetDevice22111(String targetDevice) {
        if (Glyph.DEVICE_22111.equals(targetDevice))
            return true;
        return false;
    }
}