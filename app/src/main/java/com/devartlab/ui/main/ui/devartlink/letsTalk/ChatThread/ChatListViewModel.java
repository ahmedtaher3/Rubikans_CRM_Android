package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.devartlab.base.BaseApplication;
import com.devartlab.data.retrofit.RetrofitClient;
import com.devartlab.data.shared.DataManager;
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.ChatListResponse;
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.sendMessages.SendMessagesResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListViewModel extends AndroidViewModel {

    protected MutableLiveData<Integer> errorMessage;
    protected MutableLiveData<ChatListResponse> chatListResponseMutableLiveData;
    protected MutableLiveData<SendMessagesResponse> sendMessagesResponseMutableLiveData;
    DataManager dataManager;

    public ChatListViewModel(@NonNull Application application) {
        super(application);

        dataManager = ((BaseApplication) application).getDataManager();


        chatListResponseMutableLiveData = new MutableLiveData<>();
        sendMessagesResponseMutableLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<ChatListResponse> getPeapleResponseMutableLiveData() {
        return chatListResponseMutableLiveData;
    }

    public MutableLiveData<SendMessagesResponse> getSendMessagesResponseMutableLiveData() {
        return sendMessagesResponseMutableLiveData;
    }

    public void getChatList(String id){
        new RetrofitClient(dataManager).getApis().getChatList(id).enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (response.isSuccessful()){
                    chatListResponseMutableLiveData.postValue(response.body());
                }else {
                    chatListResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }


    public void sendMessages(MultipartBody.Part file, Map<String, RequestBody> send){
        new RetrofitClient(dataManager).getApis().SEND_MESSAGES(file,send).enqueue(new Callback<SendMessagesResponse>() {
            @Override
            public void onResponse(Call<SendMessagesResponse> call, Response<SendMessagesResponse> response) {
                if (response.isSuccessful()){
                    sendMessagesResponseMutableLiveData.postValue(response.body());
                }else {
                    sendMessagesResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SendMessagesResponse> call, Throwable t) {
                errorMessage.postValue(1);
            }
        });
    }

}
