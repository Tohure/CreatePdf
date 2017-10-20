package pe.tohure.createpdftest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static int REQUEST_GROUP_PERMISSIONS = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestPermission();
    }

    public void requestGroupPermission(List<String> permissions) {
        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);
        ActivityCompat.requestPermissions(SplashActivity.this, permissionList, REQUEST_GROUP_PERMISSIONS);
    }

    public void requestPermission() {
        List<String> permissionNeeded = new ArrayList<>();
        List<String> permissionAvailable = new ArrayList<>();

        permissionAvailable.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        for (String permission : permissionAvailable) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(permission);
            }
        }

        if (permissionNeeded.isEmpty()) {
            callFormActivity();
        } else {
            requestGroupPermission(permissionNeeded);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GROUP_PERMISSIONS) {
            String result = "";
            int i = 0;
            int numberPermissions = permissions.length;
            for (String perm : permissions) {
                String status;
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    status = "GRANTED";
                    numberPermissions--;
                } else {
                    status = "DENIED";
                }
                result = result + "\n" + perm + " : " + status;
                i++;
            }

            if (numberPermissions == 0) {
                callFormActivity();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permisos");
                builder.setMessage(result);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    private void callFormActivity() {
        Intent intent = new Intent(this, FormularioActivity.class);
        startActivity(intent);
        finish();
    }
}
