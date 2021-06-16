package com.omninos.nani.retrofit;

import com.omninos.nani.modelClass.AllergiesModel;
import com.omninos.nani.modelClass.BuyerOrderModel;
import com.omninos.nani.modelClass.CheckRatingModel;
import com.omninos.nani.modelClass.DiscoverModelClass;
import com.omninos.nani.modelClass.FAQModel;
import com.omninos.nani.modelClass.GetLatLngModel;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.modelClass.GetNaniProfile;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.modelClass.MySpecialityModel;
import com.omninos.nani.modelClass.NaniOrderModelClass;
import com.omninos.nani.modelClass.NaniPaymentModelClass;
import com.omninos.nani.modelClass.PlaceSearchModel;
import com.omninos.nani.modelClass.SpecialitiesListClass;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface Api {

    @FormUrlEncoded
    @POST("checkPhoneNumberAndEmail")
    Call<Map> CheckNoAndEmail(@Field("phoneNumber") String phoneNumber,
                              @Field("email") String email);

    //
    @GET("specialitiesList")
    Call<SpecialitiesListClass> specialitiesList();

    //
    @FormUrlEncoded
    @POST("userRegister")
    Call<LoginRegisterModelClass> UserRegister(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("phone") String phone,
                                               @Field("password") String password,
                                               @Field("bankName") String bankName,
                                               @Field("accountNumber") String accountNumber,
                                               @Field("accountHolderName") String accountHolderName,
                                               @Field("branchName") String branchName,
                                               @Field("branchCode") String branchCode,
                                               @Field("optionalPhone") String optionalPhone,
                                               @Field("specialities") String specialities,
                                               @Field("refName1") String refName1,
                                               @Field("refContact1") String refContact1,
                                               @Field("refItem1") String refItem1,
                                               @Field("refName2") String refName2,
                                               @Field("refContact2") String refContact2,
                                               @Field("refItem2") String refItem2,
                                               @Field("longitude") String longitude,
                                               @Field("latitude") String latitude,
                                               @Field("address") String address,
                                               @Field("device_type") String device_type,
                                               @Field("reg_id") String reg_id,
                                               @Field("userType") String userType,
                                               @Field("idNumber") String idNumber);

    //
    @FormUrlEncoded
    @POST("matchVerificationToken")
    Call<LoginRegisterModelClass> matchVerificationToken(@Field("id") String id,
                                                         @Field("token") String token);

    //
    @FormUrlEncoded
    @POST("resendVerificationToken")
    Call<LoginRegisterModelClass> resendVerificationToken(@Field("id") String id);

    //
    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginRegisterModelClass> userLogIn(@Field("email") String email,
                                            @Field("password") String password,
                                            @Field("device_type") String device_type,
                                            @Field("reg_id") String reg_id,
                                            @Field("userType") String userType);

    @GET("maps/api/place/autocomplete/json?")
    Call<PlaceSearchModel> placeSearch(@QueryMap Map<String, String> places);

    @GET("maps/api/geocode/json?")
    Call<GetLatLngModel> getLocationFromAddress(@QueryMap Map<String, String> address);


    @FormUrlEncoded
    @POST("myPosts")
    Call<GetNaniPostModel> myPost(@Field("userId") String userId,
                                  @Field("buyerId") String buyerId);

    @FormUrlEncoded
    @POST("getSubscribedNaniPosts")
    Call<GetNaniPostModel> getSubscribedNaniPosts(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("naniGetProfile")
    Call<GetNaniProfile> getNaniProfile(@Field("naniId") String naniId);


    @Multipart
    @POST("naniPost")
    Call<Map> AddPost(@Part("naniId") RequestBody naniId,
                      @Part("name") RequestBody name,
                      @Part("description") RequestBody description,
                      @Part("quantity") RequestBody quantity,
                      @Part("unitOfMesurement") RequestBody unitOfMesurement,
                      @Part("price") RequestBody price,
                      @Part("ingredints") RequestBody ingredints,
                      @Part("meatProduct") RequestBody meatProduct,
                      @Part("preparationInstructions") RequestBody preparationInstructions,
                      @Part("allergies") RequestBody allergies,
                      @Part("collectionDay") RequestBody collectionDay,
                      @Part("specialitiesId") RequestBody specialitiesId,
                      @Part MultipartBody.Part images[]);

    @FormUrlEncoded
    @POST("mySpecialities")
    Call<MySpecialityModel> getMySpeciality(@Field("userId") String userId);


    @GET("allergiesList")
    Call<AllergiesModel> getAllergiesList();


    @FormUrlEncoded
    @POST("getNaniBookings")
    Call<NaniOrderModelClass> getNaniBookings(@Field("naniId") String naniId);

    @Multipart
    @POST("userUpdateProfile")
    Call<LoginRegisterModelClass> editProfile(@Part("name") RequestBody name,
                                              @Part("specialities") RequestBody specialities,
                                              @Part("address") RequestBody address,
                                              @Part("latitude") RequestBody latitude,
                                              @Part("longitude") RequestBody longitude,
                                              @Part MultipartBody.Part image,
                                              @Part("userId") RequestBody userId);

    @FormUrlEncoded
    @POST("updateBookingStatus")
    Call<Map> updateBookingStatus(@Field("bookingId") String bookingId,
                                  @Field("status") String status);

    @GET("getFaq")
    Call<FAQModel> getFAQ();

    @FormUrlEncoded
    @POST("getNaniPayments")
    Call<NaniPaymentModelClass> getNaniPayment(@Field("naniId") String naniId);

    @FormUrlEncoded
    @POST("getDiscover")
    Call<DiscoverModelClass> getDiscover(@Field("search") String search,
                                         @Field("type") String type,
                                         @Field("userId") String userId,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("subscribeNani")
    Call<Map> subscribeOrunScribe(@Field("naniId") String naniId,
                                  @Field("subscribeStatus") String subscribeStatus,
                                  @Field("userId") String userId);


    @FormUrlEncoded
    @POST("userCheckSocialId")
    Call<LoginRegisterModelClass> userCheckSocialId(@Field("social_id") String socialid);


    @FormUrlEncoded
    @POST("UserSocialLogin")
    Call<LoginRegisterModelClass> sociallogin(@Field("name") String name,
                                              @Field("email") String email,
                                              @Field("phone") String phone,
                                              @Field("social_id") String socialid,
                                              @Field("address") String address,
                                              @Field("bankName") String bankName,
                                              @Field("accountNumber") String accountNumber,
                                              @Field("accountHolderName") String accountHolderName,
                                              @Field("branchName") String branchName,
                                              @Field("branchCode") String branchCode,
                                              @Field("optionalPhone") String optionalPhone,
                                              @Field("specialities") String specialities,
                                              @Field("refName1") String refName1,
                                              @Field("refContact1") String refContact1,
                                              @Field("refItem1") String refItem1,
                                              @Field("refName2") String refName2,
                                              @Field("refContact2") String refContact2,
                                              @Field("refItem2") String refItem2,
                                              @Field("device_type") String device_type,
                                              @Field("reg_id") String reg_id,
                                              @Field("login_type") String login_type,
                                              @Field("idNumber") String idNumber);

    @FormUrlEncoded
    @POST("getUserBookings")
    Call<BuyerOrderModel> getOrders(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userOrderBooking")
    Call<Map> bookOrder(@Field("postId") String postId,
                        @Field("quantity") String quantity,
                        @Field("location") String location,
                        @Field("areaCode") String areaCode,
                        @Field("houseNo") String houseNo,
                        @Field("landmark") String landmark,
                        @Field("latitude") String latitude,
                        @Field("longitude") String longitude,
                        @Field("locationType") String locationType,
                        @Field("price") String price,
                        @Field("userId") String userId);
//

    @FormUrlEncoded
    @POST("changePassword")
    Call<Map> changePassword(@Field("old_password") String old_password,
                             @Field("new_password") String new_password,
                             @Field("userId") String userId);

    @FormUrlEncoded
    @POST("checkUserRating")
    Call<CheckRatingModel> checkrating(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userRating")
    Call<Map> giverating(@Field("bookingId") String bookingId,
                         @Field("rating") String rating);

    //http://13.234.148.86/naniApplication/index.php/api/user/amountCalculate
    @FormUrlEncoded
    @POST("amountCalculate")
    Call<Map> getDeliverAmt(@Field("postId") String postId,
                            @Field("latitude") String latitude,
                            @Field("longitude") String longitude);

    //http://13.234.148.86/naniApplication/index.php/api/user/checkNaniBankDetails
    @FormUrlEncoded
    @POST("checkNaniBankDetails")
    Call<LoginRegisterModelClass> checkNani(@Field("userId") String userId);

    //http://13.234.148.86/naniApplication/index.php/api/user/updateBuyerBankDetails
    @FormUrlEncoded
    @POST("updateBuyerBankDetails")
    Call<LoginRegisterModelClass> updateInfo(@Field("address") String address,
                                             @Field("bankName") String bankName,
                                             @Field("idNumber") String idNumber,
                                             @Field("accountNumber") String accountNumber,
                                             @Field("accountHolderName") String accountHolderName,
                                             @Field("branchName") String branchName,
                                             @Field("branchCode") String branchCode,
                                             @Field("optionalPhone") String optionalPhone,
                                             @Field("specialities") String specialities,
                                             @Field("refName1") String refName1,
                                             @Field("refContact1") String refContact1,
                                             @Field("refItem1") String refItem1,
                                             @Field("refName2") String refName2,
                                             @Field("refContact2") String refContact2,
                                             @Field("refItem2") String refItem2,
                                             @Field("latitude") String latitude,
                                             @Field("longitude") String longitude,
                                             @Field("userId") String userId);


    @FormUrlEncoded
    @POST("forgotPassword")
    Call<Map> forgotPassword(@Field("email") String email);
}


