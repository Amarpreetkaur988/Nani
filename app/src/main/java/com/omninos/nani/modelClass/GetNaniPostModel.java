package com.omninos.nani.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class GetNaniPostModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }


    public class PostImage implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("naniId")
        @Expose
        private String naniId;
        @SerializedName("postId")
        @Expose
        private String postId;
        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNaniId() {
            return naniId;
        }

        public void setNaniId(String naniId) {
            this.naniId = naniId;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class Detail implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("specialitiesId")
        @Expose
        private String specialitiesId;
        @SerializedName("naniId")
        @Expose
        private String naniId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("unitOfMesurement")
        @Expose
        private String unitOfMesurement;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("collectionDay")
        @Expose
        private String collectionDay;
        @SerializedName("ingredints")
        @Expose
        private String ingredints;
        @SerializedName("meatProduct")
        @Expose
        private String meatProduct;
        @SerializedName("preparationInstructions")
        @Expose
        private String preparationInstructions;
        @SerializedName("allergies")
        @Expose
        private String allergies;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("naniNane")
        @Expose
        private String naniNane;
        @SerializedName("naniAddress")
        @Expose
        private String naniAddress;
        @SerializedName("naniImage")
        @Expose
        private String naniImage;
        @SerializedName("specialities")
        @Expose
        private String specialities;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("subscriptionStatus")
        @Expose
        private String subscriptionStatus;
        @SerializedName("allergyTitle")
        @Expose
        private String allergyTitle;
        @SerializedName("postImage")
        @Expose
        private List<PostImage> postImage = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpecialitiesId() {
            return specialitiesId;
        }

        public void setSpecialitiesId(String specialitiesId) {
            this.specialitiesId = specialitiesId;
        }

        public String getNaniId() {
            return naniId;
        }

        public void setNaniId(String naniId) {
            this.naniId = naniId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnitOfMesurement() {
            return unitOfMesurement;
        }

        public void setUnitOfMesurement(String unitOfMesurement) {
            this.unitOfMesurement = unitOfMesurement;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCollectionDay() {
            return collectionDay;
        }

        public void setCollectionDay(String collectionDay) {
            this.collectionDay = collectionDay;
        }

        public String getIngredints() {
            return ingredints;
        }

        public void setIngredints(String ingredints) {
            this.ingredints = ingredints;
        }

        public String getMeatProduct() {
            return meatProduct;
        }

        public void setMeatProduct(String meatProduct) {
            this.meatProduct = meatProduct;
        }

        public String getPreparationInstructions() {
            return preparationInstructions;
        }

        public void setPreparationInstructions(String preparationInstructions) {
            this.preparationInstructions = preparationInstructions;
        }

        public String getAllergies() {
            return allergies;
        }

        public void setAllergies(String allergies) {
            this.allergies = allergies;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNaniNane() {
            return naniNane;
        }

        public void setNaniNane(String naniNane) {
            this.naniNane = naniNane;
        }

        public String getNaniAddress() {
            return naniAddress;
        }

        public void setNaniAddress(String naniAddress) {
            this.naniAddress = naniAddress;
        }

        public String getNaniImage() {
            return naniImage;
        }

        public void setNaniImage(String naniImage) {
            this.naniImage = naniImage;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getSubscriptionStatus() {
            return subscriptionStatus;
        }

        public void setSubscriptionStatus(String subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
        }

        public String getAllergyTitle() {
            return allergyTitle;
        }

        public void setAllergyTitle(String allergyTitle) {
            this.allergyTitle = allergyTitle;
        }

        public List<PostImage> getPostImage() {
            return postImage;
        }

        public void setPostImage(List<PostImage> postImage) {
            this.postImage = postImage;
        }

    }

}
