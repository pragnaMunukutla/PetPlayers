package com.example.satayam.mypetplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

public final class Util {
    /**
     * Like {@link android.os.Build.VERSION#SDK_INT}, but in a place where it can be conveniently
     * overridden for local testing.
     */
    public static final int SDK_INT = Build.VERSION.SDK_INT;

    /**
     * Like {@link Build#DEVICE}, but in a place where it can be conveniently overridden for local
     * testing.
     */
    public static final String DEVICE = Build.DEVICE;

    /**
     * Like {@link Build#MANUFACTURER}, but in a place where it can be conveniently overridden for
     * local testing.
     */
    public static final String MANUFACTURER = Build.MANUFACTURER;

    /**
     * Like {@link Build#MODEL}, but in a place where it can be conveniently overridden for local
     * testing.
     */
    public static final String MODEL = Build.MODEL;

    /**
     * A concise description of the device that it can be useful to log for debugging purposes.
     */
    public static final String DEVICE_DEBUG_INFO = DEVICE + ", " + MODEL + ", " + MANUFACTURER + ", "
            + SDK_INT;

    /**
     * Returns true if the URI is a path to a local file or a reference to a local file.
     *
     * @param uri The uri to test.
     */
    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return TextUtils.isEmpty(scheme) || "file".equals(scheme);
    }

    /**
     * Checks whether it's necessary to request the {@link Manifest.permission#READ_EXTERNAL_STORAGE}
     * permission read the specified {@link Uri}s, requesting the permission if necessary.
     *
     * @param activity The host activity for checking and requesting the permission.
     * @param uris {@link Uri}s that may require {@link Manifest.permission#READ_EXTERNAL_STORAGE} to read.
     * @return Whether a permission request was made.
     */
    @TargetApi(23)
    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, Uri... uris) {
        if (Util.SDK_INT < 23) {
            return false;
        }
        for (Uri uri : uris) {
            if (Util.isLocalFileUri(uri)) {
                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static String convertTime(String duration){
        long millis = Long.valueOf(duration);
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    @TargetApi(23)
    public static boolean maybeRequestReadExternalStoragePermission(Activity activity) {
        if (Util.SDK_INT < 23) {
            return false;
        }

        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return true;
        }

        return false;
    }

    @TargetApi(23)
    public static boolean maybeRequestWriteExternalStoragePermission(Activity activity) {
        if (Util.SDK_INT < 23) {
            return false;
        }

        if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return true;
        }

        return false;
    }

    @TargetApi(23)
    public static boolean maybeRequestInternetPermission(Activity activity) {
        if (Util.SDK_INT < 23) {
            return false;
        }

        if (activity.checkSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[] {Manifest.permission.INTERNET}, 0);
            return true;
        }

        return false;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
