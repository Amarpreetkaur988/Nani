package com.omninos.nani.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Manjinder Singh on 20 , November , 2019
 */
public  class LoginRegisterModelClass {

    @Expose
    @SerializedName("details")
    private Details details;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("success")
    private String success;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public  class Details {
        @Expose
        @SerializedName("wallet")
        private String wallet;
        @Expose
        @SerializedName("updated")
        private String updated;
        @Expose
        @SerializedName("created")
        private String created;
        @Expose
        @SerializedName("login_type")
        private String login_type;
        @Expose
        @SerializedName("device_type")
        private String device_type;
        @Expose
        @SerializedName("reg_id")
        private String reg_id;
        @Expose
        @SerializedName("longitude")
        private String longitude;
        @Expose
        @SerializedName("latitude")
        private String latitude;
        @Expose
        @SerializedName("otp")
        private String otp;
        @Expose
        @SerializedName("refItem2")
        private String refItem2;
        @Expose
        @SerializedName("refContact2")
        private String refContact2;
        @Expose
        @SerializedName("refName2")
        private String refName2;
        @Expose
        @SerializedName("refItem1")
        private String refItem1;
        @Expose
        @SerializedName("refContact1")
        private String refContact1;
        @Expose
        @SerializedName("refName1")
        private String refName1;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("specialities")
        private String specialities;
        @Expose
        @SerializedName("optionalPhone")
        private String optionalPhone;
        @Expose
        @SerializedName("branchCode")
        private String branchCode;
        @Expose
        @SerializedName("branchName")
        private String branchName;
        @Expose
        @SerializedName("accountHolderName")
        private String accountHolderName;
        @Expose
        @SerializedName("accountNumber")
        private String accountNumber;
        @Expose
        @SerializedName("bankName")
        private String bankName;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("phone")
        private String phone;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("activeStatus")
        private String activeStatus;
        @Expose
        @SerializedName("social_id")
        private String social_id;
        @Expose
        @SerializedName("verifyStatus")
        private String verifyStatus;
        @Expose
        @SerializedName("userType")
        private String userType;
        @Expose
        @SerializedName("id")
        private String id;

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLogin_type() {
            return login_type;
        }

        public void setLogin_type(String login_type) {
            this.login_type = login_type;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getReg_id() {
            return reg_id;
        }

        public void setReg_id(String reg_id) {
            this.reg_id = reg_id;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getRefItem2() {
            return refItem2;
        }

        public void setRefItem2(String refItem2) {
            this.refItem2 = refItem2;
        }

        public String getRefContact2() {
            return refContact2;
        }

        public void setRefContact2(String refContact2) {
            this.refContact2 = refContact2;
        }

        public String getRefName2() {
            return refName2;
        }

        public void setRefName2(String refName2) {
            this.refName2 = refName2;
        }

        public String getRefItem1() {
            return refItem1;
        }

        public void setRefItem1(String refItem1) {
            this.refItem1 = refItem1;
        }

        public String getRefContact1() {
            return refContact1;
        }

        public void setRefContact1(String refContact1) {
            this.refContact1 = refContact1;
        }

        public String getRefName1() {
            return refName1;
        }

        public void setRefName1(String refName1) {
            this.refName1 = refName1;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getOptionalPhone() {
            return optionalPhone;
        }

        public void setOptionalPhone(String optionalPhone) {
            this.optionalPhone = optionalPhone;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getSocial_id() {
            return social_id;
        }

        public void setSocial_id(String social_id) {
            this.social_id = social_id;
        }

        public String getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(String verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
