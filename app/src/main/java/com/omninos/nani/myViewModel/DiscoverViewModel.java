package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.DiscoverModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class DiscoverViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<DiscoverModelClass> discoverModelClassMutableLiveData;

    public LiveData<DiscoverModelClass> discoverModelClassLiveData(Activity activity, String search, String type, String userId, String latitude, String longitude) {
        discoverModelClassMutableLiveData = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getDiscover(search, type, userId, latitude, longitude).enqueue(new Callback<DiscoverModelClass>() {
                @Override
                public void onResponse(Call<DiscoverModelClass> call, Response<DiscoverModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        discoverModelClassMutableLiveData.setValue(response.body());
                    } else {
                        DiscoverModelClass discoverModelClass = new DiscoverModelClass();
                        discoverModelClass.setSuccess("0");
                        discoverModelClass.setMessage("Server Error");
                        discoverModelClassMutableLiveData.setValue(discoverModelClass);
                    }
                }

                @Override
                public void onFailure(Call<DiscoverModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    DiscoverModelClass discoverModelClass = new DiscoverModelClass();
                    discoverModelClass.setSuccess("0");
                    discoverModelClass.setMessage("Server Error");
                    discoverModelClassMutableLiveData.setValue(discoverModelClass);
                }
            });
        } else {
            DiscoverModelClass discoverModelClass = new DiscoverModelClass();
            discoverModelClass.setSuccess("0");
            discoverModelClass.setMessage("Network Error");
            discoverModelClassMutableLiveData.setValue(discoverModelClass);
        }
        return discoverModelClassMutableLiveData;
    }
}
