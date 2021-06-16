package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 26 , November , 2019
 */
public class PasswordViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);
    private MutableLiveData<Map> changePass;

    public LiveData<Map> PasswordChange(Activity activity, String old_password, String new_password, String userId) {
        changePass = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.changePassword(old_password, new_password, userId).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        changePass.setValue(response.body());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });
        } else {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show();
        }
        return changePass;
    }
}
