package com.jdkgroup.interacter;


import com.google.gson.Gson;
import com.jdkgroup.connection.RestConstant;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.MainWeather;
import com.jdkgroup.models.Response;
import com.jdkgroup.utils.AppUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.jdkgroup.connection.RestClient.getPrimaryService;

public class AppInteractor {

    public boolean isCancel;

    public AppInteractor() {

    }

    private void sendResponse(InterActorCallback callback, Response response) {
        if (!isCancel) {
            callback.onResponse(response);
        }
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void cancel() {
        isCancel = true;
    }

    private void displayRequestParams(HashMap<String, String> hashMap) {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            AppUtils.loge(AppConstant.API_PARAMETER +  pair.getKey() + pair.getValue());
        }
    }

    //Call Api for Weather
    public Subscription callWeatherApi(final HashMap<String, String> params, final InterActorCallback<MainWeather> callback) {
        return getPrimaryService().apiGet(RestConstant.BASE_URL + RestConstant.WEATHER, params)
                .map(new Func1<String, MainWeather>() {
                    @Override
                    public MainWeather call(String s) {
                        displayRequestParams(params);
                        return new Gson().fromJson(s, MainWeather.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new InterActorOnSubscribe<>(callback))
                .subscribe(new InterActorSubscriber<MainWeather>(callback, this) {
                    @Override
                    public void onNext(MainWeather response) {
                        sendResponse(callback, response);
                    }
                });
    }

}

