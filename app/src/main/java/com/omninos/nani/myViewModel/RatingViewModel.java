package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.CheckRatingModel;
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
public class RatingViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<CheckRatingModel> checkRatingModelMutableLiveData;

    private MutableLiveData<Map> giveRat;

    public LiveData<CheckRatingModel> checkRatingModelLiveData(Activity activity, String userId) {
        checkRatingModelMutableLiveData = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            api.checkrating(userId).enqueue(new Callback<CheckRatingModel>() {
                @Override
                public void onResponse(Call<CheckRatingModel> call, Response<CheckRatingModel> response) {
                    if (response.isSuccessful()) {
                        checkRatingModelMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CheckRatingModel> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
        return checkRatingModelMutableLiveData;
    }

    public LiveData<Map> RatingData(Activity activity, String bookingId, String rating) {

        giveRat = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            api.giverating(bookingId, rating).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.isSuccessful()) {
                        giveRat.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }

        return giveRat;
    }
}
