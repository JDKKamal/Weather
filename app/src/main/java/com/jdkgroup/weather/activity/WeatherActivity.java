package com.jdkgroup.weather.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.jdkgroup.baseclasses.SimpleMVPActivity;
import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.MainWeather;
import com.jdkgroup.presenter.WeatherPresenter;
import com.jdkgroup.utils.AppUtils;
import com.jdkgroup.view.WeatherView;
import com.jdkgroup.weather.R;
import com.jdkgroup.weather.adapter.WeatherAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends SimpleMVPActivity<WeatherPresenter, WeatherView> implements WeatherView  {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private WeatherAdapter weatherAdapter;

    private AppCompatActivity appCompatActivity;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appCompatActivity = this;

        init();

        HashMap<String, String> mapWhether = getDefaultParameter();
        mapWhether.put(AppConstant.key_appid, AppConstant.value_appid);
        mapWhether.put(AppConstant.key_id, AppConstant.value_id);
        getPresenter().callWeatherApi(this, mapWhether);
    }

    private void init()
    {
        linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @NonNull
    @Override
    public WeatherPresenter createPresenter() {
        return new WeatherPresenter();
    }

    @NonNull
    @Override
    public WeatherView attachView() {
        return this;
    }

    @Override
    public void onSuccess(MainWeather response) {
        weatherAdapter = new WeatherAdapter(appCompatActivity, response.getList());
        recyclerView.setAdapter(weatherAdapter);
    }

    @Override
    public void onFailure(String message) {
        Gson gson = new Gson();
        AppUtils.showToast(this, gson.toJson(message));
    }
}
