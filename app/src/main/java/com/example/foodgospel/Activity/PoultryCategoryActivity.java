package com.example.foodgospel.Activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foodgospel.Adapters.CropArrayAdapter;
import com.example.foodgospel.Global.CustomLayout;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;

import java.util.ArrayList;
import java.util.List;

public class PoultryCategoryActivity extends AppCompatActivity {

    SectorRepository objSectorRepository;
    private Spinner spPoultryGrown;
    private ArrayList<Crop> arlCropList = new ArrayList<>();
    private ArrayList<Category> arlCategoryList = new ArrayList<>();
    private EditText etNoLivestock, etOtherProduct;
    private CustomLayout custom_layout_product;
    private Button btnSave;
    private TextView txtSelectedCategoryName;
    private Toolbar toolbar;
    private LinearLayout linChild;
    Boolean isEdit;
    Category objCategory, objCategoryEdit;
    private String CategoryName;
    final ArrayList<Crop> arlSelectedCropList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_poultry_farm);

        objSectorRepository = new SectorRepository(getApplicationContext());

        isEdit = this.getIntent().getBooleanExtra("ISEDIT", false);


        etNoLivestock = findViewById(R.id.etNoLivestock);

        etOtherProduct = findViewById(R.id.etOtherProduct);

        spPoultryGrown = findViewById(R.id.spPoultryGrown);

        custom_layout_product = findViewById(R.id.custom_layout_product);

        txtSelectedCategoryName = findViewById(R.id.txtSelectedCategoryName);

        linChild = findViewById(R.id.linChild);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave = findViewById(R.id.btnSave);

        if (isEdit) {
            objCategoryEdit = (Category) getIntent().getExtras().getSerializable("POULTRYEDIT");
            CategoryName = objCategoryEdit.getCategoryName();
            arlSelectedCropList.addAll(objCategoryEdit.getArlCrop());
            for (int i = 0; i < arlSelectedCropList.size(); i++) {
                addProduct(arlSelectedCropList.get(i));
            }
        } else {

            objCategory = (Category) getIntent().getExtras().getSerializable("CATEGORY");
            CategoryName = objCategory.getCategoryName();

        }

        txtSelectedCategoryName.setText("Livestock Grown");
        toolbar.setTitle(CategoryName);

        arlCropList = getCropList(19);

        spPoultryGrown.setAdapter(new CropArrayAdapter(getApplicationContext(), R.layout.item_spinner, arlCropList));


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
                            addProduct(arlCropList.get(position));
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

                Intent intent = new Intent();
                Bundle i = new Bundle();
                if (isEdit) {
                    objCategoryEdit.setArlCrop(arlSelectedCropList);
                    i.putSerializable("ADDEDCROPPRODUCT", objCategoryEdit);
                } else {
                    objCategory.setArlCrop(arlSelectedCropList);
                    i.putSerializable("ADDEDCROPPRODUCT", objCategory);
                }
                intent.putExtras(i);
                setResult(RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();

            }
        });

    }

    public ArrayList<Crop> getCropList(int categoryId) {
        final ArrayList<Crop> arlCrop = new ArrayList<>();
        arlCrop.add(new Crop(0, "0", "0", "Select"));
        objSectorRepository.getAllCategoryWiseCropData(categoryId).observe(this, new Observer<List<Crop>>() {
            @Override
            public void onChanged(@Nullable List<Crop> crop) {
                arlCrop.addAll(crop);
            }
        });
        return arlCrop;
    }

    private void addProduct(final Crop objcrop) {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.raw_poultry_items, null);

        ((TextView) view.findViewById(R.id.itemtextview)).setText(objcrop.getCropName());

        final EditText etNoOfPoultry = view.findViewById(R.id.etNoOfPoultry);
        ImageView imgClose = view.findViewById(R.id.imgClose);

        if (isEdit) {
            etNoOfPoultry.setText(String.valueOf(objcrop.getNo_of_poultry()));
        }

        etNoOfPoultry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isEdit) {
                    objcrop.setNo_of_poultry(Integer.parseInt(etNoOfPoultry.getText().toString()));
                } else {
                    objcrop.setNo_of_poultry(Integer.parseInt(etNoOfPoultry.getText().toString()));
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlSelectedCropList.remove(objcrop);
                custom_layout_product.removeView(view);
            }
        });

        custom_layout_product.addView(view);
    }
}
