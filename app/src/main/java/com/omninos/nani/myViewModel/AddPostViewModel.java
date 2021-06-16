package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.AllergiesModel;
import com.omninos.nani.modelClass.MySpecialityModel;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<Map> addPost;
    private MutableLiveData<MySpecialityModel> mySpecialityModelMutableLiveData;

    private MutableLiveData<AllergiesModel> allergiesModelMutableLiveData;

    public LiveData<Map> post(Activity activity, RequestBody naniId, RequestBody name, RequestBody description, RequestBody quantity, RequestBody unitOfMesurement, RequestBody price, RequestBody ingredints, RequestBody meatProduct, RequestBody preparationInstructions, RequestBody allergies, RequestBody collectionDay, RequestBody specialitiesId, MultipartBody.Part images[]) {

        CommonUtils.showProgress(activity);
        addPost = new MutableLiveData<>();


        api.AddPost(naniId, name, description, quantity, unitOfMesurement, price, ingredints, meatProduct, preparationInstructions, allergies, collectionDay, specialitiesId, images).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    addPost.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                CommonUtils.dismissProgress();

            }
        });

        return addPost;
    }

    public LiveData<MySpecialityModel> modelLiveData(Activity activity, String userId) {
        mySpecialityModelMutableLiveData = new MutableLiveData<>();

        api.getMySpeciality(userId).enqueue(new Callback<MySpecialityModel>() {
            @Override
            public void onResponse(Call<MySpecialityModel> call, Response<MySpecialityModel> response) {
                if (response.body() != null) {
                    mySpecialityModelMutableLiveData.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<MySpecialityModel> call, Throwable t) {

            }
        });


        return mySpecialityModelMutableLiveData;
    }

//    public LiveData<GetSearchListModel> getSearchListModelLiveData(Activity activity) {
//        list = new MutableLiveData<>();
//
////        CommonUtils.showProgress(activity);
//        Api api = ApiClient.getApiClient().create(Api.class);
//        api.getList().enqueue(new Callback<GetSearchListModel>() {
//            @Override
//            public void onResponse(Call<GetSearchListModel> call, Response<GetSearchListModel> response) {
////                CommonUtils.dismissProgress();
//                if (response.body() != null) {
//                    list.setValue(response.body());
//                } else {
//                    GetSearchListModel getSearchListModel = new GetSearchListModel();
//                    getSearchListModel.setSuccess("0");
//                    getSearchListModel.setMessage("Server Error");
//                    list.setValue(getSearchListModel);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetSearchListModel> call, Throwable t) {
////                CommonUtils.dismissProgress();
//                GetSearchListModel getSearchListModel = new GetSearchListModel();
//                getSearchListModel.setSuccess("0");
//                getSearchListModel.setMessage("Server Error");
//                list.setValue(getSearchListModel);
//            }
//        });
//
//        return list;
//    }

    public LiveData<AllergiesModel> allergiesModelLiveData(Activity activity) {
        allergiesModelMutableLiveData = new MutableLiveData<>();
        api.getAllergiesList().enqueue(new Callback<AllergiesModel>() {
            @Override
            public void onResponse(Call<AllergiesModel> call, Response<AllergiesModel> response) {
                if (response.body() != null) {
                    allergiesModelMutableLiveData.setValue(response.body());
                } else {
                    AllergiesModel model = new AllergiesModel();
                    model.setSuccess("0");
                    model.setMessage("Server Error");
                    allergiesModelMutableLiveData.setValue(model);
                }
            }

            @Override
            public void onFailure(Call<AllergiesModel> call, Throwable t) {

                AllergiesModel model = new AllergiesModel();
                model.setSuccess("0");
                model.setMessage("Server Error");
                allergiesModelMutableLiveData.setValue(model);
            }
        });

        return allergiesModelMutableLiveData;
    }
}
