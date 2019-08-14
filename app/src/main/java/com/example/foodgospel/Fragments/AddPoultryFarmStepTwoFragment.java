package com.example.foodgospel.Fragments;


import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodgospel.Adapters.LanguageArrayAdapter;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.Language;
import com.example.foodgospel.Models.State;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPoultryFarmStepTwoFragment extends Fragment {

    Spinner spQualification, spLanguage, spState, spDistrict, spCity;
    Button btnNext;
    SharedPreferences mPrefs;
    EditText etMarketEmail, etEmail;
    int SelectedLanguageId;
    String SelectedQualificationName, districtNameList, cityNameList;

    //For country,city,state arraylist
    ArrayList<String> arlStateName = new ArrayList<>();
    ArrayList<Integer> arlStateId = new ArrayList<>();
    ArrayList<Integer> arlDistrictId = new ArrayList<>();
    private ArrayList<String> arlDistrictList = new ArrayList<>();
    private ArrayList<String> arlCityList = new ArrayList<>();
    ArrayList<Integer> arlCityId = new ArrayList<>();
    int stateId, districtstateId, selectedStateId, selectedCityId, selectedDistrictId;

    SectorRepository objSectorRepository;

    private boolean isEditStepOne;
    UserFarmer objFarmer;


    public AddPoultryFarmStepTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_poultry_farm_step_two, container, false);

        spQualification = view.findViewById(R.id.spQualification);
        spLanguage = view.findViewById(R.id.spLanguage);
        spState = view.findViewById(R.id.spState);
        spDistrict = view.findViewById(R.id.spDistrict);
        spCity = view.findViewById(R.id.spCity);
        etMarketEmail = view.findViewById(R.id.etMarketEmail);
        etEmail = view.findViewById(R.id.etEmail);
        btnNext = view.findViewById(R.id.btnNext);


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 2");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final AddPoultryFarmAllData objAddPoultryFarmAllData = (AddPoultryFarmAllData) getArguments().getSerializable("PoultryStepOne");

        isEditStepOne = getArguments().getBoolean("isEdit", false);

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
        spQualification.setAdapter(adapter);

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

        final ArrayList<Language> arlLanguage = new ArrayList<>();
        arlLanguage.add(new Language(0, "Select Language"));
        arlLanguage.add(new Language(1, "Marathi"));
        arlLanguage.add(new Language(2, "Hindi"));
        arlLanguage.add(new Language(3, "English"));
        arlLanguage.add(new Language(4, "Tamil"));
        arlLanguage.add(new Language(5, "Bengali"));
        arlLanguage.add(new Language(6, "Telugu"));
        arlLanguage.add(new Language(7, "Punjabi"));

        spLanguage.setAdapter(new LanguageArrayAdapter(getActivity(), R.layout.item_spinner, arlLanguage));

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

        //for fetching allstate name from database

        objSectorRepository = new SectorRepository(getActivity());
        objSectorRepository.getAllState().observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(@Nullable List<State> states) {
                for (State objState : states) {

                /*    stateId = Integer.parseInt(String.valueOf(objState.getId()));
                    stateNameList = objState.getName();*/

                    arlStateName.add(objState.getName());
                    arlStateId.add(objState.getId());

                    ArrayAdapter<String> adpState = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlStateName);
                    adpState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spState.setAdapter(adpState);

                }
            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = arlStateId.get(position);
                selectedStateId = arlStateId.get(position);
                arlDistrictList.clear();
                arlCityList.clear();

                objSectorRepository.getAllDistrict(selectedId).observe(AddPoultryFarmStepTwoFragment.this, new Observer<List<District>>() {

                    @Override
                    public void onChanged(@Nullable List<District> districts) {
                        for (District objDistrict : districts) {
                            //set online data into model class
                            districtNameList = objDistrict.getName();
                            districtstateId = objDistrict.getId();
                            arlDistrictList.add(districtNameList);
                            arlDistrictId.add(districtstateId);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlDistrictList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spDistrict.setAdapter(adapter);

                        }
                    }
                });

                objSectorRepository.getAllCityData(selectedId).observe(AddPoultryFarmStepTwoFragment.this, new Observer<List<City>>() {

                    @Override
                    public void onChanged(@Nullable List<City> cities) {
                        for (City objCity : cities) {
                            //set online data into model class
                            cityNameList = objCity.getName();
                            arlCityList.add(cityNameList);
                            arlCityId.add(objCity.getId());

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlCityList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spCity.setAdapter(adapter);

                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrictId = arlDistrictId.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCityId = arlCityId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (isEditStepOne) {
            objFarmer = (UserFarmer) getArguments().getSerializable("PoultryEditStepOne");

            if (objFarmer.getLanguage() == null) {
                spLanguage.setSelection(0);
            } else {
                spLanguage.setAdapter(new LanguageArrayAdapter(getActivity(), R.layout.item_spinner, arlLanguage));
                spLanguage.setSelection(objFarmer.getLanguage().getId());
            }

            if (objFarmer.getQualification() == null) {
                spQualification.setSelection(0);
            } else {
                spQualification.setAdapter(adapter);
                int getPosition = adapter.getPosition(objFarmer.getQualification());
                spQualification.setSelection(getPosition);
            }


            if (objFarmer.getState() == null) {
                spState.setSelection(0);
            } else {
                spState.setSelection(objFarmer.getState().getId());
            }
            if (objFarmer.getDistrict() == null) {
                spDistrict.setSelection(0);
            } else {
                spDistrict.setSelection(objFarmer.getDistrict().getId());
            }
            if (objFarmer.getCity() == null) {
                spCity.setSelection(0);
            } else {
                spCity.setSelection(objFarmer.getCity().getId());
            }
            etEmail.setText(objFarmer.getEmail());
            etMarketEmail.setText(objFarmer.getMarketEmail());

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding step-2 data into main object
                objAddPoultryFarmAllData.setQualification(SelectedQualificationName);
                objAddPoultryFarmAllData.setLanguage(SelectedLanguageId);
                objAddPoultryFarmAllData.setState(selectedStateId);
                objAddPoultryFarmAllData.setDistrict(selectedDistrictId);
                objAddPoultryFarmAllData.setCity(selectedCityId);
                objAddPoultryFarmAllData.setEmail(etEmail.getText().toString());
                objAddPoultryFarmAllData.setMarket_email(etMarketEmail.getText().toString());

                //Fragment calling step - 3
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                final AddPoultryFarmStepThreeFragment objAddPoultryStepThreeFragment = new AddPoultryFarmStepThreeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PoultryStepTwo", objAddPoultryFarmAllData);
                bundle.putSerializable("PoultryEditStepTwo", objFarmer);
                bundle.putBoolean("isEditStepTwo", isEditStepOne);
                objAddPoultryStepThreeFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameLayout, objAddPoultryStepThreeFragment).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        return view;
    }

}
