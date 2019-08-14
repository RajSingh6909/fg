package com.example.foodgospel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodgospel.Activity.FarmerActivity;
import com.example.foodgospel.Adapters.HarvestMonthArrayAdapter;
import com.example.foodgospel.Adapters.LanguageArrayAdapter;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.ConnectivityReceiver;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.Models.Language;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AddFarmerFragment extends Fragment {

    Spinner spQualification, spLanguage, spState, spDistrict;
    Button btnNext;
    SharedPreferences mPrefs;
    EditText etMarketEmail, etEmail, etAge;
    RadioGroup rgMain;
    RadioButton rbMale, rbFemale;
    int SelectedLanguageId;
    String SelectedQualificationName;
    int selectedId;
    String rbSelectedValue = " ";
    Boolean isEditStepOne;
    private UserFarmer objFarmer = null;


    public AddFarmerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_farmer, container, false);
        spQualification = view.findViewById(R.id.spQualification);
        spLanguage = view.findViewById(R.id.spLanguage);
        etMarketEmail = view.findViewById(R.id.etMarketEmail);
        etEmail = view.findViewById(R.id.etEmail);
        etAge = view.findViewById(R.id.etAge);

        rgMain = view.findViewById(R.id.rgMain);
        rbFemale = view.findViewById(R.id.rbFemale);
        rbMale = view.findViewById(R.id.rbMale);

        mPrefs = getActivity().getSharedPreferences("SectorPref", MODE_PRIVATE);

        final AddFarmerAllData objAllFarmerData = (AddFarmerAllData) getArguments().getSerializable("FarmerStepOne");

        final ArrayList<Language> arlLanguage = new ArrayList<>();
        arlLanguage.add(new Language(0, "Select Language"));
        arlLanguage.add(new Language(1, "Marathi"));
        arlLanguage.add(new Language(2, "Hindi"));
        arlLanguage.add(new Language(3, "English"));
        arlLanguage.add(new Language(4, "Tamil"));
        arlLanguage.add(new Language(5, "Bengali"));
        arlLanguage.add(new Language(6, "Telugu"));
        arlLanguage.add(new Language(7, "Punjabi"));

        final ArrayList<String> arlQualification = new ArrayList<>();
        arlQualification.add(0, "Select Qualification");
        arlQualification.add(1, "ILLITERATE");
        arlQualification.add(2, "SEMI-LITERATE");
        arlQualification.add(3, "PRIMARY");
        arlQualification.add(4, "MIDDLE");
        arlQualification.add(5, "10TH / 12TH");
        arlQualification.add(6, "GRADUATE");
        arlQualification.add(7, "MASTERS");
        arlQualification.add(8, "PHD");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlQualification);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        isEditStepOne = getArguments().getBoolean("isEdit", false);
        spLanguage.setAdapter(new LanguageArrayAdapter(getActivity(), R.layout.item_spinner, arlLanguage));
        spQualification.setAdapter(adapter);

        if (isEditStepOne) {
            objFarmer = (UserFarmer) getArguments().getSerializable("FarmerEditStepOne");
            etEmail.setText(objFarmer.getEmail().trim());
            etMarketEmail.setText(objFarmer.getMarketEmail().trim());
            spLanguage.setAdapter(new LanguageArrayAdapter(getActivity(), R.layout.item_spinner, arlLanguage));
            if (objFarmer.getLanguage() == null) {
                spLanguage.setSelection(0);
            } else {
                spLanguage.setSelection(objFarmer.getLanguage().getId());
            }

            etAge.setText(objFarmer.getAge().trim());
            if (objFarmer.getSex().equalsIgnoreCase("Male")) {
                rbMale.setChecked(true);
            } else if (objFarmer.getSex().equalsIgnoreCase("Female")) {
                rbFemale.setChecked(true);
            }

            if (objFarmer.getQualification() == null) {
                spQualification.setSelection(0);
            } else {
                spQualification.setAdapter(adapter);
                int getPosition = adapter.getPosition(objFarmer.getQualification());
                spQualification.setSelection(getPosition);
            }
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 2");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        spQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    SelectedQualificationName = parent.getSelectedItem().toString();
                    Toast.makeText(getActivity(), "Selected Qualification" + SelectedQualificationName, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    SelectedLanguageId = arlLanguage.get(position).getId();
                    Toast.makeText(getActivity(), "Selected Language id is : " + SelectedLanguageId, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbFemale.isChecked()) {
                    rbSelectedValue = rbFemale.getText().toString();
                } else if (rbMale.isChecked()) {
                    rbSelectedValue = rbMale.getText().toString();
                }

                if (checkAllFields()) {
                    objAllFarmerData.setEmail(etEmail.getText().toString());
                    objAllFarmerData.setMarket_email(etMarketEmail.getText().toString());
                    objAllFarmerData.setLanguage(SelectedLanguageId);
                    objAllFarmerData.setAge(etAge.getText().toString());
                    objAllFarmerData.setQualification(SelectedQualificationName);
                    objAllFarmerData.setSex(rbSelectedValue);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddFarmerStepThreeFragment objAddFarmerStepThreeFragment = new AddFarmerStepThreeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FarmerStepTwo", objAllFarmerData);
                    bundle.putSerializable("FarmerEditStepTwo", objFarmer);
                    bundle.putBoolean("isEditStepTwo", isEditStepOne);
                    objAddFarmerStepThreeFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frameLayout, objAddFarmerStepThreeFragment).commit();
                    fragmentTransaction.addToBackStack(null);
                }

            }
        });

        return view;
    }

    private boolean checkAllFields() {
        String Email = etEmail.getText().toString();
        boolean flag = true;
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Email Name cannot be empty.");
            etEmail.startAnimation(shake);
            flag = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            etEmail.setError("Enter Valid Email Address.");
            etEmail.startAnimation(shake);
        }

        return flag;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
