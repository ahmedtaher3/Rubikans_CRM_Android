package com.devartlab.ui.main.ui.devartlink.letsTalk;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.devartlab.base.BaseApplication;
import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.data.shared.DataManager;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.searchPeople.SearchPeapleResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.userID.UserIDResponse;
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetsTalkViewModel extends AndroidViewModel {

    protected MutableLiveData<Integer> errorMessage;
    protected MutableLiveData<SearchPeapleResponse> peapleResponseMutableLiveData;
    protected MutableLiveData<UserIDResponse> userIDResponseMutableLiveData;
    DataManager dataManager;

    public LetsTalkViewModel(@NonNull Application application) {
        super(application);

        peapleResponseMutableLiveData = new MutableLiveData<>();
        userIDResponseMutableLiveData = new MutableLiveData<>();
        dataManager = ((BaseApplication) getApplication()).getDataManager();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<UserIDResponse> getUserIDResponseMutableLiveData() {
        return userIDResponseMutableLiveData;
    }

    public MutableLiveData<SearchPeapleResponse> getPeapleResponseMutableLiveData() {
        return peapleResponseMutableLiveData;
    }
    public void getpeapleSearchList(){
        RetrofitClient.getApis().getSearchPeaple().enqueue(new Callback<SearchPeapleResponse>() {
            @Override
            public void onResponse(Call<SearchPeapleResponse> call, Response<SearchPeapleResponse> response) {
                if (response.isSuccessful()){
                    peapleResponseMutableLiveData.postValue(response.body());
                }else {
                    peapleResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchPeapleResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }

    public void getUserID(String userID){
        RetrofitClient.getApis().getUserID(UserPreferenceHelper.getUserChat()
                .getId(),userID).enqueue(new Callback<UserIDResponse>() {
            @Override
            public void onResponse(Call<UserIDResponse> call, Response<UserIDResponse> response) {
                if (response.isSuccessful()){
                    userIDResponseMutableLiveData.postValue(response.body());
                }else {
                    userIDResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserIDResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }
}
