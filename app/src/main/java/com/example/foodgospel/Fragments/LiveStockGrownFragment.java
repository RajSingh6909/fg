package com.example.foodgospel.Fragments;


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

import com.example.foodgospel.Adapters.MilkProductArrayAdapter;
import com.example.foodgospel.Global.CustomLayout;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveStockGrownFragment extends Fragment {
    private TextView txtSelectedCrownName;
    private Spinner spProductName;
    private EditText etNoLivestock,etOtherProduct;
    private Button btnSave;
    private CustomLayout clSelectedCrown;
    private Toolbar toolbar;
    private SectorRepository objSectorRepository;
    private ArrayList<LiveStockGrown> arlLiveStockGrownList = new ArrayList<>();
    private ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();
    private LinearLayout linChild;
    private int productId;


    public LiveStockGrownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_stock_grown, container, false);

        objSectorRepository = new SectorRepository(getActivity());

        final LiveStockGrown objLiveStockGrown = (LiveStockGrown) getArguments().getSerializable("LIVESTOCKGROWN");

        arlLiveStockGrownList = (ArrayList<LiveStockGrown>) getArguments().getSerializable("SELECTEDLIVESTOCKGROWN");

        arlLiveStockGrownList.indexOf(objLiveStockGrown.getId());

        txtSelectedCrownName = view.findViewById(R.id.txtSelectedCrownName);

        spProductName = view.findViewById(R.id.spProductName);

        etNoLivestock = view.findViewById(R.id.etNoLivestock);

        etOtherProduct = view.findViewById(R.id.etOtherProduct);

        linChild = view.findViewById(R.id.linChild);

        clSelectedCrown = view.findViewById(R.id.custom_layout_product);

        btnSave = view.findViewById(R.id.btnSave);

        txtSelectedCrownName.setText(objLiveStockGrown.getProduceName());

        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setTitle(objLiveStockGrown.getProduceName());

        productId = objLiveStockGrown.getId();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        arlMilkProduct.clear();

        arlMilkProduct = getProductList();

        spProductName.setAdapter(new MilkProductArrayAdapter(getActivity(), R.layout.item_spinner, arlMilkProduct));

        final ArrayList<MilkProduct> arlSelectedMilkProduct = new ArrayList<>();

        spProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    if (!arlSelectedMilkProduct.contains(arlMilkProduct.get(position))) {
                        arlSelectedMilkProduct.add(arlMilkProduct.get(position));
                        if (arlMilkProduct.get(position).getName().contains("Others")) {
                            linChild.setVisibility(View.VISIBLE);
                        } else {
                            addProduct(arlMilkProduct.get(position), clSelectedCrown);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etNoLivestock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arlLiveStockGrownList.get(arlLiveStockGrownList.indexOf(objLiveStockGrown)).setLiveStock(etNoLivestock.getText().toString());

            }
        });

        etOtherProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arlLiveStockGrownList.get(arlLiveStockGrownList.indexOf(objLiveStockGrown)).setOthers(etOtherProduct.getText().toString());

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlLiveStockGrownList.get(arlLiveStockGrownList.indexOf(objLiveStockGrown)).setArlMilkProduct(arlSelectedMilkProduct);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        return view;
    }


    public ArrayList<MilkProduct> getProductList() {
        final ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();
        arlMilkProduct.add(new MilkProduct(0, "0", "Select Product"));
        arlMilkProduct.add(new MilkProduct(0, "0", "Others"));
        objSectorRepository.getAllProductDataProduceType(productId).observe(getActivity(), new Observer<List<MilkProduct>>() {
            @Override
            public void onChanged(@Nullable List<MilkProduct> milkProducts) {
                arlMilkProduct.addAll(milkProducts);
            }
        });
        return arlMilkProduct;
    }

    private void addProduct(MilkProduct objMilkProduct, CustomLayout objCustomLayout) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.rounded_textview, null);
        ((TextView) view.findViewById(R.id.itemtextview)).setText(objMilkProduct.getName());

        objCustomLayout.addView(view);
    }
}
