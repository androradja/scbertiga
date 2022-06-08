package com.musicapps.tubidyeaskekuli;

import android.Manifest;
import android.app.Activity;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ToolsPermision {
   static String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQCODE = 123;
    @AfterPermissionGranted(REQCODE)
    public  static void  Build(Activity activity){
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Have permission, do the thing!
//            Toast.makeText(activity, "TODO: Camera things", Toast.LENGTH_LONG).show();


        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    activity,
                    "This app needs access to your Write External.",
                    REQCODE,
                    perms);
        }

    }


}
