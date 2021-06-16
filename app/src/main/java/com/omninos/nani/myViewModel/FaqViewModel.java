package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.FAQModel;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class FaqViewModel extends ViewModel {

    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<FAQModel> faqModelMutableLiveData;

    public LiveData<FAQModel> faqModelLiveData(Activity activity) {
        faqModelMutableLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getFAQ().enqueue(new Callback<FAQModel>() {
                @Override
                public void onResponse(Call<FAQModel> call, Response<FAQModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        faqModelMutableLiveData.setValue(response.body());
                    } else {
                        FAQModel faqModel = new FAQModel();
                        faqModel.setSuccess("0");
                        faqModel.setMessage("Server Error");
                        faqModelMutableLiveData.setValue(faqModel);
                    }
                }

                @Override
                public void onFailure(Call<FAQModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    FAQModel faqModel = new FAQModel();
                    faqModel.setSuccess("0");
                    faqModel.setMessage("Server Error");
                    faqModelMutableLiveData.setValue(faqModel);
                }
            });
        } else {

            FAQModel faqModel = new FAQModel();
            faqModel.setSuccess("0");
            faqModel.setMessage("Network Error");
            faqModelMutableLiveData.setValue(faqModel);
        }
        return faqModelMutableLiveData;
    }
}
