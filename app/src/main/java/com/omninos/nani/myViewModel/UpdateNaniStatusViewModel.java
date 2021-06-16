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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 13 , December , 2019
 */
public class UpdateNaniStatusViewModel extends ViewModel {

    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<LoginRegisterModelClass> naniCheck;

    private MutableLiveData<LoginRegisterModelClass> UpdateInfo;


    public LiveData<LoginRegisterModelClass> checkNani(final Activity activity, String userId) {
        naniCheck = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.checkNani(userId).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        naniCheck.setValue(response.body());
                    } else {
                        Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

        return naniCheck;
    }


    public LiveData<LoginRegisterModelClass> UpdateBankDetail(Activity activity, String address, String bankName, String idNumber, String accountNumber, String accountHolderName, String branchName, String branchCode, String optionalPhone, String specialities, String refName1, String refContact1, String refItem1, String refName2, String refContact2, String refItem2, String latitude, String longitude, String userId) {
        UpdateInfo = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.updateInfo(address, bankName, idNumber, accountNumber, accountHolderName, branchName, branchCode, optionalPhone, specialities, refName1, refContact1, refItem1, refName2, refContact2, refItem2, latitude, longitude, userId).enqueue(new Callback<LoginRegisterModelClass>() {
                @Override
                public void onResponse(Call<LoginRegisterModelClass> call, Response<LoginRegisterModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        UpdateInfo.setValue(response.body());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

        return UpdateInfo;
    }
}
