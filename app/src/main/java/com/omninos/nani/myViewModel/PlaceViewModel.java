package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.GetLatLngModel;
import com.omninos.nani.modelClass.PlaceSearchModel;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceViewModel extends ViewModel {

    ApiClient apiClient = new ApiClient();
    Api api=apiClient.PlacesBuild(Api.class);

    private MutableLiveData<PlaceSearchModel> placeSearchModelMutableLiveData;

    private MutableLiveData<GetLatLngModel> getLatLngModelMutableLiveData;

    public LiveData<PlaceSearchModel> placeSearchModelLiveData(final Activity activity, Map<String, String> place) {
        if (CommonUtils.isNetworkConnected(activity)) {
            placeSearchModelMutableLiveData = new MutableLiveData<>();


            api.placeSearch(place).enqueue(new Callback<PlaceSearchModel>() {
                @Override
                public void onResponse(Call<PlaceSearchModel> call, Response<PlaceSearchModel> response) {
                    if (response.body() != null) {
                        placeSearchModelMutableLiveData.setValue(response.body());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<PlaceSearchModel> call, Throwable t) {
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
        return placeSearchModelMutableLiveData;
    }


    public LiveData<GetLatLngModel> getLatLngModelLiveData(final Activity activity, Map<String, String> address) {
        if (CommonUtils.isNetworkConnected(activity)) {

            getLatLngModelMutableLiveData = new MutableLiveData<>();

            api.getLocationFromAddress(address).enqueue(new Callback<GetLatLngModel>() {
                @Override
                public void onResponse(Call<GetLatLngModel> call, Response<GetLatLngModel> response) {
                    if (response.body() != null) {
                        getLatLngModelMutableLiveData.setValue(response.body());
                    } else {
                        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetLatLngModel> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

        return getLatLngModelMutableLiveData;
    }
}
