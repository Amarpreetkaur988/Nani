package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 20 , November , 2019
 */
public class LoginRegisterViewModel extends ViewModel {

    ApiClient client = new ApiClient();
    Api api = client.build(Api.class);

    private MutableLiveData<LoginRegisterModelClass> userLogin;

    private MutableLiveData<LoginRegisterModelClass> userRegister;

    private MutableLiveData<Map> CheckNumberAndEmail;

    public LiveData<Map> NumberAndEmail(Activity activity, String phoneNumber, String email) {
        CheckNumberAndEmail = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.CheckNoAndEmail(phoneNumber, email).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        CheckNumberAndEmail.setValue(response.body());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

        return CheckNumberAndEmail;
    }


    public LiveData<LoginRegisterModelClass> Login(Activity activity, String email, String password, String device_type, String reg_id, String userType) {
        userLogin = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.userLogIn(email, password, device_type, reg_id, userType).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        userLogin.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Issue");
                        userLogin.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage(t.toString());
                    userLogin.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Issue");
            userLogin.setValue(loginRegisterModelClass);
        }

        return userLogin;
    }


    public LiveData<LoginRegisterModelClass> Register(Activity activity, String name, String email, String phone, String password, String bankName, String accountNumber, String accountHolderName, String branchName, String branchCode, String optionalPhone, String specialities, String refName1, String refContact1, String refItem1, String refName2, String refContact2, String refItem2, String longitude, String latitude, String address, String device_type, String reg_id, String userType,String idNumber) {
        userRegister = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.UserRegister(name, email, phone, password, bankName, accountNumber, accountHolderName, branchName, branchCode, optionalPhone, specialities, refName1, refContact1, refItem1, refName2, refContact2, refItem2, longitude, latitude, address, device_type, reg_id, userType,idNumber).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        userRegister.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Issue");
                        userRegister.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage("Server Issue");
                    userRegister.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Issue");
            userRegister.setValue(loginRegisterModelClass);

        }

        return userRegister;
    }


    private MutableLiveData<LoginRegisterModelClass> matchVerificatonToken;

    public LiveData<LoginRegisterModelClass> MatchVerification(Activity activity, String id, String token) {

        matchVerificatonToken = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.matchVerificationToken(id, token).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        matchVerificatonToken.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Issue");
                        matchVerificatonToken.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage("Server Issue");
                    matchVerificatonToken.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Issue");
            matchVerificatonToken.setValue(loginRegisterModelClass);
        }

        return matchVerificatonToken;
    }


    private MutableLiveData<LoginRegisterModelClass> resendVerificatonToken;

    public LiveData<LoginRegisterModelClass> ResendVerificationToken(Activity activity, String id) {

        resendVerificatonToken = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.resendVerificationToken(id).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        resendVerificatonToken.setValue(response.body());
                    } else {
                        LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                        loginRegisterModelClass.setSuccess("0");
                        loginRegisterModelClass.setMessage("Server Issue");
                        resendVerificatonToken.setValue(loginRegisterModelClass);
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
                    loginRegisterModelClass.setSuccess("0");
                    loginRegisterModelClass.setMessage("Server Issue");
                    resendVerificatonToken.setValue(loginRegisterModelClass);
                }
            });
        } else {
            LoginRegisterModelClass loginRegisterModelClass = new LoginRegisterModelClass();
            loginRegisterModelClass.setSuccess("0");
            loginRegisterModelClass.setMessage("Network Error");
            resendVerificatonToken.setValue(loginRegisterModelClass);
        }

        return resendVerificatonToken;
    }

}
