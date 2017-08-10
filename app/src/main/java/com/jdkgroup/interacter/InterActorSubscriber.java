package com.jdkgroup.interacter;

import android.content.ActivityNotFoundException;

import com.jdkgroup.constant.AppConstant;
import com.jdkgroup.models.Response;
import com.jdkgroup.utils.AppUtils;
import com.jdkgroup.weather.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import rx.Subscriber;

import static com.jdkgroup.baseclasses.BaseApplication.getBaseApplication;

/**
 * This class is used for Indicating InterActor process whether completed or in error otherwise you
 * need to override onNext method for handling response.
 */
abstract class InterActorSubscriber<T extends Response> extends Subscriber<T> {

    private final String TAG = getClass().getSimpleName();
    private InterActorCallback<T> mInterActorCallback;
    private AppInteractor appInteractor;

    InterActorSubscriber(InterActorCallback<T> mInterActorCallback, AppInteractor appInteractor) {
        this.mInterActorCallback = mInterActorCallback;
        this.appInteractor = appInteractor;
    }

    @Override
    public void onCompleted() {
        if (!appInteractor.isCancel) {
            mInterActorCallback.onFinish();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!appInteractor.isCancel) {
            AppUtils.loge(AppConstant.API_CALL_ERROR + e.toString());
            if (e instanceof SocketTimeoutException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_connection_time_out));
            } else if (e instanceof ActivityNotFoundException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_activity_not_found));
            } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_server_not_responding));
            } else {
                mInterActorCallback.onError(getBaseApplication().getResources().getString(R.string.msg_server_not_responding));
            }
            mInterActorCallback.onFinish();
        }
    }
}