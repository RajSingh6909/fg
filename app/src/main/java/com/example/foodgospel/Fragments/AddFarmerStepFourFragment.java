package com.example.foodgospel.Fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.foodgospel.Activity.NavHomeActivity;
import com.example.foodgospel.Activity.SectorActivity;
import com.example.foodgospel.Adapters.DeliveryArrayAdapter;
import com.example.foodgospel.Adapters.ProduceListArrayAdapter;
import com.example.foodgospel.Adapters.SectorArrayAdapter;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.Utility;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.DelieveryOptions;
import com.example.foodgospel.Models.PostResponse;
import com.example.foodgospel.Models.ProduceList;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.UserFarmer;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;

public class AddFarmerStepFourFragment extends Fragment {

    private RadioGroup rgMain;
    private RadioButton rbOwned, rbLeased;
    private EditText etLandHoldName, etDelieveryTime, etPartnerName, etMailingAddress;
    private Spinner spOptions, spSectorName, spDelievryoptions, spProduceList;
    private Toolbar toolbar;
    private SectorRepository objSectorRepository;
    private String LandHoldType = "", RbSelectedValue = "";
    private ArrayList<Sector> arlSectorIds = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlDelieveryOptions = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlSelectedDelieveryOptions = new ArrayList<>();
    private ArrayList<ProduceList> arlProduceList = new ArrayList<>();
    private RecyclerView rcvSelectedSectorList;
    private AddFarmerAllData objAllFarmerData;
    private Button btnSubmit;
    private LinearLayout llDelieveryLayout;
    private Spinner spDelieveryOptions, spHours;
    private PostResponse objPostResponse;
    String strDelieveryTime = "", strColdChainPatnerName = "", strSendOption = "", strHours = "", strProductUse = "", strDeliveryOption = "";

    private ArrayList<Sector> arlSelectedSector = new ArrayList<>();
    ArrayList<Sector> arlFetchUsersSelectedSector = new ArrayList<>();
    Boolean isEditStepFour;
    UserFarmer objFarmer;
    DeliveryArrayAdapter objDeliveryArrayAdapter;
    SectorArrayAdapter objSectorArrayAdapter;
    ViewDataListAdapter rcvAdapter;
    ProduceListArrayAdapter objProductListArrayAdapter;


    public AddFarmerStepFourFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_farmer_step_four, container, false);

        objAllFarmerData = (AddFarmerAllData) getArguments().getSerializable("FarmerStepThree");

        //For checking edit data flag from screen 2
        isEditStepFour = getArguments().getBoolean("isEditThree", false);

        etMailingAddress = view.findViewById(R.id.etMailingAddress);
        rgMain = view.findViewById(R.id.rgMain);
        rbOwned = view.findViewById(R.id.rbOwned);
        rbLeased = view.findViewById(R.id.rbLeased);
        toolbar = view.findViewById(R.id.toolbar);
        etLandHoldName = view.findViewById(R.id.etLandHoldName);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        rcvSelectedSectorList = view.findViewById(R.id.rcvSelectedSectorList);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        //spinner
        spOptions = view.findViewById(R.id.spOptions);

        spSectorName = view.findViewById(R.id.spSector);

        spDelievryoptions = view.findViewById(R.id.spDeleiveryOptions);

        spProduceList = view.findViewById(R.id.spProduceList);

        llDelieveryLayout = view.findViewById(R.id.llDelieveryLayout);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        rcvSelectedSectorList.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvAdapter = new ViewDataListAdapter(arlSelectedSector);
        rcvSelectedSectorList.setAdapter(rcvAdapter);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rcvSelectedSectorList.setAdapter(rcvAdapter);
                return false;
            }
        });


        toolbar.setTitle("Step 4");

        //Land Hold()
        ArrayList<String> arlLandOption = new ArrayList<>();
        arlLandOption.add("Select");
        arlLandOption.add("Cents");
        arlLandOption.add("Acres");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlLandOption);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOptions.setAdapter(adapter);


        arlDelieveryOptions.clear();

        arlDelieveryOptions.add(new DelieveryOptions(0, "Select Delievery Options", ""));
        arlDelieveryOptions.add(new DelieveryOptions(1, "Sends Delivery", "sends_delivery"));
        arlDelieveryOptions.add(new DelieveryOptions(2, "Food Gospel Pickup", "food_gospel_pickup"));
        arlDelieveryOptions.add(new DelieveryOptions(3, "Cold Chain Pickup", "cold_chain_pickup"));

        objDeliveryArrayAdapter = new DeliveryArrayAdapter(getActivity(), R.layout.item_spinner, arlDelieveryOptions);
        spDelievryoptions.setAdapter(objDeliveryArrayAdapter);

        spDelievryoptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {

                    llDelieveryLayout.removeAllViews();
                    inflateDelieveryOption(arlDelieveryOptions.get(position));

                   /* if (!arlSelectedDelieveryOptions.contains(arlDelieveryOptions.get(position))) {
                        arlSelectedDelieveryOptions.add(arlDelieveryOptions.get(position));
                        inflateDelieveryOption(arlDelieveryOptions.get(position));
                    }*/

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        arlProduceList.clear();

        arlProduceList.add(new ProduceList(0, "Select"));
        arlProduceList.add(new ProduceList(1, "PRIVATE USE"));
        arlProduceList.add(new ProduceList(2, "SELLS LOCALLY"));
        arlProduceList.add(new ProduceList(3, "SELLS TO PRIVATE LABEL"));
        arlProduceList.add(new ProduceList(4, "DAIRY CO-OP"));
        arlProduceList.add(new ProduceList(5, "COLD-CHAIN PARTNER"));

        objProductListArrayAdapter = new ProduceListArrayAdapter(getActivity(), R.layout.item_spinner, arlProduceList);

        spProduceList.setAdapter(objProductListArrayAdapter);

        spOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LandHoldType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        arlSectorIds.clear();

        arlSectorIds.add(new Sector(0, "0", "Select Sector"));

        //Sector name from room database
        objSectorRepository = new SectorRepository(getActivity());
        objSectorRepository.getAllSector().observe(this, new Observer<List<Sector>>() {
            @Override
            public void onChanged(@Nullable List<Sector> sectors) {
                arlSectorIds.addAll(sectors);
            }
        });

        spSectorName.setAdapter(new SectorArrayAdapter(getActivity(), R.layout.item_spinner, arlSectorIds));

        //Sector main click is managing all dropdown item's click

        spSectorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                } else {
                    if (!arlSelectedSector.contains(arlSectorIds.get(position))) {
                        // arlSelectedSector.add(arlSectorIds.get(position));
                        Intent intent = new Intent(getActivity(), SectorActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SECTOR", arlSectorIds.get(position));
                        bundle.putSerializable("SELECTEDSECTORS", arlSelectedSector);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 123);
                    }

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rcvSelectedSectorList.setAdapter(new ViewDataListAdapter(arlSelectedSector));

            }


        });


        spProduceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strProductUse = arlProduceList.get(position).getProduceName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (isEditStepFour) {
            objFarmer = (UserFarmer) getArguments().getSerializable("FarmerEditStepThree");
            etMailingAddress.setText(objFarmer.getMailingAddress());
            etLandHoldName.setText(objFarmer.getLandHold());
            if (objFarmer.getLandOwnership().equalsIgnoreCase("Owned")) {
                rbOwned.setChecked(true);
            } else if (objFarmer.getLandOwnership().equalsIgnoreCase("Leased")) {
                rbLeased.setChecked(true);
            }
            spOptions.setAdapter(adapter);
            int getPosition = adapter.getPosition(objFarmer.getLandHoldType());
            spOptions.setSelection(getPosition);


//            for (DelieveryOptions objDelieveryoptions : arlDelieveryOptions) {
//                if (objDelieveryoptions.getDelieveryNameCode().equalsIgnoreCase(objFarmer.getDelivery())) {
//                    int pos = objDeliveryArrayAdapter.getPosition(objDelieveryoptions);
//                    spDelieveryOptions.setSelection(pos);
//                }
//            }

            for (ProduceList objProduceList : arlProduceList) {
                int position = objProductListArrayAdapter.getPosition(objProduceList);
                spProduceList.setSelection(position);
            }


            objAllFarmerData.setFarmer_id(Integer.parseInt(objFarmer.getFarmerId()));

//            if (objFarmer.getFarm() == null) {
//                if (objFarmer.getDairy() != null) {
//                    arlSelectedSector.addAll(objFarmer.getDairy().getSector());
//                } if (objFarmer.getPoultry() != null) {
//                    arlSelectedSector.addAll(objFarmer.getPoultry().getSector());
//                }
//            } else {
//                arlSelectedSector.addAll(objFarmer.getFarm().getSector());
//
//            }

            arlSelectedSector.addAll(objFarmer.getFarm().getSector());

            rcvSelectedSectorList.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvAdapter = new ViewDataListAdapter(arlSelectedSector);
            rcvSelectedSectorList.setAdapter(rcvAdapter);
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbLeased.isChecked()) {
                    RbSelectedValue = rbLeased.getText().toString();
                } else if (rbOwned.isChecked()) {
                    RbSelectedValue = rbOwned.getText().toString();
                }

                if (etDelieveryTime.getVisibility() == View.VISIBLE) {
                    strDelieveryTime = etDelieveryTime.getText().toString();
                } else {
                    strDelieveryTime = "";
                }

                if (etPartnerName.getVisibility() == View.VISIBLE) {
                    strColdChainPatnerName = etPartnerName.getText().toString();
                } else {
                    strColdChainPatnerName = "";
                }

                objAllFarmerData.setMailing_address(etMailingAddress.getText().toString());
                objAllFarmerData.setLand_hold(etLandHoldName.getText().toString());
                objAllFarmerData.setLand_ownership(RbSelectedValue);
                objAllFarmerData.setLand_hold_type(LandHoldType);
                objAllFarmerData.setDelivery_time(strDelieveryTime);
                objAllFarmerData.setCold_chain_store(strColdChainPatnerName);
                objAllFarmerData.setSend_option(strSendOption);
                objAllFarmerData.setDelivery(strDeliveryOption);
                objAllFarmerData.setTime_type(strHours);
                objAllFarmerData.setProduct_use(strProductUse);
                objAllFarmerData.setUser_id(Integer.parseInt(FoodGospelSharedPrefernce.getSharePrefernceData(getActivity(), "USERID")));

                Gson gson = new Gson();
                objAllFarmerData.setArlSectors(arlSelectedSector);
                String json = gson.toJson(objAllFarmerData);
                Log.d("AllData : ", json);
                JsonObject object = new JsonObject();
                object.addProperty("AddFarmer", json);

                if (Utility.checkconnection(getActivity())) {
                    if (isEditStepFour) {
                        apiEditFarmer(object);
                    } else {
                        apiAddFarmer(object);
                        objSectorRepository.insertFarmer(objAllFarmerData);
                    }
                } else {
                    objSectorRepository.insertFarmer(objAllFarmerData);
                }
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
        Log.d("OnDetech", "on Deteched called");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            Sector objSector = (Sector) bundle.getSerializable("ADDEDSECTOR");
            for (Sector sector2 : arlSectorIds) {
                if (sector2.getSectorId().equals(objSector.getSectorId())) {
                    arlSelectedSector.add(sector2);
                }
            }
            //arlSelectedSector.get(arlSelectedSector.indexOf(objSector)).setArlCategoryList(objSector.getArlCategoryList());
            for (Sector sector : arlSelectedSector) {
                if (sector.getSectorId().equals(objSector.getSectorId())) {
                    arlSelectedSector.get(arlSelectedSector.indexOf(sector)).setArlCategoryList(objSector.getArlCategoryList());
                }
            }
            rcvAdapter = new ViewDataListAdapter(arlSelectedSector);
            rcvSelectedSectorList.setAdapter(rcvAdapter);

        } else if (requestCode == 124 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Sector objSector = (Sector) bundle.getSerializable("ADDEDSECTOR");

            for (Sector sector : arlSelectedSector) {
                if (sector.getSectorId().equals(objSector.getSectorId())) {
                    arlSelectedSector.get(arlSelectedSector.indexOf(sector)).setArlCategoryList(objSector.getArlCategoryList());
                }
            }
            rcvAdapter = new ViewDataListAdapter(arlSelectedSector);
            rcvSelectedSectorList.setAdapter(rcvAdapter);
        }


    }

    public void inflateDelieveryOption(DelieveryOptions objDeliveryoption) {

        LayoutInflater objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View objView = objLayoutInflater.inflate(R.layout.raw_delievery_option, null);
        TextView linChild = objView.findViewById(R.id.linedelievryTime); // label for delievery time

        etDelieveryTime = objView.findViewById(R.id.etDelieveryTime);//edittext

        etPartnerName = objView.findViewById(R.id.etPartnerName);//edittext
        spDelieveryOptions = objView.findViewById(R.id.spOptions);//spinner
        spHours = objView.findViewById(R.id.spHours);//spinner


        if (objDeliveryoption.getDelieveryName().equalsIgnoreCase("Food Gospel Pickup")) {
            spHours.setVisibility(View.GONE);
            etPartnerName.setVisibility(View.GONE);
            etDelieveryTime.setVisibility(View.GONE);
            linChild.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName().equalsIgnoreCase("Cold Chain Pickup")) {
            spHours.setVisibility(View.GONE);
            etPartnerName.setVisibility(View.VISIBLE);
            linChild.setVisibility(View.GONE);
            etDelieveryTime.setVisibility(View.GONE);
            spDelieveryOptions.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName().equalsIgnoreCase("Sends Delivery")) {
            etPartnerName.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        }


        ArrayList<String> arlHours = new ArrayList<>();
        arlHours.add("Hours");

        ArrayAdapter<String> adpState = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arlHours);
        adpState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHours.setAdapter(adpState);

        //for delievery options
        ArrayList<String> arlDelieveryoptions = new ArrayList<>();
        arlDelieveryoptions.add("NEED 4-6 HOUR PRIOR NOTICE");
        arlDelieveryoptions.add("NEED 12 HOUR PRIOR NOTICE");
        arlDelieveryoptions.add("NEED 24 HOUR PRIOR NOTICE");

        ArrayAdapter<String> adpOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arlDelieveryoptions);
        adpOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDelieveryOptions.setAdapter(adpOptions);

        llDelieveryLayout.addView(objView);

        spDelieveryOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSendOption = parent.getSelectedItem().toString();
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strHours = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private boolean checkAllFields() {
        boolean flag = true;
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        if (spDelieveryOptions.getSelectedItem().toString().trim().equals("Pick one")) {
            flag = false;
        }

        return flag;
    }

    private void apiAddFarmer(JsonObject send) {

        Call<PostResponse> call = ApiClient.getPostService().addFarmer(send);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                objPostResponse = response.body();
                Toast.makeText(getContext(), objPostResponse.getMessage(), Toast.LENGTH_LONG).show();
                Intent intentSplash = new Intent(getActivity(), NavHomeActivity.class);
                startActivity(intentSplash);

            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Data Insertion failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    class ViewDataListAdapter extends RecyclerView.Adapter<ViewDataListAdapter.ViewHolder> {

        Context context;
        ArrayList<Sector> arlAllSector = new ArrayList<>();

        public ViewDataListAdapter(ArrayList<Sector> arlAllSector) {
            this.context = getActivity();
            this.arlAllSector = arlAllSector;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_all_raw_data, viewGroup, false);
            return new ViewDataListAdapter.ViewHolder(v);
        }

        @Override

        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            String strCategoryName = " ";
            final ArrayList<Category> arlCategory = new ArrayList<>();

            Sector objSector = arlAllSector.get(i);
            viewHolder.txtSectorName.setText(objSector.getName());

            viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    arlAllSector.get(viewHolder.getAdapterPosition());
                    Sector objSector = arlAllSector.get(viewHolder.getAdapterPosition());
                    Intent i = new Intent(context, SectorActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("SECTOREDIT", objSector);
                    b.putBoolean("ISEDIT", true);
                    //  b.putSerializable("USERSELECTION",arlCategory);
                    i.putExtras(b);
                    startActivityForResult(i, 124);

                }
            });
        }


        @Override
        public int getItemCount() {
            return arlAllSector.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtSectorName, txtCategoryName, txtCropName, txtHarvestMonth;
            ImageView imgEdit, imgDelete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                txtSectorName = itemView.findViewById(R.id.txtSelectedSectorName);
                txtCategoryName = itemView.findViewById(R.id.txtSelectedCategoryName);
                txtCropName = itemView.findViewById(R.id.txtCropName);
                txtHarvestMonth = itemView.findViewById(R.id.txtHarvestMonth);
                imgEdit = itemView.findViewById(R.id.imgEdit);
                imgDelete = itemView.findViewById(R.id.imgDelete);


            }
        }

    }

    private void apiEditFarmer(JsonObject send) {
        Call<PostResponse> call = ApiClient.getPostService().editFarmer(send);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                objPostResponse = response.body();
                Toast.makeText(getContext(), objPostResponse.getMessage(), Toast.LENGTH_LONG).show();
                Intent intentSplash = new Intent(getActivity(), NavHomeActivity.class);
                startActivity(intentSplash);

            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Data Insertion failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

}


