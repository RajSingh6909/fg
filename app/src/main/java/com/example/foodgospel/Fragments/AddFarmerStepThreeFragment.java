package com.example.foodgospel.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.Toast;


import com.example.foodgospel.Activity.FarmerActivity;
import com.example.foodgospel.BuildConfig;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.Config;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.Utility;
import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.Constants;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.SectorDetailsModel;
import com.example.foodgospel.Models.State;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;
import static org.osmdroid.tileprovider.util.StorageUtils.getStorage;

public class AddFarmerStepThreeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    SectorDetailsModel objSectorDetailsModel;
    SharedPreferences mPrefs;
    Spinner spSector, spCity, spLandHold, spState, spDistrict;
    ArrayList<String> arlSectorName = new ArrayList<>();
    ArrayList<String> arlCityName = new ArrayList<>();
    ArrayList<String> arlStateName = new ArrayList<>();
    ArrayList<Integer> arlStateId = new ArrayList<>();
    ArrayList<String> arlDistrictName = new ArrayList<>();
    ArrayList<Integer> arlDistrictId = new ArrayList<>();
    private ArrayList<District> arlDistrict = new ArrayList<>();
    private ArrayList<String> districtList = new ArrayList<>();
    private ArrayList<String> arlDistrictList = new ArrayList<>();
    private ArrayList<String> arlCityList = new ArrayList<>();
    ArrayList<Integer> arlCityId = new ArrayList<>();
    Toolbar toolbar;
    EditText etAddress, etPostalCode;
    District objDistrict;
    Integer StateId;
    int stateId, districtstateId, selectedStateId, selectedCityId, selectedDistrictId;
    Button btnNext;
    private SectorRepository objSectorRepository;
    private String stateNameList, districtId, districtNameList, cityNameList;

    FarmerActivity activity;
    GoogleMap map;
    com.google.android.gms.maps.MapView mapView;
    LatLng src;
    private FusedLocationProviderClient mFusedLocationClient;
    private UserFarmer objFarmer = null;
    Boolean isEditStepTwo;
    ArrayAdapter<String> adpState;

    public AddFarmerStepThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map_view);
        src = new LatLng(19.1135, 72.8422);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_farmer_step_three, container, false);

        activity = (FarmerActivity) getActivity();

        //Bind all elements of this screens into main object
        final AddFarmerAllData objAllFarmerData = (AddFarmerAllData) getArguments().getSerializable("FarmerStepTwo");

        //For checking edit data flag from screen 2
        isEditStepTwo = getArguments().getBoolean("isEditStepTwo", false);

        spCity = view.findViewById(R.id.spCity);
        spState = view.findViewById(R.id.spState);
        spDistrict = view.findViewById(R.id.spDistrict);
        etAddress = view.findViewById(R.id.etAddress);
        etPostalCode = view.findViewById(R.id.etPostalCode);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 3");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

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

                    adpState = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlStateName);
                    adpState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spState.setAdapter(adpState);

                }
            }
        });


        if (isEditStepTwo) {
            objFarmer = (UserFarmer) getArguments().getSerializable("FarmerEditStepTwo");

            if (objFarmer.getState() == null) {
                spState.setSelection(0);
            } else {
                spState.setAdapter(adpState);
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

            etAddress.setText(objFarmer.getAddress());
            etPostalCode.setText(objFarmer.getPostalCode());
        }

        btnNext = view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllFields()) {

                    objAllFarmerData.setState(selectedStateId);
                    objAllFarmerData.setDistrict(selectedDistrictId);
                    objAllFarmerData.setCity(selectedCityId);
                    objAllFarmerData.setAddress(etAddress.getText().toString());
                    objAllFarmerData.setPostal_code(etPostalCode.getText().toString());
                    objAllFarmerData.setLatitude(String.valueOf(src.latitude));
                    objAllFarmerData.setLongitude(String.valueOf(src.longitude));

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddFarmerStepFourFragment objAddFarmerStepFourFragment = new AddFarmerStepFourFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FarmerStepThree", objAllFarmerData);
                    bundle.putSerializable("FarmerEditStepThree", objFarmer);
                    bundle.putSerializable("isEditThree", isEditStepTwo);
                    objAddFarmerStepFourFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fourLayout, objAddFarmerStepFourFragment).commit();
                    fragmentTransaction.addToBackStack(null);
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

                objSectorRepository.getAllDistrict(selectedId).observe(AddFarmerStepThreeFragment.this, new Observer<List<District>>() {

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

                objSectorRepository.getAllCityData(selectedId).observe(AddFarmerStepThreeFragment.this, new Observer<List<City>>() {

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


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity().getApplicationContext());
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
            } else if (FoodGospelSharedPrefernce.getSharePrefernceBoolean(getActivity(), "location"))
                alertlocation();
            else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
            }
            FoodGospelSharedPrefernce.setSharePrefernceBoolean(getActivity(), "location", true);
        } else {
            if (Utility.checkGPSStatus(getActivity())) {
                map.setMyLocationEnabled(true);
                getCurrentlocation();
            } else {
                buildAlertMessageNoGps();
            }
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions().position(src).title("Toogle Bytes").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        CameraPosition cameraPosition = CameraPosition.builder().target(src).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.getUiSettings().setMapToolbarEnabled(true);
      /*  map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });*/

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                //   Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                src = new LatLng(arg0.getPosition().latitude, arg0.getPosition().longitude);

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_LOCATION_PERMS:

                if (Utility.validateGrantedPermissions(grantResults)) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (Utility.checkGPSStatus(getActivity())) {
                        map.setMyLocationEnabled(true);
                        getCurrentlocation();
                    } else {
                        buildAlertMessageNoGps();
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);

                    } else if (FoodGospelSharedPrefernce.getSharePrefernceBoolean(getActivity(), "location")) {
                        alertlocation();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
                    }
                }
                break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public void alertlocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Location Permission");
        builder.setMessage("This app needs Location permission");
        builder.setPositiveButton("Go to Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 100);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (reqCode == 100) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (Utility.checkGPSStatus(getActivity())) {
                    map.setMyLocationEnabled(true);
                    getCurrentlocation();
                } else {
                    buildAlertMessageNoGps();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
            }
        } else if (reqCode == 1010) {
            //    activity.getSupportFragmentManager().popBackStackImmediate();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (Utility.checkGPSStatus(getActivity())) {
                    map.setMyLocationEnabled(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getCurrentlocation();
                        }
                    }, 10000);
                } else {
                    buildAlertMessageNoGps();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1010);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if (Utility.checkGPSStatus(getActivity())) {
                                map.setMyLocationEnabled(true);
                                getCurrentlocation();
                            } else {
                                buildAlertMessageNoGps();
                            }
                        } else {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_LOCATION_PERMS);
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void getCurrentlocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(5).build();
                            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            // lat = location.getLatitude() + "";
                            // lng = location.getLongitude() + "";

                        }
                    }
                });
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean checkAllFields() {
        boolean flag = true;
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("Address cannot be empty.");
            etAddress.startAnimation(shake);
            flag = false;
        }

        if (etPostalCode.getText().toString().isEmpty()) {
            etPostalCode.setError("Postal Code cannot be empty.");
            etPostalCode.startAnimation(shake);
            flag = false;
        } else if (etPostalCode.length() != 6) {
            etPostalCode.setError("Invalid Postal Code. It must be 6 digit");
            etPostalCode.startAnimation(shake);
            flag = false;
        }

        return flag;
    }

}
