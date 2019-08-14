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
import com.example.foodgospel.Fragments.AddPoultryFarmStepTwoFragment;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

public class PoultryFarmActivity extends AppCompatActivity {

    private EditText etFarmerName, etPoultryFarmName, etCompanyName, etLandline, etMobile, etWhatsappNo;
    private Button btnNext;
    private RadioButton rbSelectedValue, rbSMS, rbCall, rbWhatsapp, rbEmail;
    private RadioGroup rgMain;
    private AddPoultryFarmAllData objAddPoultryFarmAllData;
    private Boolean isEdit;
    private UserFarmer objFarmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poultry_farm);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Poultry Farm");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etFarmerName = findViewById(R.id.etFarmerName);
        etPoultryFarmName = findViewById(R.id.etPoultryFarmName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etLandline = findViewById(R.id.etLandline);
        etMobile = findViewById(R.id.etMobile);
        etWhatsappNo = findViewById(R.id.etWhatsappNo);
        rgMain = findViewById(R.id.rgMain);
        btnNext = findViewById(R.id.btnNext);
        rbSMS = findViewById(R.id.rbSMS);
        rbCall = findViewById(R.id.rbCall);
        rbWhatsapp = findViewById(R.id.rbWhatsapp);
        rbEmail = findViewById(R.id.rbEmail);

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            objFarmer = (UserFarmer) getIntent().getExtras().getSerializable("PoultryEditData");
            etFarmerName.setText(objFarmer.getName());
            etPoultryFarmName.setText(objFarmer.getFarmName());
            etCompanyName.setText(objFarmer.getCompanyName());
            etLandline.setText(objFarmer.getLandline());
            etMobile.setText(objFarmer.getMobile());
            etWhatsappNo.setText(objFarmer.getWhatsapp());
            if (objFarmer.getContactMode().equalsIgnoreCase("SMS")) {
                rbSMS.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("CALL")) {
                rbCall.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("WHATSAPP")) {
                rbWhatsapp.setChecked(true);
            } else if (objFarmer.getContactMode().equalsIgnoreCase("EMAIL")) {
                rbEmail.setChecked(true);
            }

        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllFields()) {

                    if (rgMain.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        Toast.makeText(getApplicationContext(), "Please select radio button!", Toast.LENGTH_LONG).show();
                    } else {
                        int selectedId = rgMain.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        rbSelectedValue = (RadioButton) findViewById(selectedId);

                    }

                    objAddPoultryFarmAllData = new AddPoultryFarmAllData();
                    objAddPoultryFarmAllData.setFarm_type(3);
                    objAddPoultryFarmAllData.setName(etFarmerName.getText().toString());
                    objAddPoultryFarmAllData.setPoultry_farm_name(etPoultryFarmName.getText().toString());
                    objAddPoultryFarmAllData.setCompany_name(etCompanyName.getText().toString());
                    objAddPoultryFarmAllData.setContact_mode(rbSelectedValue.getText().toString());
                    objAddPoultryFarmAllData.setLandline(etLandline.getText().toString());
                    objAddPoultryFarmAllData.setMobile(etMobile.getText().toString());
                    objAddPoultryFarmAllData.setWhatsapp(etWhatsappNo.getText().toString());

                    //Fragment calling step-2
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddPoultryFarmStepTwoFragment objPoultryFarmStepTwoFragment = new AddPoultryFarmStepTwoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PoultryStepOne", objAddPoultryFarmAllData);
                    bundle.putSerializable("PoultryEditStepOne", objFarmer);
                    bundle.putBoolean("isEdit", isEdit);
                    objPoultryFarmStepTwoFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, objPoultryFarmStepTwoFragment).commit();
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
