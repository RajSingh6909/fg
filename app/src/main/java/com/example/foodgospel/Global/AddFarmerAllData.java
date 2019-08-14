package com.example.foodgospel.Global;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.foodgospel.Models.Sector;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class AddFarmerAllData implements Serializable {

    @Ignore
    AddFarmerAllData objAddFarmerAllData;

    @PrimaryKey(autoGenerate = true)
    public int id;

    int user_id, farm_type, language, state, district, city,farmer_id;
    String name = "", farm_name = "", contact_mode = "", landline = "", mobile = "", whatsapp = "", email = "", market_email = "", sex = "", qualification = "", latitude = "", longitude = "", address = "", mailing_address = "", land_ownership = "", land_hold = "", land_hold_type = "", delivery = "", send_option = "", cold_chain_store = "", delivery_time = "", time_type = "", product_use = "", age = "", postal_code;

    @Ignore
    private ArrayList<Sector> arlSectors = new ArrayList<>();

    public AddFarmerAllData() {
    }

    @Ignore
    public AddFarmerAllData(AddFarmerAllData objAddFarmerAllData,ArrayList<Sector> arlSectors) {
       this.objAddFarmerAllData = objAddFarmerAllData;
       this.arlSectors.addAll(arlSectors);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFarm_type() {
        return farm_type;
    }

    public void setFarm_type(int farm_type) {
        this.farm_type = farm_type;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public String getContact_mode() {
        return contact_mode;
    }

    public void setContact_mode(String contact_mode) {
        this.contact_mode = contact_mode;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarket_email() {
        return market_email;
    }

    public void setMarket_email(String market_email) {
        this.market_email = market_email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMailing_address() {
        return mailing_address;
    }

    public void setMailing_address(String mailing_address) {
        this.mailing_address = mailing_address;
    }

    public String getLand_ownership() {
        return land_ownership;
    }

    public void setLand_ownership(String land_ownership) {
        this.land_ownership = land_ownership;
    }

    public String getLand_hold() {
        return land_hold;
    }

    public void setLand_hold(String land_hold) {
        this.land_hold = land_hold;
    }

    public String getLand_hold_type() {
        return land_hold_type;
    }

    public void setLand_hold_type(String land_hold_type) {
        this.land_hold_type = land_hold_type;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getSend_option() {
        return send_option;
    }

    public void setSend_option(String send_option) {
        this.send_option = send_option;
    }

    public String getCold_chain_store() {
        return cold_chain_store;
    }

    public void setCold_chain_store(String cold_chain_store) {
        this.cold_chain_store = cold_chain_store;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public String getProduct_use() {
        return product_use;
    }

    public void setProduct_use(String product_use) {
        this.product_use = product_use;
    }

    public ArrayList<Sector> getArlSectors() {
        return arlSectors;
    }

    public void setArlSectors(ArrayList<Sector> arlSectors) {
        this.arlSectors = arlSectors;
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
    }
}
