package com.omninos.nani.myViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.nani.modelClass.BuyerOrderModel;
import com.omninos.nani.modelClass.NaniOrderModelClass;
import com.omninos.nani.retrofit.Api;
import com.omninos.nani.retrofit.ApiClient;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class OrderViewModel extends ViewModel {
    Api api = new ApiClient().build(Api.class);

    private MutableLiveData<NaniOrderModelClass> naniOrderModelClassMutableLiveData;

    private MutableLiveData<Map> updateStatus;

    private MutableLiveData<BuyerOrderModel> getOrder;

    private MutableLiveData<Map> booking;


    private MutableLiveData<Map> getDelivery;


    public LiveData<NaniOrderModelClass> naniOrderModelClassLiveData(Activity activity, String naniId) {
        naniOrderModelClassMutableLiveData = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getNaniBookings(naniId).enqueue(new Callback<NaniOrderModelClass>() {
                @Override
                public void onResponse(Call<NaniOrderModelClass> call, Response<NaniOrderModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        naniOrderModelClassMutableLiveData.setValue(response.body());
                    } else {
                        NaniOrderModelClass orderModelClass = new NaniOrderModelClass();
                        orderModelClass.setSuccess("0");
                        orderModelClass.setMessage("Server Error");
                        naniOrderModelClassMutableLiveData.setValue(orderModelClass);
                    }
                }

                @Override
                public void onFailure(Call<NaniOrderModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    NaniOrderModelClass orderModelClass = new NaniOrderModelClass();
                    orderModelClass.setSuccess("0");
                    orderModelClass.setMessage("Server Error");
                    naniOrderModelClassMutableLiveData.setValue(orderModelClass);
                }
            });
        } else {
            NaniOrderModelClass orderModelClass = new NaniOrderModelClass();
            orderModelClass.setSuccess("0");
            orderModelClass.setMessage("Network Error");
            naniOrderModelClassMutableLiveData.setValue(orderModelClass);

        }
        return naniOrderModelClassMutableLiveData;
    }

    public LiveData<Map> statusUpdate(Activity activity, String bookingId, String status) {
        updateStatus = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.updateBookingStatus(bookingId, status).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        updateStatus.setValue(response.body());
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
        return updateStatus;
    }

    public LiveData<BuyerOrderModel> getOrdersData(Activity activity, String userId) {
        getOrder = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getOrders(userId).enqueue(new Callback<BuyerOrderModel>() {
                @Override
                public void onResponse(Call<BuyerOrderModel> call, Response<BuyerOrderModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        getOrder.setValue(response.body());
                    } else {
                        BuyerOrderModel buyerOrderModel = new BuyerOrderModel();
                        buyerOrderModel.setSuccess("0");
                        buyerOrderModel.setMessage("Server Error");
                        getOrder.setValue(buyerOrderModel);
                    }
                }

                @Override
                public void onFailure(Call<BuyerOrderModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    BuyerOrderModel buyerOrderModel = new BuyerOrderModel();
                    buyerOrderModel.setSuccess("0");
                    buyerOrderModel.setMessage("Server Error");
                    getOrder.setValue(buyerOrderModel);
                }
            });
        } else {

            BuyerOrderModel buyerOrderModel = new BuyerOrderModel();
            buyerOrderModel.setSuccess("0");
            buyerOrderModel.setMessage("Network Error");
            getOrder.setValue(buyerOrderModel);
        }
        return getOrder;
    }


    public LiveData<Map> OrderBook(Activity activity, String postId, String quantity, String location, String areaCode, String houseNo,
                                   String landmark, String latitude, String longitude, String locationType, String price, String userId) {

        booking = new MutableLiveData<>();


        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.bookOrder(postId, quantity, location, areaCode, houseNo, landmark, latitude, longitude, locationType, price, userId).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        booking.setValue(response.body());
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
        return booking;
    }


    public LiveData<Map> getCharges(Activity activity, String postId, String latitude, String longitude) {
        getDelivery = new MutableLiveData<>();


        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgress(activity);
            api.getDeliverAmt(postId, latitude, longitude).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if (response.isSuccessful()) {
                        getDelivery.setValue(response.body());
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
        return getDelivery;
    }
}
