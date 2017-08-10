package com.jdkgroup.weather.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jdkgroup.models.AllList;
import com.jdkgroup.models.Main;
import com.jdkgroup.utils.AppUtils;
import com.jdkgroup.weather.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private AppCompatActivity appCompatActivity;
    private List<AllList> alList;

    public WeatherAdapter(AppCompatActivity appCompatActivity, List<AllList> alList) {
        this.appCompatActivity = appCompatActivity;
        this.alList = alList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_weather, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        AllList list  =  alList.get(position);
        Main main = list.getMain();

        viewHolder.apptvProductName.setText(String.valueOf(main.getGrndLevel()));
    }

    @Override
    public int getItemCount() {
        return (null != alList ? alList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView apptvProductName;
        private final AppCompatImageView appivProductImage;
        private final ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            appivProductImage = (AppCompatImageView) itemView.findViewById(R.id.appivProductImage);
            apptvProductName = (AppCompatTextView) itemView.findViewById(R.id.apptvProductName);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

}

