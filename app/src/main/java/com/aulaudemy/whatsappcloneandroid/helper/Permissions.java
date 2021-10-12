package com.aulaudemy.whatsappcloneandroid.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    public static boolean validatePermissions (Activity activity, String[] permissions, int requestCode) {

        if(Build.VERSION.SDK_INT >= 23) {
            Log.i("SDK", "SDK >= 23");
            List<String> permissionsList = new ArrayList<String>();
            //Percorre as permissões verificando se já estão liberadas
            for(String permission: permissions) {
                boolean validatePermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if(!validatePermission) {
                    permissionsList.add(permission);
                    Log.i("PERMISSION", "Permission not granted:" +permission.toString());
                }
            }

            if(permissionsList.isEmpty()) return true;

            String[] convertPermissionsListToArray = new String[permissionsList.size()];
            permissionsList.toArray(convertPermissionsListToArray);
            Log.i("PERMISSION", "Permission array: "+convertPermissionsListToArray.toString());

            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
        return true;
    }
}
