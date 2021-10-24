package com.devartlab.ui.main.ui.contactlist.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.devartlab.ui.main.ui.contactlist.data.retrofit.RetrofitClient1;
import com.devartlab.ui.main.ui.contactlist.pojo.Request;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetContactsViewModel extends ViewModel {

    private static final String TAG = "ContactlistViewModel";
    MutableLiveData googlesheetMutableLiveData = new MutableLiveData<>();

    public void getRequest() {
        Single<Request> observable = RetrofitClient1.getINSTANCE().getcontact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(o -> googlesheetMutableLiveData.setValue(o), e -> Log.d(TAG, "RequestDetails: " + e.getMessage()));

    }



}
