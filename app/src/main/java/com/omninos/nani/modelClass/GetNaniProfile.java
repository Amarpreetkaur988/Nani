package com.omninos.nani.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class GetNaniProfile {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("verifyStatus")
        @Expose
        private String verifyStatus;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("activeStatus")
        @Expose
        private String activeStatus;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("bankName")
        @Expose
        private String bankName;
        @SerializedName("accountNumber")
        @Expose
        private String accountNumber;
        @SerializedName("accountHolderName")
        @Expose
        private String accountHolderName;
        @SerializedName("branchName")
        @Expose
        private String branchName;
        @SerializedName("branchCode")
        @Expose
        private String branchCode;
        @SerializedName("optionalPhone")
        @Expose
        private String optionalPhone;
        @SerializedName("specialities")
        @Expose
        private String specialities;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("refName1")
        @Expose
        private String refName1;
        @SerializedName("refContact1")
        @Expose
        private String refContact1;
        @SerializedName("refItem1")
        @Expose
        private String refItem1;
        @SerializedName("refName2")
        @Expose
        private String refName2;
        @SerializedName("refContact2")
        @Expose
        private String refContact2;
        @SerializedName("refItem2")
        @Expose
        private String refItem2;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("login_type")
        @Expose
        private String loginType;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("totalItems")
        @Expose
        private String totalItems;
        @SerializedName("totalRevenue")
        @Expose
        private String totalRevenue;
        @SerializedName("totalSubscription")
        @Expose
        private String totalSubscription;
        @SerializedName("specialitiesss")
        @Expose
        private String specialitiesss;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(String verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getOptionalPhone() {
            return optionalPhone;
        }

        public void setOptionalPhone(String optionalPhone) {
            this.optionalPhone = optionalPhone;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRefName1() {
            return refName1;
        }

        public void setRefName1(String refName1) {
            this.refName1 = refName1;
        }

        public String getRefContact1() {
            return refContact1;
        }

        public void setRefContact1(String refContact1) {
            this.refContact1 = refContact1;
        }

        public String getRefItem1() {
            return refItem1;
        }

        public void setRefItem1(String refItem1) {
            this.refItem1 = refItem1;
        }

        public String getRefName2() {
            return refName2;
        }

        public void setRefName2(String refName2) {
            this.refName2 = refName2;
        }

        public String getRefContact2() {
            return refContact2;
        }

        public void setRefContact2(String refContact2) {
            this.refContact2 = refContact2;
        }

        public String getRefItem2() {
            return refItem2;
        }

        public void setRefItem2(String refItem2) {
            this.refItem2 = refItem2;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
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

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public String getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(String totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public String getTotalSubscription() {
            return totalSubscription;
        }

        public void setTotalSubscription(String totalSubscription) {
            this.totalSubscription = totalSubscription;
        }

        public String getSpecialitiesss() {
            return specialitiesss;
        }

        public void setSpecialitiesss(String specialitiesss) {
            this.specialitiesss = specialitiesss;
        }

    }

}
