package com.example.foodgospel.Activity;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodgospel.Activity.FarmerActivity;

import com.example.foodgospel.Adapters.CategoryArrayAdapter;
import com.example.foodgospel.Adapters.CropArrayAdapter;
import com.example.foodgospel.Adapters.HarvestMonthArrayAdapter;
import com.example.foodgospel.Adapters.MilkProductArrayAdapter;
import com.example.foodgospel.Global.AddFarmerAllData;
import com.example.foodgospel.Global.CustomLayout;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Models.Category;
import com.example.foodgospel.Models.Crop;
import com.example.foodgospel.Models.HarvestMonth;
import com.example.foodgospel.Models.MilkProduct;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.R;
import com.example.foodgospel.Repository.SectorRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectorActivity extends AppCompatActivity {

    TextView txtSectorName;
    Spinner spCategoryName;
    SectorRepository objSectorRepository;
    private Integer CategoryId, CropId, selectedCategoryId;
    private String SectorName = "", categoryName, cropName, SectorID = "";
    private ArrayList<Category> arlCategory = new ArrayList<>();
    ArrayList<Category> arlCategoryselected = new ArrayList<>();
    private ArrayList<Crop> arlCropName = new ArrayList<>();
    ArrayList<HarvestMonth> arlHarvestmonth = new ArrayList<>();
    Toolbar toolbar;
    FarmerActivity activity;
    LinearLayout llSelectedCategory;
    Button btnSubmit;
    Sector objSector, objSectorEdit;
    ArrayList<Sector> arlSector = new ArrayList<>();
    ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();
    int ProduceId;
    int postionarray;
    Boolean isEdit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sector);
        spCategoryName = findViewById(R.id.spCategoryName);
        llSelectedCategory = findViewById(R.id.llSelectedCategory);

        //Harvest Month list
        arlHarvestmonth.add(new HarvestMonth(0, "Select Harvest Month"));
        arlHarvestmonth.add(new HarvestMonth(1, "jan"));
        arlHarvestmonth.add(new HarvestMonth(2, "feb"));
        arlHarvestmonth.add(new HarvestMonth(3, "mar"));
        arlHarvestmonth.add(new HarvestMonth(4, "apr"));
        arlHarvestmonth.add(new HarvestMonth(5, "may"));
        arlHarvestmonth.add(new HarvestMonth(6, "jun"));
        arlHarvestmonth.add(new HarvestMonth(7, "jul"));
        arlHarvestmonth.add(new HarvestMonth(8, "aug"));
        arlHarvestmonth.add(new HarvestMonth(9, "sep"));
        arlHarvestmonth.add(new HarvestMonth(10, "oct"));
        arlHarvestmonth.add(new HarvestMonth(11, "nov"));
        arlHarvestmonth.add(new HarvestMonth(12, "dec"));
        objSectorRepository = new SectorRepository(this);

        arlCategory.add(new Category(0, "0", "0", "Select Category"));

        final Bundle bundle = this.getIntent().getExtras();

        isEdit = this.getIntent().getBooleanExtra("ISEDIT", false);

        if (isEdit) {
            objSectorEdit = (Sector) bundle.getSerializable("SECTOREDIT");
            SectorName = objSectorEdit.getName();
            SectorID = objSectorEdit.getSectorId();
            arlCategoryselected.addAll(objSectorEdit.getArlCategoryList());
            for (int i = 0; i < arlCategoryselected.size(); i++) {
                inflateViewCropCell(arlCategoryselected.get(i), arlCategoryselected);
            }
        }
        else {
            objSector = (Sector) bundle.getSerializable("SECTOR");
            arlSector.addAll((ArrayList<Sector>) bundle.getSerializable("SELECTEDSECTORS"));
            SectorName = objSector.getName();
            SectorID = objSector.getSectorId();
        }


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(SectorName);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSubmit = findViewById(R.id.btnSubmit);


        objSectorRepository.getAllSectorWiseCategory(Integer.parseInt(SectorID)).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> category) {
                arlCategory.addAll(category);

            }
        });

        spCategoryName.setAdapter(new CategoryArrayAdapter(this, R.layout.item_spinner, arlCategory));

        //OnClick of spCategory
        spCategoryName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {

                    if (!arlCategoryselected.contains(arlCategory.get(position))) {
                        arlCategoryselected.add(arlCategory.get(position));
                        inflateViewCropCell(arlCategory.get(position), arlCategoryselected);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle i = new Bundle();
                if (isEdit) {
                    objSectorEdit.setArlCategoryList(arlCategoryselected);
                    i.putSerializable("ADDEDSECTOR", objSectorEdit);
                } else {
                    objSector.setArlCategoryList(arlCategoryselected);
                    i.putSerializable("ADDEDSECTOR", objSector);
                }
                intent.putExtras(i);
                setResult(RESULT_OK, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }

        });
    }


    public ArrayList<Crop> getCropList(int categoryId) {
        final ArrayList<Crop> arlCrop = new ArrayList<>();
        arlCrop.add(new Crop(0, "0", "0", "Select Crop"));
        objSectorRepository.getAllCategoryWiseCropData(categoryId).observe((LifecycleOwner) this, new Observer<List<Crop>>() {
            @Override
            public void onChanged(@Nullable List<Crop> crop) {
                arlCrop.addAll(crop);
            }
        });
        return arlCrop;
    }


    public ArrayList<MilkProduct> getProductList() {
        final ArrayList<MilkProduct> arlMilkProduct = new ArrayList<>();
        arlMilkProduct.add(new MilkProduct(0, "0", "Select Product"));
        objSectorRepository.getAllProductData().observe(this, new Observer<List<MilkProduct>>() {
            @Override
            public void onChanged(@Nullable List<MilkProduct> milkProducts) {
                arlMilkProduct.addAll(milkProducts);
            }
        });
        return arlMilkProduct;
    }

    /*-----------------------------INFLATE VIEWS-------------------------------------------------------------------------*/

    public void inflateViewCropCell(final Category objCategory, final ArrayList<Category> arlCategory) {

        LayoutInflater objLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View objView = objLayoutInflater.inflate(R.layout.view_crop_cell, null);

        //  objView.setTag(objCategory.getCategoryId());

        TextView txtSelectedCategoryName = objView.findViewById(R.id.txtSelectedCategoryName);
        Spinner spCropName = objView.findViewById(R.id.spCropName);
        final LinearLayout llVHarvestData = objView.findViewById(R.id.llVHarvestData);
        ImageView imgClose = objView.findViewById(R.id.imgClose);

        txtSelectedCategoryName.setText(objCategory.getCategoryName());

        ArrayList<Crop> arlcrop = new ArrayList<>();
        arlcrop = getCropList(Integer.parseInt(objCategory.getCategoryId()));

        spCropName.setAdapter(new CropArrayAdapter(this, R.layout.item_spinner, arlcrop));
        final ArrayList<Crop> finalArlcrop = arlcrop;
        final ArrayList<Crop> arlCropselected = new ArrayList<>();


        if (isEdit) {
            arlCropselected.addAll(objCategory.getArlCrop());
            for (int i = 0; i < arlCropselected.size(); i++) {
                inflateHarvestCellView(llVHarvestData, arlCropselected.get(i), arlCropselected);
            }
        }

        spCropName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {

                    if (!arlCropselected.contains(finalArlcrop.get(position))) {
                        arlCropselected.add(finalArlcrop.get(position));
                        arlCategory.get(arlCategory.indexOf(objCategory)).setArlCrop(arlCropselected);//---------
                        inflateHarvestCellView(llVHarvestData, finalArlcrop.get(position), arlCropselected);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //removing selected category from the list
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlCategory.remove(arlCategory.indexOf(objCategory));
                llSelectedCategory.removeView(objView);

            }
        });

        llSelectedCategory.addView(objView);

    }

    public void inflateHarvestCellView(final LinearLayout liMainLayout, final Crop objCrop, final ArrayList<Crop> selected) {
        LayoutInflater objLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View objView = objLayoutInflater.inflate(R.layout.view_harvest_cell, null);
        TextView txtCropName = objView.findViewById(R.id.txtSelectedCropName);
        Spinner spHarvestmonth = objView.findViewById(R.id.spharvestName);
        ImageView imgClose = objView.findViewById(R.id.imgClose);
        final CustomLayout layout = objView.findViewById(R.id.custom_layout);

        final ArrayList<HarvestMonth> arlSelectedHarvestMonth = new ArrayList<>();

        txtCropName.setText(objCrop.getCropName());

        if (isEdit) {
            arlSelectedHarvestMonth.addAll(objCrop.getArlHarvestmonth());
            for (int i = 0; i < arlSelectedHarvestMonth.size(); i++) {
                addMonth(arlSelectedHarvestMonth.get(i), layout, arlSelectedHarvestMonth);
            }
        }

        if (objCrop.getCategoryId().equals("18") || objCrop.getCategoryId().equals("19")) {
            //For Animal Husbandry

            LayoutInflater objLayoutInflaterMilk = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View objViewMilk = objLayoutInflaterMilk.inflate(R.layout.raw_milk_data, null);
            LinearLayout linMain = objViewMilk.findViewById(R.id.linMain);
            TextView txtSelectedCropName = objViewMilk.findViewById(R.id.txtSelectedCropName);
            final EditText etLiveStock = objViewMilk.findViewById(R.id.etNoLivestock);
            Spinner spProductName = objViewMilk.findViewById(R.id.spProductName);
            ImageView imgCloseMilk = objViewMilk.findViewById(R.id.imgClose);
            final CustomLayout objLayout = objViewMilk.findViewById(R.id.custom_layout_product);

            if (objCrop.getIs_produce() == 1) {

                txtSelectedCropName.setText(objCrop.getCropName());

                etLiveStock.setText(objCrop.getLiveStock());

                arlMilkProduct = getProductList();

                spProductName.setAdapter(new MilkProductArrayAdapter(this, R.layout.item_spinner, arlMilkProduct));

                final ArrayList<MilkProduct> arlSelectedMilkProduct = new ArrayList<>();

                if (isEdit) {
                    arlSelectedMilkProduct.addAll(objCrop.getArlMilkProduct());
                    for (int i = 0; i < arlSelectedMilkProduct.size(); i++) {
                        addProduct(arlSelectedMilkProduct.get(i), objLayout);
                    }
                }

                spProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {

                        } else {
                            if (!arlSelectedMilkProduct.contains(arlMilkProduct.get(position))) {
                                arlSelectedMilkProduct.add(arlMilkProduct.get(position));
                                selected.get(selected.indexOf(objCrop)).setArlMilkProduct(arlSelectedMilkProduct);
                                addProduct(arlMilkProduct.get(position), objLayout);
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                etLiveStock.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (isEdit) {
                            objCrop.setLiveStock(etLiveStock.getText().toString());
                        } else {
                            selected.get(selected.indexOf(objCrop)).setLiveStock(etLiveStock.getText().toString());
                        }

                    }
                });

                liMainLayout.addView(objViewMilk);
            } else {
                linMain.setVisibility(View.GONE);
                spProductName.setVisibility(View.GONE);
                txtSelectedCropName.setText(objCrop.getCropName());
                liMainLayout.addView(objViewMilk);
            }

            imgCloseMilk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected.remove(selected.indexOf(objCrop));

                    if (isEdit) {
                        for (Category cat : arlCategoryselected) {
                            if (cat.getCategoryId().equals((objCrop.getCategoryId()))) {
                                arlCategoryselected.get(arlCategoryselected.indexOf(cat)).setArlCrop(selected);
                            }
                        }
                    }

                    liMainLayout.removeView(objViewMilk);

                }
            });

        } else {
            //For Harvest Month Spinner data

            spHarvestmonth.setAdapter(new HarvestMonthArrayAdapter(this, R.layout.item_spinner, arlHarvestmonth));
            spHarvestmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {

                    } else {
                        if (!arlSelectedHarvestMonth.contains(arlHarvestmonth.get(position))) {
                            arlSelectedHarvestMonth.add(arlHarvestmonth.get(position));
                            selected.get(selected.indexOf(objCrop)).setArlHarvestmonth(arlSelectedHarvestMonth);
                            addMonth(arlHarvestmonth.get(position), layout, arlSelectedHarvestMonth);

                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            liMainLayout.addView(objView);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.remove(selected.indexOf(objCrop));

                //kachra logic
                if (isEdit) {
                    for (Category cat : arlCategoryselected) {
                        if (cat.getCategoryId().equals((objCrop.getCategoryId()))) {
                            arlCategoryselected.get(arlCategoryselected.indexOf(cat)).setArlCrop(selected);
                        }
                    }
                }
                liMainLayout.removeView(objView);
            }
        });


    }

    private void addMonth(final HarvestMonth objHarvestMonth, final CustomLayout objCustomLayout, final ArrayList<HarvestMonth> selectedmonths) {

        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.rounded_textview, null);
        ((TextView) view.findViewById(R.id.itemtextview)).setText(objHarvestMonth.getMonthName());

        ImageView imgClose = view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedmonths.remove(objHarvestMonth);
                objCustomLayout.removeView(view);


            }
        });

        objCustomLayout.setTag(objHarvestMonth.getId());
        objCustomLayout.addView(view);

    }

    private void addProduct(MilkProduct objMilkProduct, CustomLayout objCustomLayout) {
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.rounded_textview, null);
        ((TextView) view.findViewById(R.id.itemtextview)).setText(objMilkProduct.getName());

        objCustomLayout.addView(view);
    }


}
