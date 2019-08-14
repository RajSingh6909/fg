package com.example.foodgospel.Fragments;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Adapters.CropArrayAdapter;
import com.example.foodgospel.Global.CustomLayout;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoultryFarmFragment extends Fragment {

    SectorRepository objSectorRepository;
    private Spinner spPoultryGrown;
    private ArrayList<Crop> arlCropList = new ArrayList<>();

    private ArrayList<Category> arlCategoryList = new ArrayList<>();
    private EditText etNoLivestock,etOtherProduct;
    private CustomLayout custom_layout_product;
    private Button btnSave;
    private TextView txtSelectedCategoryName;
    private Toolbar toolbar;
    private LinearLayout linChild;
    public PoultryFarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poultry_farm, container, false);

        objSectorRepository = new SectorRepository(getActivity());

        final Category objCategory = (Category) getArguments().getSerializable("CATEGORY");

        arlCategoryList = (ArrayList<Category>) getArguments().getSerializable("SELECTEDCATEGORY");

        arlCategoryList.indexOf(objCategory.getCategoryId());

        etNoLivestock = view.findViewById(R.id.etNoLivestock);

        etOtherProduct = view.findViewById(R.id.etOtherProduct);

        spPoultryGrown = view.findViewById(R.id.spPoultryGrown);

        custom_layout_product = view.findViewById(R.id.custom_layout_product);

        txtSelectedCategoryName = view.findViewById(R.id.txtSelectedCategoryName);

        txtSelectedCategoryName.setText(objCategory.getCategoryName());

        linChild = view.findViewById(R.id.linChild);

        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setTitle(objCategory.getCategoryName());

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSave = view.findViewById(R.id.btnSave);

        arlCropList = getCropList(19);

        spPoultryGrown.setAdapter(new CropArrayAdapter(getActivity(), R.layout.item_spinner, arlCropList));

        final ArrayList<Crop> arlSelectedCropList = new ArrayList<>();


        spPoultryGrown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    if (!arlSelectedCropList.contains(arlCropList.get(position))) {
                        arlSelectedCropList.add(arlCropList.get(position));
                        if (arlCropList.get(position).getCropName().contains("Others")) {
                            linChild.setVisibility(View.VISIBLE);
                        } else {
                            addProduct(arlCropList.get(position), custom_layout_product);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlCategoryList.get(arlCategoryList.indexOf(objCategory)).setArlCrop(arlSelectedCropList);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        return view;
    }


    public ArrayList<Crop> getCropList(int categoryId) {
        final ArrayList<Crop> arlCrop = new ArrayList<>();
        arlCrop.add(new Crop(0, "0", "0", "Select"));
        objSectorRepository.getAllCategoryWiseCropData(categoryId).observe((LifecycleOwner) getActivity(), new Observer<List<Crop>>() {
            @Override
            public void onChanged(@Nullable List<Crop> crop) {
                arlCrop.addAll(crop);
            }
        });
        return arlCrop;
    }

    private void addProduct(final Crop objcrop, CustomLayout objCustomLayout) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.raw_poultry_items, null);
        ((TextView) view.findViewById(R.id.itemtextview)).setText(objcrop.getCropName());
        final EditText etNoOfPoultry = view.findViewById(R.id.etNoOfPoultry);
        etNoOfPoultry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arlCropList.get(arlCropList.indexOf(objcrop)).setNo_of_poultry(Integer.parseInt(etNoOfPoultry.getText().toString()));
            }
        });

        objCustomLayout.addView(view);
    }
}
