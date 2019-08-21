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
import com.example.foodgospel.Activity.PoultryCategoryActivity;
import com.example.foodgospel.Adapters.CategoryArrayAdapter;
import com.example.foodgospel.Adapters.DeliveryArrayAdapter;
import com.example.foodgospel.Adapters.ProduceListArrayAdapter;
import com.example.foodgospel.Global.AddDairyAllData;
import com.example.foodgospel.Global.AddPoultryFarmAllData;
import com.example.foodgospel.Global.ApiClient;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
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
public class AddPoultryFarmStepFourFragment extends Fragment {


    private Spinner spPoultryCategory, spDelieveryOption, spProduceList, spHours, spOptions;
    private RecyclerView rvSelectedPoultryList;
    private String strDelieveryTime = "", strColdChainPatnerName = "", strSendOption = "", strHours = "", strProductUse = "", strDeliveryOption = "";
    private ArrayList<Category> arlCategory = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlDelieveryOptions = new ArrayList<>();
    private ArrayList<DelieveryOptions> arlSelectedDelieveryOptions = new ArrayList<>();
    private ArrayList<ProduceList> arlProduceList = new ArrayList<>();
    private ArrayList<LiveStockGrown> arlLiveStockId = new ArrayList<>();
    private ArrayList<Category> arlSelectedCategory = new ArrayList<>();
    private SectorRepository objSectorRepository;
    private LinearLayout llDelieveryLayout;
    private EditText etDelieveryTime, etPartnerName;
    AddPoultryFarmAllData objAddPoultryFarmAllData;
    Button btnSubmit;
    PostResponse objPostResponse;
    private UserFarmer objFarmer;
    private Boolean isEditStepFour;
    ViewPoultryListAdapter rcvAdapter;
    ProduceListArrayAdapter objProductListArrayAdapter;
    DeliveryArrayAdapter objDeliveryArrayAdapter;
    private LinearLayout linDelieveryOptionsLayout,linParent,liSpinner,liTime,liColdChain;

    public AddPoultryFarmStepFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_poultry_step_four, container, false);

        objAddPoultryFarmAllData = (AddPoultryFarmAllData) getArguments().getSerializable("PoultryStepThree");

        isEditStepFour = getArguments().getBoolean("isEditThree", false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Step 4");

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        spPoultryCategory = view.findViewById(R.id.spPoultryCategory);
        spDelieveryOption = view.findViewById(R.id.spDelieveryOption);
        rvSelectedPoultryList = view.findViewById(R.id.rvSelectedPoultryList);
        llDelieveryLayout = view.findViewById(R.id.llDelieveryLayout);
        spProduceList = view.findViewById(R.id.spProduceList);

        arlCategory.add(new Category(0, "0", "0", "Select Category"));

        objSectorRepository = new SectorRepository(getActivity());
        objSectorRepository.getAllSectorWiseCategory(4).observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> category) {
                arlCategory.addAll(category);
                arlCategory.remove(1);
            }
        });

        spPoultryCategory.setAdapter(new CategoryArrayAdapter(getActivity(), R.layout.item_spinner, arlCategory));


        spPoultryCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Selected Product Name" + parent.getSelectedItem()
                        , Toast.LENGTH_LONG).show();

                if (position == 0) {

                } else {
                    if (!arlSelectedCategory.contains(arlCategory.get(position))) {
                        Intent intent = new Intent(getActivity(), PoultryCategoryActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("CATEGORY", arlCategory.get(position));
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

        arlDelieveryOptions.clear();

        arlDelieveryOptions.add(new DelieveryOptions(0, "Select Delivery Options", ""));
        arlDelieveryOptions.add(new DelieveryOptions(1, "Sends Delivery", "sends_delivery"));
        arlDelieveryOptions.add(new DelieveryOptions(2, "Food Gospel Pickup", "food_gospel_pickup"));
        arlDelieveryOptions.add(new DelieveryOptions(3, "Cold Chain Pickup", "cold_chain_pickup"));

        //spDelieveryOption.setAdapter(new DeliveryArrayAdapter(getActivity(), R.layout.item_spinner, arlDelieveryOptions));

        objDeliveryArrayAdapter = new DeliveryArrayAdapter(getActivity(), R.layout.item_spinner, arlDelieveryOptions);
        spDelieveryOption.setAdapter(objDeliveryArrayAdapter);

        spDelieveryOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    if (!arlSelectedDelieveryOptions.contains(arlDelieveryOptions.get(position))) {
                        arlSelectedDelieveryOptions.add(arlDelieveryOptions.get(position));
                        inflateDelieveryOption(arlDelieveryOptions.get(position));
                    }

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

        objProductListArrayAdapter = new ProduceListArrayAdapter(getActivity(),R.layout.item_spinner,arlProduceList);
        spProduceList.setAdapter(objProductListArrayAdapter);
//        spProduceList.setAdapter(new ProduceListArrayAdapter(getContext(), R.layout.item_spinner, arlProduceList));

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
            objFarmer = (UserFarmer) getArguments().getSerializable("PoultryEditStepThree");

            objAddPoultryFarmAllData.setFarmer_id(Integer.parseInt(objFarmer.getFarmerId()));


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

            arlSelectedCategory.addAll((objFarmer.getPoultry().getSector()).get(0).getArlCategoryList());
            rvSelectedPoultryList.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvAdapter = new ViewPoultryListAdapter(arlSelectedCategory);
            rvSelectedPoultryList.setAdapter(rcvAdapter);

        }

        btnSubmit = view.findViewById(R.id.btnSubmit);

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

                objAddPoultryFarmAllData.setDelivery(strDeliveryOption);
                objAddPoultryFarmAllData.setDelivery_time(strDelieveryTime);
                objAddPoultryFarmAllData.setProduct_use(strProductUse);
                objAddPoultryFarmAllData.setSend_option(strSendOption);
                objAddPoultryFarmAllData.setTime_type(strHours);
                objAddPoultryFarmAllData.setCold_chain_store(strColdChainPatnerName);
                objAddPoultryFarmAllData.setUser_id(Integer.parseInt(FoodGospelSharedPrefernce.getSharePrefernceData(getActivity(), "USERID")));

                Gson gson = new Gson();
                objAddPoultryFarmAllData.setArlPoultryGrown(arlSelectedCategory);
                String json = gson.toJson(objAddPoultryFarmAllData);
                Log.d("AllPoultryData : ", json);
                JsonObject object = new JsonObject();
                object.addProperty("AddPoultryFarm", json);
//                AddPoultryFarm
                if (GlobalHandler.checkconnection(getContext())) {
                    if (isEditStepFour) {
                        apiEditPoultryFarmData(object);
                    } else {
                        apiAddPoultryFarmData(object);
                        objSectorRepository.insertPoultryData(objAddPoultryFarmAllData);
                    }

                } else {
                    objSectorRepository.insertPoultryData(objAddPoultryFarmAllData);
                }
            }
        });

        return view;
    }

    public void inflateDelieveryOption(DelieveryOptions objDeliveryoption) {

        LayoutInflater objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View objView = objLayoutInflater.inflate(R.layout.raw_delievery_option, null);

        etDelieveryTime = objView.findViewById(R.id.etDelieveryTime);
        etPartnerName = objView.findViewById(R.id.etPartnerName);
        spOptions = objView.findViewById(R.id.spOptions);
        spHours = objView.findViewById(R.id.spHours);
        linDelieveryOptionsLayout=objView.findViewById(R.id.linDelieveryOptionsLayout);
        linParent=objView.findViewById(R.id.linParent);
        liColdChain=objView.findViewById(R.id.liColdChain);
        liSpinner=objView.findViewById(R.id.liSpinner);
        liTime=objView.findViewById(R.id.liTime);


        if (objDeliveryoption.getDelieveryName() == "Food Gospel Pickup") {
           // spHours.setVisibility(View.GONE);
            liTime.setVisibility(View.GONE);
            linParent.setVisibility(View.GONE);
            liSpinner.setVisibility(View.VISIBLE);
            liColdChain.setVisibility(View.GONE);
            //etPartnerName.setVisibility(View.GONE);
           // etDelieveryTime.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName() == "Cold Chain Pickup") {
           // spHours.setVisibility(View.GONE);
            linParent.setVisibility(View.GONE);
            liSpinner.setVisibility(View.GONE);
            liTime.setVisibility(View.GONE);
            liColdChain.setVisibility(View.VISIBLE);
           // etPartnerName.setVisibility(View.GONE);
           // etDelieveryTime.setVisibility(View.GONE);
           // spOptions.setVisibility(View.GONE);
            strDeliveryOption = objDeliveryoption.getDelieveryNameCode();
        } else if (objDeliveryoption.getDelieveryName() == "Sends Delivery") {
            //etPartnerName.setVisibility(View.GONE);
            linParent.setVisibility(View.VISIBLE);
            liSpinner.setVisibility(View.GONE);
            liTime.setVisibility(View.GONE);
            liColdChain.setVisibility(View.GONE);
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
            Category objCategory = (Category) bundle.getSerializable("ADDEDCROPPRODUCT");
            arlSelectedCategory.remove(objCategory);
            arlSelectedCategory.add(objCategory);

            rcvAdapter = new AddPoultryFarmStepFourFragment.ViewPoultryListAdapter(arlSelectedCategory);
            rvSelectedPoultryList.setAdapter(rcvAdapter);

        } else if (requestCode == 124 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Category objCategory = (Category) bundle.getSerializable("ADDEDCROPPRODUCT");

            arlSelectedCategory.remove(objCategory);
            arlSelectedCategory.add(objCategory);

            rcvAdapter = new AddPoultryFarmStepFourFragment.ViewPoultryListAdapter(arlSelectedCategory);
            rvSelectedPoultryList.setAdapter(rcvAdapter);

        }
    }

    private void apiAddPoultryFarmData(JsonObject send) {
        Call<PostResponse> call = ApiClient.getPostService().addPoultryFarmer(send);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                objPostResponse = response.body();
                Toast.makeText(getContext(), objPostResponse.getMessage(), Toast.LENGTH_LONG).show();
                FoodGospelSharedPrefernce.setSharePrefernceData(getContext(), "poultryFarmerId", String.valueOf(objPostResponse.getFarmer_id()));
                Intent intentSplash = new Intent(getContext(), NavHomeActivity.class);
                startActivity(intentSplash);

            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Data Insertion failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void apiEditPoultryFarmData(JsonObject send) {
        Call<PostResponse> call = ApiClient.getPostService().editPoultryFarmer(send);

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


    class ViewPoultryListAdapter extends RecyclerView.Adapter<AddPoultryFarmStepFourFragment.ViewPoultryListAdapter.ViewHolder> {

        Context context;
        ArrayList<Category> arlCategoryList = new ArrayList<>();

        public ViewPoultryListAdapter(ArrayList<Category> arlCategoryList) {
            this.context = getActivity();
            this.arlCategoryList = arlCategoryList;
        }

        @NonNull
        @Override
        public AddPoultryFarmStepFourFragment.ViewPoultryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_all_raw_data, viewGroup, false);
            return new AddPoultryFarmStepFourFragment.ViewPoultryListAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final AddPoultryFarmStepFourFragment.ViewPoultryListAdapter.ViewHolder viewHolder, final int i) {

            final Category objCategory = arlCategoryList.get(i);
            viewHolder.txtSectorName.setText(objCategory.getCategoryName());

            viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PoultryCategoryActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("POULTRYEDIT", objCategory);
                    b.putBoolean("ISEDIT", true);
                    intent.putExtras(b);
                    startActivityForResult(intent, 124);

                }
            });

        }

        @Override
        public int getItemCount() {
            return arlCategoryList.size();
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
}
