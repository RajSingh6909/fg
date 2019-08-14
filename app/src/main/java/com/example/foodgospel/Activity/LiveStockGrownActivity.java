package com.example.foodgospel.Activity;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Adapters.MilkProductArrayAdapter;
import com.example.foodgospel.Global.CustomLayout;
import com.example.foodgospel.Models.LiveStockGrown;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveStockGrownActivity extends AppCompatActivity {
    private TextView txtSelectedCrownName;
    private Spinner spProductName;
    private EditText etNoLivestock, etOtherProduct;
    private Button btnSave;
    private CustomLayout clSelectedCrown;
    private Toolbar toolbar;
    private SectorRepository objSectorRepository;
    //  private ArrayList<LiveStockGrown> arlLiveStockGrownList = new ArrayList<>();
    private ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();
    private LinearLayout linChild;
    private int productId;
    LiveStockGrown objLiveStockGrown, objLiveStockGrownEdit;
    Boolean isEdit;
    ImageView imgcloselinear;
    private String productName;
    private ArrayList<MilkProduct> arlSelectedMilkProduct = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_live_stock_grown);

        objSectorRepository = new SectorRepository(getApplicationContext());

//        arlLiveStockGrownList.indexOf(objLiveStockGrown.getId());

        isEdit = this.getIntent().getBooleanExtra("ISEDIT", false);


        txtSelectedCrownName = findViewById(R.id.txtSelectedCrownName);

        spProductName = findViewById(R.id.spProductName);

        etNoLivestock = findViewById(R.id.etNoLivestock);

        etOtherProduct = findViewById(R.id.etOtherProduct);

        linChild = findViewById(R.id.linChild);

        clSelectedCrown = findViewById(R.id.custom_layout_product);

        btnSave = findViewById(R.id.btnSave);

        toolbar = findViewById(R.id.toolbar);

        imgcloselinear = findViewById(R.id.imgCloseother);


        if (isEdit) {
            objLiveStockGrownEdit = (LiveStockGrown) getIntent().getExtras().getSerializable("DAIRYEDIT");
            productId = objLiveStockGrownEdit.getId();
            productName = objLiveStockGrownEdit.getProduceName();
            etNoLivestock.setText(objLiveStockGrownEdit.getLiveStock());
            arlSelectedMilkProduct.addAll(objLiveStockGrownEdit.getArlMilkProduct());
            for (int i = 0; i < arlSelectedMilkProduct.size(); i++) {
                addProduct(arlSelectedMilkProduct.get(i));
            }
//            if (!objLiveStockGrownEdit.getOthers().equals("")) {
//                linChild.setVisibility(View.VISIBLE);
//                etOtherProduct.setText(objLiveStockGrownEdit.getOthers());
//            } else {
//
//            }
        } else {
            objLiveStockGrown = (LiveStockGrown) getIntent().getExtras().getSerializable("LIVESTOCKGROWN");
            // arlLiveStockGrownList.indexOf(objLiveStockGrown.getId());
            //  arlLiveStockGrownList.addAll((ArrayList<LiveStockGrown>) getIntent().getExtras().getSerializable("SELECTEDLIVESTOCKGROWN"));
            productId = objLiveStockGrown.getId();
            productName = objLiveStockGrown.getProduceName();
        }

        txtSelectedCrownName.setText(productName);
        toolbar.setTitle(productName);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arlMilkProduct.add(new MilkProduct(0, "0", "Select Product"));
        arlMilkProduct.add(new MilkProduct(0, "0", "Others"));
        objSectorRepository.getAllProductDataProduceType(productId).observe(this, new Observer<List<MilkProduct>>() {
            @Override
            public void onChanged(@Nullable List<MilkProduct> milkProducts) {
                arlMilkProduct.addAll(milkProducts);
            }
        });

        spProductName.setAdapter(new MilkProductArrayAdapter(this, R.layout.item_spinner, arlMilkProduct));

        imgcloselinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    arlSelectedMilkProduct.remove(arlMilkProduct.get(1));
                    objLiveStockGrownEdit.setOthers("");
                } else {
                    arlSelectedMilkProduct.remove(arlMilkProduct.get(1));
                    objLiveStockGrown.setOthers("");
                }
                linChild.setVisibility(View.GONE);
            }
        });
        spProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//

                if (position == 0) {

                } else {
                    Toast.makeText(getApplicationContext(), arlMilkProduct.get(position).getName(), Toast.LENGTH_LONG).show();
                    if (!arlSelectedMilkProduct.contains(arlMilkProduct.get(position))) {
                        arlSelectedMilkProduct.add(arlMilkProduct.get(position));
                        if (arlMilkProduct.get(position).getName().equals("Others")) {
                            linChild.setVisibility(View.VISIBLE);
                        } else {
                            //  Toast.makeText(LiveStockGrownActivity.this, arlMilkProduct.get(position).getName(), Toast.LENGTH_LONG).show();
                            addProduct(arlMilkProduct.get(position));

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
                if (isEdit) {
                    objLiveStockGrownEdit.setLiveStock(etNoLivestock.getText().toString());
                } else {
                    objLiveStockGrown.setLiveStock(etNoLivestock.getText().toString());
                }
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
                if (isEdit) {
                    objLiveStockGrownEdit.setOthers(etOtherProduct.getText().toString());
                } else {
                    objLiveStockGrown.setOthers(etOtherProduct.getText().toString());
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle i = new Bundle();
                if (isEdit) {
                    objLiveStockGrownEdit.setArlMilkProduct(arlSelectedMilkProduct);
                    i.putSerializable("ADDEDMILKPRODUCT", objLiveStockGrownEdit);
                } else {
                    objLiveStockGrown.setArlMilkProduct(arlSelectedMilkProduct);
                    i.putSerializable("ADDEDMILKPRODUCT", objLiveStockGrown);
                }
                intent.putExtras(i);
                setResult(RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }
        });
    }


    private void addProduct(final MilkProduct objMilkProduct) {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.rounded_textview, null);
        ImageView imgView = (ImageView) view.findViewById(R.id.imgClose);


        ((TextView) view.findViewById(R.id.itemtextview)).setText(objMilkProduct.getName());
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlSelectedMilkProduct.remove(objMilkProduct);
                clSelectedCrown.removeView(view);
            }
        });


        clSelectedCrown.addView(view);
    }
}
