package com.omninos.nani.myViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class PostViewModel extends ViewModel {

    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<GetNaniPostModel> getNaniPostModelMutableLiveData;

    public LiveData<GetNaniPostModel> getNaniPostModelLiveData(Activity activity, String userId,String buyerId) {
        getNaniPostModelMutableLiveData = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
//            CommonUtils.showProgress(activity);
            api.myPost(userId,buyerId).enqueue(new Callback<GetNaniPostModel>() {
                @Override
                public void onResponse(Call<GetNaniPostModel> call, Response<GetNaniPostModel> response) {
//                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        getNaniPostModelMutableLiveData.setValue(response.body());
                    } else {
                        GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
                        getNaniPostModel.setSuccess("0");
                        getNaniPostModel.setMessage("Server Error");
                        getNaniPostModelMutableLiveData.setValue(getNaniPostModel);
                    }
                }

                @Override
                public void onFailure(Call<GetNaniPostModel> call, Throwable t) {
//                    CommonUtils.dismissProgress();
                    GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
                    getNaniPostModel.setSuccess("0");
                    getNaniPostModel.setMessage(t.toString());
                    getNaniPostModelMutableLiveData.setValue(getNaniPostModel);
                }
            });
        } else {
            GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
            getNaniPostModel.setSuccess("0");
            getNaniPostModel.setMessage("Internet Error");
            getNaniPostModelMutableLiveData.setValue(getNaniPostModel);
        }

        return getNaniPostModelMutableLiveData;
    }

    private MutableLiveData<GetNaniPostModel> getSubList;

    public LiveData<GetNaniPostModel> getSubscribedNaniPosts(Activity activity, String userId) {
        getSubList = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
//            CommonUtils.showProgress(activity);
            api.getSubscribedNaniPosts(userId).enqueue(new Callback<GetNaniPostModel>() {
                @Override
                public void onResponse(Call<GetNaniPostModel> call, Response<GetNaniPostModel> response) {
//                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        getSubList.setValue(response.body());
                    } else {
                        GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
                        getNaniPostModel.setSuccess("0");
                        getNaniPostModel.setMessage("Server Error");
                        getSubList.setValue(getNaniPostModel);
                    }
                }

                @Override
                public void onFailure(Call<GetNaniPostModel> call, Throwable t) {
//                    CommonUtils.dismissProgress();
                    GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
                    getNaniPostModel.setSuccess("0");
                    getNaniPostModel.setMessage(t.toString());
                    getSubList.setValue(getNaniPostModel);
                }
            });
        } else {
            GetNaniPostModel getNaniPostModel = new GetNaniPostModel();
            getNaniPostModel.setSuccess("0");
            getNaniPostModel.setMessage("Internet Error");
            getSubList.setValue(getNaniPostModel);
        }

        return getSubList;
    }
}
