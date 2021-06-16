package com.omninos.nani.myViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.SpecialitiesListClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 20 , November , 2019
 */
public class SpecialitiesViewModel extends ViewModel {

    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<SpecialitiesListClass> SpecialitiesList;

    public LiveData<SpecialitiesListClass> specialitiesList() {

        SpecialitiesList = new MutableLiveData<>();
        api.specialitiesList().enqueue(new Callback<SpecialitiesListClass>() {
            @Override
            public void onResponse(Call<SpecialitiesListClass> call, Response<SpecialitiesListClass> response) {
                if (response.body() != null) {
                    SpecialitiesList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SpecialitiesListClass> call, Throwable t) {

            }
        });

        return SpecialitiesList;
    }
}
