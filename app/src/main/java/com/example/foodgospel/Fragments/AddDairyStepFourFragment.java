package com.example.foodgospel.Fragments;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Activity.LiveStockGrownActivity;
import com.example.foodgospel.Activity.NavHomeActivity;
import com.example.foodgospel.Activity.SectorActivity;
import com.example.foodgospel.Adapters.DeliveryArrayAdapter;
import com.example.foodgospel.Adapters.LiveStockGrownArrayAdapter;
import com.example.foodgospel.Adapters.ProduceListArrayAdapter;
import com.example.foodgospel.Global.AddDairyAllData;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.Utility;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.DelieveryOptions;
import com.example.foodgospel.Models.LiveStockGrown;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDairyStepFourFragment extends Fragment {

    private Spinner spLiveStockGrown, spDelieveryOption, spProduceList, spHours, spOptions;
    private RecyclerView rvSelectedLiveStockGrown;
    private String strDelieveryTime = "", strColdChainPatnerName = "", strSendOption = "", strHours = "", strProductUse = "", strDeliveryOption = "";
    private ArrayList<LiveStockGrown> arlLiveStockGrown = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlDelieveryOptions = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlSelectedDelieveryOptions = new ArrayList<>();
    private ArrayList<ProduceList> arlProduceList = new ArrayList<>();
    private ArrayList<LiveStockGrown> arlLiveStockId = new ArrayList<>();
    private ArrayList<LiveStockGrown> arlSelectedLiveStockGrown = new ArrayList<>();
    private SectorRepository objSectorRepository;
    private LinearLayout llDelieveryLayout;
    private EditText etDelieveryTime, etPartnerName;
    private Button btnSubmit;
    private PostResponse objPostResponse;
    private Boolean isEditStepFour;
    private UserFarmer objFarmer;
    ViewDairyListAdapter rcvAdapter;
    ProduceListArrayAdapter objProductListArrayAdapter;
    DeliveryArrayAdapter objDeliveryArrayAdapter;

    public AddDairyStepFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_dairy_step_four, container, false);

        //get step-3 data from main object

        final AddDairyAllData objAddDairyAllData = (AddDairyAllData) getArguments().getSerializable("DairyStepThree");

        isEditStepFour = getArguments().getBoolean("isEditThree", false);

        spLiveStockGrown = view.findViewById(R.id.spLiveStockGrown);
        spDelieveryOption = view.findViewById(R.id.spDelieveryOption);
        spProduceList = view.findViewById(R.id.spProduceList);

        rvSelectedLiveStockGrown = view.findViewById(R.id.rvSelectedLiveStock);

        rvSelectedLiveStockGrown.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvAdapter = new ViewDairyListAdapter(arlSelectedLiveStockGrown);
        rvSelectedLiveStockGrown.setAdapter(rcvAdapter);

        llDelieveryLayout = view.findViewById(R.id.llDelieveryLayout);

        spProduceList = view.findViewById(R.id.spProduceList);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 4");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //DelieveryOptions
        arlDelieveryOptions.clear();

        arlDelieveryOptions.add(new DelieveryOptions(0, "Select Delivery Options", ""));
        arlDelieveryOptions.add(new DelieveryOptions(1, "Sends Delivery", "sends_delivery"));
        arlDelieveryOptions.add(new DelieveryOptions(2, "Food Gospel Pickup", "food_gospel_pickup"));
        arlDelieveryOptions.add(new DelieveryOptions(3, "Cold Chain Pickup", "cold_chain_pickup"));

        objDeliveryArrayAdapter = new DeliveryArrayAdapter(getActivity(), R.layout.item_spinner, arlDelieveryOptions);
        spDelieveryOption.setAdapter(objDeliveryArrayAdapter);

//        spDelieveryOption.setAdapter(new DeliveryArrayAdapter(getActivity(), R.layout.item_spinner, arlDelieveryOptions));


        //ProductList
        arlProduceList.clear();

        arlProduceList.add(new ProduceList(0, "Select"));
        arlProduceList.add(new ProduceList(1, "PRIVATE USE"));
        arlProduceList.add(new ProduceList(2, "SELLS LOCALLY"));
        arlProduceList.add(new ProduceList(3, "SELLS TO PRIVATE LABEL"));
        arlProduceList.add(new ProduceList(4, "DAIRY CO-OP"));
        arlProduceList.add(new ProduceList(5, "COLD-CHAIN PARTNER"));


        objProductListArrayAdapter = new ProduceListArrayAdapter(getActivity(),R.layout.item_spinner,arlProduceList);
        spProduceList.setAdapter(objProductListArrayAdapter);

       // spProduceList.setAdapter(new ProduceListArrayAdapter(getContext(), R.layout.item_spinner, arlProduceList));


        //LiveStockGrown dataBinding
        arlLiveStockGrown.clear();

        arlLiveStockGrown.add(new LiveStockGrown(0, "Select Product"));

        objSectorRepository = new SectorRepository(getActivity());

        objSectorRepository.getAllLiveStockGrownData().observe(this, new Observer<List<LiveStockGrown>>() {
            @Override
            public void onChanged(@Nullable List<LiveStockGrown> liveStockGrowns) {
                arlLiveStockGrown.addAll(liveStockGrowns);
            }
        });

        spLiveStockGrown.setAdapter(new LiveStockGrownArrayAdapter(getActivity(), R.layout.item_spinner, arlLiveStockGrown));

        spLiveStockGrown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Selected Product Name" + parent.getSelectedItem()
                        , Toast.LENGTH_LONG).show();

                if (position == 0) {

                } else {
                    if (!arlSelectedLiveStockGrown.contains(arlLiveStockGrown.get(position))) {
                        Intent intent = new Intent(getActivity(), LiveStockGrownActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("LIVESTOCKGROWN", arlLiveStockGrown.get(position));
                        // bundle.putSerializable("SELECTEDLIVESTOCKGROWN", arlSelectedLiveStockGrown);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 123);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDelieveryOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
//                    if (!arlSelectedDelieveryOptions.contains(arlDelieveryOptions.get(position))) {
//                        arlSelectedDelieveryOptions.add(arlDelieveryOptions.get(position));
//                        inflateDelieveryOption(arlDelieveryOptions.get(position));
//                    }

                    llDelieveryLayout.removeAllViews();
                    inflateDelieveryOption(arlDelieveryOptions.get(position));


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            objFarmer = (UserFarmer) getArguments().getSerializable("DairyEditStepThree");
//            if (objFarmer.getDeliveryTime().equals("0")) {
//                etDelieveryTime.setText("0");
//            } else {
//                etDelieveryTime.setText(objFarmer.getDeliveryTime());
//            }
            objAddDairyAllData.setFarmer_id(Integer.parseInt(objFarmer.getFarmerId()));

            for (DelieveryOptions objDelieveryoptions : arlDelieveryOptions) {
                if (objDelieveryoptions.getDelieveryNameCode().equalsIgnoreCase(objFarmer.getDelivery())) {
                    int pos = objDeliveryArrayAdapter.getPosition(objDelieveryoptions);
                    spDelieveryOption.setSelection(pos);
                }
            }

            for (ProduceList objProduceList : arlProduceList) {
                int position = objProductListArrayAdapter.getPosition(objProduceList);
                spProduceList.setSelection(position);
            }

            arlSelectedLiveStockGrown.addAll(objFarmer.getDairy().getArlLiveStockGrown());
            rvSelectedLiveStockGrown.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvAdapter = new ViewDairyListAdapter(arlSelectedLiveStockGrown);
            rvSelectedLiveStockGrown.setAdapter(rcvAdapter);


        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                objAddDairyAllData.setDelivery(strDeliveryOption);
                objAddDairyAllData.setDelivery_time(strDelieveryTime);
                objAddDairyAllData.setProduct_use(strProductUse);
                objAddDairyAllData.setSend_option(strSendOption);
                objAddDairyAllData.setTime_type(strHours);
                objAddDairyAllData.setCold_chain_store(strColdChainPatnerName);
                objAddDairyAllData.setUser_id(Integer.parseInt(FoodGospelSharedPrefernce.getSharePrefernceData(getActivity(), "USERID")));
                Gson gson = new Gson();
                objAddDairyAllData.setArlLiveStockGrown(arlSelectedLiveStockGrown);
                String json = gson.toJson(objAddDairyAllData);
                Log.d("AllDairyData : ", json);
                JsonObject object = new JsonObject();
                object.addProperty("AddDairy", json);

                if (Utility.checkconnection(getActivity())) {
                    if (isEditStepFour) {
                        apiEditDairy(object);
                    } else {
                        apiAddDairy(object);
                        objSectorRepository.insertDairyData(objAddDairyAllData);
                    }
                } else {
                    objSectorRepository.insertDairyData(objAddDairyAllData);
                }


            }
        });

        return view;
    }

    public void inflateDelieveryOption(DelieveryOptions objDeliveryoption) {

        LayoutInflater objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View objView = objLayoutInflater.inflate(R.layout.raw_delievery_option, null);
        TextView linChild = objView.findViewById(R.id.linedelievryTime); // label for delievery time
        etDelieveryTime = objView.findViewById(R.id.etDelieveryTime);
        etPartnerName = objView.findViewById(R.id.etPartnerName);
        spOptions = objView.findViewById(R.id.spOptions);
        spHours = objView.findViewById(R.id.spHours);


        if (objDeliveryoption.getDelieveryName() == "Food Gospel Pickup") {
            spHours.setVisibility(View.GONE);
            etPartnerName.setVisibility(View.GONE);
            etDelieveryTime.setVisibility(View.GONE);
            linChild.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName() == "Cold Chain Pickup") {
            spHours.setVisibility(View.GONE);
            etPartnerName.setVisibility(View.VISIBLE);
            etDelieveryTime.setVisibility(View.GONE);
            spOptions.setVisibility(View.GONE);
            linChild.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName() == "Sends Delivery") {
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
        spOptions.setAdapter(adpOptions);

        llDelieveryLayout.addView(objView);

        spOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            LiveStockGrown objLiveStockGrown = (LiveStockGrown) bundle.getSerializable("ADDEDMILKPRODUCT");
            arlSelectedLiveStockGrown.remove(objLiveStockGrown);
            arlSelectedLiveStockGrown.add(objLiveStockGrown);


            rcvAdapter = new ViewDairyListAdapter(arlSelectedLiveStockGrown);
            rvSelectedLiveStockGrown.setAdapter(rcvAdapter);

        } else if (requestCode == 124 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            LiveStockGrown objLiveStockGrown = (LiveStockGrown) bundle.getSerializable("ADDEDMILKPRODUCT");

            arlSelectedLiveStockGrown.remove(objLiveStockGrown);
            arlSelectedLiveStockGrown.add(objLiveStockGrown);

            rcvAdapter = new ViewDairyListAdapter(arlSelectedLiveStockGrown);
            rvSelectedLiveStockGrown.setAdapter(rcvAdapter);

        }
    }

    class ViewDairyListAdapter extends RecyclerView.Adapter<ViewDairyListAdapter.ViewHolder> {

        Context context;
        ArrayList<LiveStockGrown> arlAllLiveStock = new ArrayList<>();

        public ViewDairyListAdapter(ArrayList<LiveStockGrown> arlAllLiveStock) {
            this.context = getActivity();
            this.arlAllLiveStock = arlAllLiveStock;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_all_raw_data, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override

        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

            final LiveStockGrown objLiveStockGrown = arlAllLiveStock.get(i);
            viewHolder.txtSectorName.setText(objLiveStockGrown.getProduceName());

            viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, LiveStockGrownActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("DAIRYEDIT", objLiveStockGrown);
                    b.putBoolean("ISEDIT", true);
                    intent.putExtras(b);
                    startActivityForResult(intent, 124);

                }
            });


        }

        @Override
        public int getItemCount() {
            return arlAllLiveStock.size();
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

    private void apiEditDairy(JsonObject send) {
        Call<PostResponse> call = ApiClient.getPostService().editDairyFarmer(send);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                objPostResponse = response.body();
                Toast.makeText(getContext(), objPostResponse.getMessage(), Toast.LENGTH_LONG).show();
                Intent intentSplash = new Intent(getContext(), NavHomeActivity.class);
                startActivity(intentSplash);

            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Data Insertion failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void apiAddDairy(JsonObject send) {
        Call<PostResponse> call = ApiClient.getPostService().addDairyFarmer(send);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                objPostResponse = response.body();
                Toast.makeText(getContext(), objPostResponse.getMessage(), Toast.LENGTH_LONG).show();
                Intent intentSplash = new Intent(getContext(), NavHomeActivity.class);
                startActivity(intentSplash);

            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Data Insertion failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
