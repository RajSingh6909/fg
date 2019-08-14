
package com.example.foodgospel.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFarmer implements Serializable {

    @SerializedName("farm_type")
    @Expose
    private String farmType;
    @SerializedName("farmer_id")
    @Expose
    private String farmerId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("contact_mode")
    @Expose
    private String contactMode;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("market_email")
    @Expose
    private String marketEmail;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("mailing_address")
    @Expose
    private String mailingAddress;
    @SerializedName("land_ownership")
    @Expose
    private String landOwnership;
    @SerializedName("land_hold")
    @Expose
    private String landHold;
    @SerializedName("land_hold_type")
    @Expose
    private String landHoldType;
    @SerializedName("language")
    @Expose
    private Language language;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("send_option")
    @Expose
    private String sendOption;
    @SerializedName("cold_chain_store")
    @Expose
    private String coldChainStore;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("time_type")
    @Expose
    private String timeType;
    @SerializedName("product_use")
    @Expose
    private String productUse;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("sector")
    @Expose
    private ArrayList<Sector> sector = null;
    @SerializedName("farm")
    @Expose
    private Farm farm;
    @SerializedName("dairy")
    @Expose
    private Dairy dairy = null;
    @SerializedName("poultry")
    @Expose
    private Poultry poultry = null;

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getContactMode() {
        return contactMode;
    }

    public void setContactMode(String contactMode) {
        this.contactMode = contactMode;
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

    public String getMarketEmail() {
        return marketEmail;
    }

    public void setMarketEmail(String marketEmail) {
        this.marketEmail = marketEmail;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getLandOwnership() {
        return landOwnership;
    }

    public void setLandOwnership(String landOwnership) {
        this.landOwnership = landOwnership;
    }

    public String getLandHold() {
        return landHold;
    }

    public void setLandHold(String landHold) {
        this.landHold = landHold;
    }

    public String getLandHoldType() {
        return landHoldType;
    }

    public void setLandHoldType(String landHoldType) {
        this.landHoldType = landHoldType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getSendOption() {
        return sendOption;
    }

    public void setSendOption(String sendOption) {
        this.sendOption = sendOption;
    }

    public String getColdChainStore() {
        return coldChainStore;
    }

    public void setColdChainStore(String coldChainStore) {
        this.coldChainStore = coldChainStore;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getProductUse() {
        return productUse;
    }

    public void setProductUse(String productUse) {
        this.productUse = productUse;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<Sector> getSector() {
        return sector;
    }

    public void setSector(ArrayList<Sector> sector) {
        this.sector = sector;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Dairy getDairy() {
        return dairy;
    }

    public void setDairy(Dairy dairy) {
        this.dairy = dairy;
    }

    public Poultry getPoultry() {
        return poultry;
    }

    public void setPoultry(Poultry poultry) {
        this.poultry = poultry;
    }
}
