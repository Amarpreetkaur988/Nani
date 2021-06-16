package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.NaniPaymentModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class NaniPaymentViewModel extends ViewModel {
    Api api=new ApiClient().build(Api.class);
    private MutableLiveData<NaniPaymentModelClass> naniPaymentModelClassMutableLiveData;

    public LiveData<NaniPaymentModelClass> naniPaymentModelClassLiveData(Activity activity,String naniId){
        naniPaymentModelClassMutableLiveData=new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){
            CommonUtils.showProgress(activity);
            api.getNaniPayment(naniId).enqueue(new Callback<NaniPaymentModelClass>() {
                @Override
                public void onResponse(Call<NaniPaymentModelClass> call, Response<NaniPaymentModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()){
                        naniPaymentModelClassMutableLiveData.setValue(response.body());
                    }else {
                        NaniPaymentModelClass modelClass=new NaniPaymentModelClass();
                        modelClass.setSuccess("0");
                        modelClass.setMessage("Server Error");
                        naniPaymentModelClassMutableLiveData.setValue(modelClass);
                    }
                }

                @Override
                public void onFailure(Call<NaniPaymentModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    NaniPaymentModelClass modelClass=new NaniPaymentModelClass();
                    modelClass.setSuccess("0");
                    modelClass.setMessage("Server Error");
                    naniPaymentModelClassMutableLiveData.setValue(modelClass);
                }
            });
        }else {

            NaniPaymentModelClass modelClass=new NaniPaymentModelClass();
            modelClass.setSuccess("0");
            modelClass.setMessage("Network  Error");
            naniPaymentModelClassMutableLiveData.setValue(modelClass);
        }
        return naniPaymentModelClassMutableLiveData;
    }
}
