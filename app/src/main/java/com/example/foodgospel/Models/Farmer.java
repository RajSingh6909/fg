package com.example.foodgospel.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.foodgospel.Models.City;
import com.example.foodgospel.Models.District;
import com.example.foodgospel.Models.Language;
import com.example.foodgospel.Models.Sector;
import com.example.foodgospel.Models.State;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Farmer implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
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
    private List<Sector> sector = null;
    @SerializedName("category")
    @Expose
    private List<Object> category = null;
    @SerializedName("crop")
    @Expose
    private List<Object> crop = null;
    @SerializedName("harvest_month")
    @Expose
    private List<Object> harvestMonth = null;

    protected Farmer(Parcel in) {
        id = in.readString();
        name = in.readString();
        farmName = in.readString();
        contactMode = in.readString();
        landline = in.readString();
        mobile = in.readString();
        whatsapp = in.readString();
        email = in.readString();
        marketEmail = in.readString();
        qualification = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        address = in.readString();
        postalCode = in.readString();
        mailingAddress = in.readString();
        landHold = in.readString();
        landHoldType = in.readString();
        delivery = in.readString();
        sendOption = in.readString();
        coldChainStore = in.readString();
        deliveryTime = in.readString();
        timeType = in.readString();
        productUse = in.readString();
        age = in.readString();
        sex = in.readString();
    }

    public static final Creator<Farmer> CREATOR = new Creator<Farmer>() {
        @Override
        public Farmer createFromParcel(Parcel in) {
            return new Farmer(in);
        }

        @Override
        public Farmer[] newArray(int size) {
            return new Farmer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Sector> getSector() {
        return sector;
    }

    public void setSector(List<Sector> sector) {
        this.sector = sector;
    }

    public List<Object> getCategory() {
        return category;
    }

    public void setCategory(List<Object> category) {
        this.category = category;
    }

    public List<Object> getCrop() {
        return crop;
    }

    public void setCrop(List<Object> crop) {
        this.crop = crop;
    }

    public List<Object> getHarvestMonth() {
        return harvestMonth;
    }

    public void setHarvestMonth(List<Object> harvestMonth) {
        this.harvestMonth = harvestMonth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(farmName);
        dest.writeString(contactMode);
        dest.writeString(landline);
        dest.writeString(mobile);
        dest.writeString(whatsapp);
        dest.writeString(email);
        dest.writeString(marketEmail);
        dest.writeString(qualification);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(address);
        dest.writeString(postalCode);
        dest.writeString(mailingAddress);
        dest.writeString(landHold);
        dest.writeString(landHoldType);
        dest.writeString(delivery);
        dest.writeString(sendOption);
        dest.writeString(coldChainStore);
        dest.writeString(deliveryTime);
        dest.writeString(timeType);
        dest.writeString(productUse);
        dest.writeString(age);
        dest.writeString(sex);
    }
}