package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.GetNaniProfile;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class ProfileViewModel extends ViewModel {

    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<GetNaniProfile> mySpecialityModelMutableLiveData;

    private MutableLiveData<LoginRegisterModelClass> updateProfile;

    public LiveData<GetNaniProfile> mySpecialityModelLiveData(Activity activity, String userId) {
        mySpecialityModelMutableLiveData = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getNaniProfile(userId).enqueue(new Callback<GetNaniProfile>() {
                @Override
                public void onResponse(Call<GetNaniProfile> call, Response<GetNaniProfile> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        mySpecialityModelMutableLiveData.setValue(response.body());
                    } else {
                        GetNaniProfile mySpecialityModel = new GetNaniProfile();
                        mySpecialityModel.setSuccess("0");
                        mySpecialityModel.setMessage("Server Issue");
                        mySpecialityModelMutableLiveData.setValue(mySpecialityModel);
                    }
                }

                @Override
                public void onFailure(Call<GetNaniProfile> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    GetNaniProfile mySpecialityModel = new GetNaniProfile();
                    mySpecialityModel.setSuccess("0");
                    mySpecialityModel.setMessage(t.toString());
                    mySpecialityModelMutableLiveData.setValue(mySpecialityModel);
                }
            });
        } else {

            GetNaniProfile mySpecialityModel = new GetNaniProfile();
            mySpecialityModel.setSuccess("0");
            mySpecialityModel.setMessage("Network Issue");
            mySpecialityModelMutableLiveData.setValue(mySpecialityModel);
        }
        return mySpecialityModelMutableLiveData;
    }


    public LiveData<LoginRegisterModelClass> update(Activity activity, RequestBody name, RequestBody specialities, RequestBody address, RequestBody latitude, RequestBody longitude, MultipartBody.Part image, RequestBody userId) {
        updateProfile = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.editProfile(name, specialities, address, latitude, longitude, image, userId).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        updateProfile.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setMessage("Server Error");
                        loginRegisterModelClass.setSuccess("0");
                        updateProfile.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setMessage("Server Error");
                    loginRegisterModelClass.setSuccess("0");
                    updateProfile.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setMessage("Network Error");
            loginRegisterModelClass.setSuccess("0");
            updateProfile.setValue(loginRegisterModelClass);
        }
        return updateProfile;
    }
}
