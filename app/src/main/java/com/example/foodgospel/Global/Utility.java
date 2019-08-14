package com.example.foodgospel.Global;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Utility {


    public static boolean hasCameraPermission(Context context) {
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && cameraPermissionCheck == PackageManager.PERMISSION_DENIED);
    }

    public static void requestCameraPermissions(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                requestCode);
    }

    public static void checkDirExistsifnotcreate(String path) {

        File myDirectory = new File(Environment.getExternalStorageDirectory(), path);
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
    }







/*
    public static void changeVisibility(EditText mPassword, ImageView mIcon) {
        if (mIcon.getTag().toString().equals("0")) {
            mIcon.setImageResource(R.drawable.password_eye);
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mIcon.setTag("1");
        } else {
            mIcon.setImageResource(R.drawable.show_password_eye);
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            mIcon.setTag("0");
        }
        mPassword.setSelection(mPassword.getText().length());
    }*/


    public static boolean isNotLogin(Context context) {
        String json = FoodGospelSharedPrefernce.getSharePrefernceData(context, "USERID");
        return json.equals("");
    }

    public static String getCurrentTime() {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a z", Locale.US);
        return dateFormatter.format(Calendar.getInstance().getTime());
    }


    public static void showLongSnack(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(10);
        snackbar.show();
    }

    public static void setViewBackgroundColor(Activity activity, View view, int color) {
        if (color == 0)
            return;
        if (view instanceof ImageButton) {
            AppCompatImageButton imageButton = (AppCompatImageButton) view;
            GradientDrawable drawable = (GradientDrawable) imageButton.getBackground();
            drawable.setColor(color);
            if (Build.VERSION.SDK_INT >= 16)
                imageButton.setBackground(drawable);
            else
                imageButton.setBackgroundDrawable(drawable);
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            GradientDrawable drawable = (GradientDrawable) imageView.getBackground();
            drawable.setColor(color);
            if (Build.VERSION.SDK_INT >= 16)
                imageView.setBackground(drawable);
            else
                imageView.setBackgroundDrawable(drawable);
        } else if (view instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) view;
            toolbar.setBackgroundColor(color);
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getDarkColor(color));
            }
        }
    }


    public static int getDarkColor(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb((int) (red * 0.8), (int) (green * 0.8), (int) (blue * 0.8));
    }

    public static int getLightColor(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb((int) (255 * 0.5), red, green, blue);
    }

    public static void initToolBar(AppCompatActivity activity, Toolbar toolbar, boolean homeUpIndicator) {
        activity.setSupportActionBar(toolbar);
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayHomeAsUpEnabled(homeUpIndicator);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
    }

    public static boolean hasCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    public static boolean hasCameraFlashHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH);
    }

    public static void showShortSnack(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(10);
        snackbar.show();
    }


    public static boolean validateGrantedPermissions(int[] grantResults) {
        boolean isGranted = true;
        if (grantResults != null && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    isGranted = false;
                    break;
                }
            }
            return isGranted;
        } else {
            isGranted = false;
            return isGranted;
        }
    }

    public static void requestStoragePermissions(Activity activity, int requestCode) {
        int hasReadPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<>();
        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
        }
    }

    public static boolean hasStoragePermission(Context context) {
        int writePermissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (writePermissionCheck == PackageManager.PERMISSION_DENIED
                || readPermissionCheck == PackageManager.PERMISSION_DENIED));
    }

    public static void alertConfirm(final Activity c, String title, String message, final int requestcode, final Fragment f) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Go to Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", c.getPackageName(), null);
                intent.setData(uri);
                f.startActivityForResult(intent, requestcode);
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public static void alertConfirm(final Activity c, String title, String message, final int requestcode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Go to Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", c.getPackageName(), null);
                intent.setData(uri);
                c.startActivityForResult(intent, requestcode);
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

  /*  public static void showconnectiondialog(final Context c, final Fragment f) {
        final Dialog nointernetdialog;
        nointernetdialog = new Dialog(c);
        nointernetdialog.setContentView(R.layout.no_internet);
        final PetHealthTextView refresh = nointernetdialog.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkconnection(c)) {
                    if (f != null) {
                        FragmentTransaction ft = ((AppCompatActivity) c).getSupportFragmentManager().beginTransaction();
                        ft.detach(f).attach(f).commit();


                     *//*   else if (f instanceof AddressFragment)
                            ((AddressFragment)f).getCity();*//*

                    } else {

                        //for f=null means activity
                       *//* if (c instanceof MainActivity)
                            ((MainActivity) c).init();*//*

                    }
                    nointernetdialog.cancel();
                } else nointernetdialog.show();

            }
        });
        nointernetdialog.setCancelable(false);
        nointernetdialog.show();
    }*/


    public static boolean checkconnection(Context c) {
        return ConnectivityReceiver.isConnected(c);
    }

    public static boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }


}
