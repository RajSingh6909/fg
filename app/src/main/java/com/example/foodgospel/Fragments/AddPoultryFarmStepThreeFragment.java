package com.example.foodgospel.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.foodgospel.Activity.DairyActivity;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.Utility;
import com.example.foodgospel.Models.Constants;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPoultryFarmStepThreeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private EditText etAddress, etPostalCode, etMailingAddress, etLandHoldName, etDelieveryTime, etPartnerName;
    private Spinner spOptions, spDelievryoptions, spProduceList, spDelieveryOptions, spHours;
    private Button btnNext;
    private RadioGroup rgMain;
    private RadioButton rbOwned, rbLeased;
    ;
    private LinearLayout llDelieveryLayout;
    DairyActivity activity;
    GoogleMap map;
    com.google.android.gms.maps.MapView mapView;
    LatLng src;
    private FusedLocationProviderClient mFusedLocationClient;
    private String LandHoldType, RbSelectedValue;
    private boolean isEditStepTwo;
    private UserFarmer objFarmer;

    public AddPoultryFarmStepThreeFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_add_poultry_step_three, container, false);

        //get step-2 data from main object

        final AddPoultryFarmAllData objAddPoultryFarmAllData = (AddPoultryFarmAllData) getArguments().getSerializable("PoultryStepTwo");

        isEditStepTwo = getArguments().getBoolean("isEditStepTwo", false);

        etLandHoldName = view.findViewById(R.id.etLandHoldName);

        etAddress = view.findViewById(R.id.etAddress);

        etPostalCode = view.findViewById(R.id.etPostalCode);

        etMailingAddress = view.findViewById(R.id.etMailingAddress);

        spOptions = view.findViewById(R.id.spOptions);

        spDelievryoptions = view.findViewById(R.id.spDeleiveryOptions);

        spProduceList = view.findViewById(R.id.spProduceList);

        rgMain = view.findViewById(R.id.rgMain);

        rbLeased = view.findViewById(R.id.rbLeased);

        rbOwned = view.findViewById(R.id.rbOwned);


        llDelieveryLayout = view.findViewById(R.id.llDelieveryLayout);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 3");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Land Hold()
        ArrayList<String> arlLandOption = new ArrayList<>();
        arlLandOption.add("Select");
        arlLandOption.add("Cents");
        arlLandOption.add("Acres");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlLandOption);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOptions.setAdapter(adapter);

        spOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LandHoldType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnNext = view.findViewById(R.id.btnNext);

        if (isEditStepTwo) {
            objFarmer = (UserFarmer) getArguments().getSerializable("PoultryEditStepTwo");
            if (objFarmer.getLandOwnership().equalsIgnoreCase("Owned")) {
                rbOwned.setChecked(true);
            } else if (objFarmer.getLandOwnership().equalsIgnoreCase("Leased")) {
                rbLeased.setChecked(true);
            }
            etLandHoldName.setText(objFarmer.getLandHold());
            etPostalCode.setText(objFarmer.getPostalCode());
            etAddress.setText(objFarmer.getAddress());
            etMailingAddress.setText(objFarmer.getMailingAddress());
            spOptions.setAdapter(adapter);
            int getPosition = adapter.getPosition(objFarmer.getLandHoldType());
            spOptions.setSelection(getPosition);

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bind data into main object

                if (checkAllFields()) {
                    if (rbLeased.isChecked()) {
                        RbSelectedValue = rbLeased.getText().toString();
                    } else if (rbOwned.isChecked()) {
                        RbSelectedValue = rbOwned.getText().toString();
                    }
                    objAddPoultryFarmAllData.setLand_ownership(RbSelectedValue);
                    objAddPoultryFarmAllData.setLand_hold_type(LandHoldType);
                    objAddPoultryFarmAllData.setAddress(etAddress.getText().toString());
                    objAddPoultryFarmAllData.setPostal_code(etPostalCode.getText().toString());
                    objAddPoultryFarmAllData.setMailing_address(etMailingAddress.getText().toString());
                    objAddPoultryFarmAllData.setLand_hold(etLandHoldName.getText().toString());
                    objAddPoultryFarmAllData.setLatitude(String.valueOf(src.latitude));
                    objAddPoultryFarmAllData.setLongitude(String.valueOf(src.longitude));

                    //Fragment calling step - 4
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final AddPoultryFarmStepFourFragment objAddPoultryStepFourFragment = new AddPoultryFarmStepFourFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PoultryStepThree", objAddPoultryFarmAllData);
                    bundle.putSerializable("PoultryEditStepThree", objFarmer);
                    bundle.putSerializable("isEditThree", isEditStepTwo);
                    objAddPoultryStepFourFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.ThirdLayoutMain, objAddPoultryStepFourFragment).commit();
                    fragmentTransaction.addToBackStack(null);
                }

            }
        });

        return view;
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
    public void onLocationChanged(Location location) {

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
}
