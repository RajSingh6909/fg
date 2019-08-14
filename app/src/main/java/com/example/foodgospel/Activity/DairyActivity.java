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

import com.example.foodgospel.Fragments.AddDairyStepTwoFragment;
import com.example.foodgospel.Fragments.AddFarmerFragment;
import com.example.foodgospel.Global.AddDairyAllData;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

public class DairyActivity extends AppCompatActivity {

    private EditText etFarmerName, etDairyName, etCompanyName,etLandline, etMobile, etWhatsappNo;
    private Button btnNext;
    private RadioButton rbSelectedValue, rbSMS, rbCall, rbWhatsapp, rbEmail;;
    private RadioGroup rgMain;
    private AddDairyAllData objAddDairyAllData;
    private Boolean isEdit;
    private UserFarmer objFarmer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dairy Farm");
        setSupportActionBar(toolbar);

        etFarmerName = findViewById(R.id.etFarmerName);
        etDairyName = findViewById(R.id.etDairyName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etLandline = findViewById(R.id.etLandline);
        etMobile = findViewById(R.id.etMobile);
        etWhatsappNo = findViewById(R.id.etWhatsappNo);
        rgMain = findViewById(R.id.rgMain);
        rbSMS = findViewById(R.id.rbSMS);
        rbCall = findViewById(R.id.rbCall);
        rbWhatsapp = findViewById(R.id.rbWhatsapp);
        rbEmail = findViewById(R.id.rbEmail);
        btnNext = findViewById(R.id.btnNext);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            objFarmer = (UserFarmer) getIntent().getSerializableExtra("DairyEditData");
            if (objFarmer.getName().isEmpty()) {
                etFarmerName.setText("No Data");
            } else {
                etFarmerName.setText(objFarmer.getName());
            }
            etDairyName.setText(objFarmer.getFarmName());
            etCompanyName.setText(objFarmer.getCompanyName());

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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFields())
                {
                    if (rgMain.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "Please select radio button!", Toast.LENGTH_LONG).show();
                    } else {
                        int selectedId = rgMain.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        rbSelectedValue = (RadioButton) findViewById(selectedId);

                    }

                    //bind data into bundle for passing on next activity
                    objAddDairyAllData = new AddDairyAllData();
                    objAddDairyAllData.setFarm_type(2);
                    objAddDairyAllData.setName(etFarmerName.getText().toString());
                    objAddDairyAllData.setFarm_name(etFarmerName.getText().toString());
                    objAddDairyAllData.setCompany_name(etCompanyName.getText().toString());
                    objAddDairyAllData.setContact_mode(rbSelectedValue.getText().toString());
                    objAddDairyAllData.setLandline(etLandline.getText().toString());
                    objAddDairyAllData.setMobile(etMobile.getText().toString());
                    objAddDairyAllData.setWhatsapp(etWhatsappNo.getText().toString());

                    //Fragment calling step-2
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddDairyStepTwoFragment objAddDairyStepTwoFragment = new AddDairyStepTwoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DairyStepOne", objAddDairyAllData);
                    bundle.putSerializable("DairyEditStepOne", objFarmer);
                    bundle.putBoolean("isEdit", isEdit);
                    objAddDairyStepTwoFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, objAddDairyStepTwoFragment).commit();
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

        if (etCompanyName.getText().toString().isEmpty()) {
            etCompanyName.setError("Company Name cannot be empty.");
            etCompanyName.startAnimation(shake);
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
