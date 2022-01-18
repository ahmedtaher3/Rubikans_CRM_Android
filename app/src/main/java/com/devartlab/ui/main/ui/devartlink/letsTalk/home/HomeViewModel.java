package com.devartlab.ui.main.ui.devartlink.letsTalk.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.ImageModel.ImageProfileResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen.MarkSeenRequest;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen.MarkSeenResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList.PeopleListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    protected MutableLiveData<Integer> errorMessage;
    protected MutableLiveData<PeopleListResponse> peapleResponseMutableLiveData;
    protected MutableLiveData<ImageProfileResponse> imageProfileResponseMutableLiveData;
    protected MutableLiveData<MarkSeenResponse> markSeenRequestMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        peapleResponseMutableLiveData = new MutableLiveData<>();
        peapleResponseMutableLiveData = new MutableLiveData<>();
        imageProfileResponseMutableLiveData = new MutableLiveData<>();
        markSeenRequestMutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<ImageProfileResponse> getImageProfileResponseMutableLiveData() {
        return imageProfileResponseMutableLiveData;
    }

    public MutableLiveData<MarkSeenResponse> getMarkSeenRequestMutableLiveData() {
        return markSeenRequestMutableLiveData;
    }

    public MutableLiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<PeopleListResponse> getPeapleResponseMutableLiveData() {
        return peapleResponseMutableLiveData;
    }

    public void getListPeaple(String id){
        RetrofitClient.getApis().getPeopleList(id).enqueue(new Callback<PeopleListResponse>() {
            @Override
            public void onResponse(Call<PeopleListResponse> call, Response<PeopleListResponse> response) {
                if (response.isSuccessful()){
                    peapleResponseMutableLiveData.postValue(response.body());
                }else {
                    peapleResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PeopleListResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }
    public void getImageProfile(String id){
        RetrofitClient.getApis().getImageProfile(id).enqueue(new Callback<ImageProfileResponse>() {
            @Override
            public void onResponse(Call<ImageProfileResponse> call, Response<ImageProfileResponse> response) {
                if (response.isSuccessful()){
                    imageProfileResponseMutableLiveData.postValue(response.body());
                }else {
                    imageProfileResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ImageProfileResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }
    public void markSeen(MarkSeenRequest markSeenRequest){
        RetrofitClient.getApis().MARK_SEEN(markSeenRequest).enqueue(new Callback<MarkSeenResponse>() {
            @Override
            public void onResponse(Call<MarkSeenResponse> call, Response<MarkSeenResponse> response) {
                if (response.isSuccessful()){
                    markSeenRequestMutableLiveData.postValue(response.body());
                }else {
                    markSeenRequestMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MarkSeenResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }
}
