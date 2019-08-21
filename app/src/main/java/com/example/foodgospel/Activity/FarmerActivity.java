package com.example.foodgospel.Activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.foodgospel.Fragments.AddFarmerFragment;
import com.example.foodgospel.Fragments.AddFarmerStepFourFragment;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.ConnectivityReceiver;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.Models.Farmer;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

public class FarmerActivity extends AppCompatActivity {

    private EditText etFarmerName, etFarmName, etLandline, etMobile, etEmail, etWhatsappNo;
    private Button btnNext;
    private RadioButton rbSelectedValue, rbSMS, rbCall, rbWhatsapp, rbEmail;
    private RadioGroup rgMain;
    private AddFarmerAllData objAddFarmerAllData;
    private UserFarmer objFarmer = null;
    Boolean isEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Farmer");
        setSupportActionBar(toolbar);

        etFarmerName = findViewById(R.id.etFarmerName);
        etFarmName = findViewById(R.id.etFarmName);
        etLandline = findViewById(R.id.etLandline);
        etMobile = findViewById(R.id.etMobile);
        etWhatsappNo = findViewById(R.id.etWhatsappNo);
        rbSMS = findViewById(R.id.rbSMS);
        rbCall = findViewById(R.id.rbCall);
        rbWhatsapp = findViewById(R.id.rbWhatsapp);
        rbEmail = findViewById(R.id.rbEmail);

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            objFarmer = (UserFarmer) getIntent().getSerializableExtra("FarmerEditData");
            etFarmerName.setText(objFarmer.getName()); //Farmer name

            etFarmName.setText(objFarmer.getFarmName()); //Farm NameFarmerEditData
            if (objFarmer.getContactMode().equalsIgnoreCase("SMS")) {
                rbSMS.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("CALL")) {
                rbCall.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("WHATSAPP")) {
                rbWhatsapp.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("EMAIL")) {
                rbEmail.setChecked(true);
            }
            etLandline.setText(objFarmer.getLandline());
            etMobile.setText(objFarmer.getMobile());
            etWhatsappNo.setText(objFarmer.getWhatsapp());
        }


        //RadioGroup
        rgMain = findViewById(R.id.rgMain);

        btnNext = findViewById(R.id.btnNext);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rgMain.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(), "Please select radio button!", Toast.LENGTH_LONG).show();
                } else {
                    int selectedId = rgMain.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    rbSelectedValue = (RadioButton) findViewById(selectedId);

                }

                if (checkAllFields()) {

                    //Bind data into bundle
                    objAddFarmerAllData = new AddFarmerAllData();
                    objAddFarmerAllData.setFarm_type(1);
                    objAddFarmerAllData.setName(etFarmerName.getText().toString());
                    objAddFarmerAllData.setFarm_name(etFarmName.getText().toString());
                    objAddFarmerAllData.setContact_mode(rbSelectedValue.getText().toString());
                    objAddFarmerAllData.setLandline(etLandline.getText().toString());
                    objAddFarmerAllData.setMobile(etMobile.getText().toString());
                    objAddFarmerAllData.setWhatsapp(etWhatsappNo.getText().toString());

                    //fragment calling step-2
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddFarmerFragment objAddFarmerFragment = new AddFarmerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FarmerStepOne", objAddFarmerAllData);
                    bundle.putSerializable("FarmerEditStepOne", objFarmer);
                    bundle.putBoolean("isEdit", isEdit);
                    objAddFarmerFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, objAddFarmerFragment).commit();
                    fragmentTransaction.addToBackStack(null);
                }
            }

        });

    }

    private boolean checkAllFields() {
        String Mobile = etMobile.getText().toString();
        boolean flag = true;
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        if (etFarmerName.getText().toString().isEmpty()) {
            etFarmerName.setError("Farmer Name cannot be empty.");
            etFarmerName.startAnimation(shake);
            flag = false;
        }

        if (rgMain.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(getApplicationContext(), "Please select radio button!", Toast.LENGTH_LONG).show();
        } else {
            // one of the radio buttons is checked
            int selectedId = rgMain.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton rbSelection = (RadioButton) findViewById(selectedId);

        }

        if (etMobile.getText().toString().isEmpty()) {
            etMobile.setError("Mobile Number cannot be empty.");
            etMobile.startAnimation(shake);
            flag = false;
        } else if (Mobile.length() != 10) {
            etMobile.setError("Invalid Phone Number.");
            etMobile.startAnimation(shake);
            flag = false;
        }

        return flag;
    }
}
