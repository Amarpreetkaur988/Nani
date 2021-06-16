package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 24 , November , 2019
 */
public class SocialLoginViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<LoginRegisterModelClass> checkSocial;

    private MutableLiveData<LoginRegisterModelClass> socialRegister;


    public LiveData<LoginRegisterModelClass> socialCheck(Activity activity, String socialId) {
        checkSocial = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {

            api.userCheckSocialId(socialId).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    if (response.isSuccessful()) {
                        checkSocial.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Error");
                        checkSocial.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage("Server Error");
                    checkSocial.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Error");
            checkSocial.setValue(loginRegisterModelClass);
        }

        return checkSocial;
    }

    public LiveData<LoginRegisterModelClass> RegisterSocial(Activity activity, String name, String email, String phone, String social_id, String address, String bankName, String accountNumber, String accountHolderName, String branchName, String branchCode, String optionalPhone, String specialities, String refName1, String refContact1, String refItem1, String refName2, String refContact2, String refItem2, String device_type, String reg_id, String login_type,String idNumber) {
        socialRegister = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.sociallogin(name, email, phone, social_id, address, bankName, accountNumber, accountHolderName, branchName, branchCode, optionalPhone, specialities, refName1, refContact1, refItem1, refName2, refContact2, refItem2, device_type, reg_id, login_type,idNumber).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        socialRegister.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Error");
                        socialRegister.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage("Server Error");
                    socialRegister.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Error");
            socialRegister.setValue(loginRegisterModelClass);
        }
        return socialRegister;
    }
}
