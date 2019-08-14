package com.example.foodgospel.Global;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.foodgospel.R;


public class GlobalHandler extends AppCompatActivity {


    public static void showconnectiondialog(final Context c, final Fragment f) {
        final Dialog nointernetdialog;
        nointernetdialog = new Dialog(c, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        nointernetdialog.setContentView(R.layout.no_internet);
        final TextView refresh = nointernetdialog.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.checkconnection(c)) {
                    if (f != null) {
                        FragmentTransaction ft = ((AppCompatActivity) c).getSupportFragmentManager().beginTransaction();
                        ft.detach(f).attach(f).commit();
                    }
                    nointernetdialog.cancel();
                } else nointernetdialog.show();

            }
        });
        nointernetdialog.setCancelable(false);
        nointernetdialog.show();
    }

    public static boolean checkconnection(Context c) {
        if (!ConnectivityReceiver.isConnected(c)) {
            return false;
        }
        return true;
    }


    //for mobile validation
    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    //for email validation
    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}



