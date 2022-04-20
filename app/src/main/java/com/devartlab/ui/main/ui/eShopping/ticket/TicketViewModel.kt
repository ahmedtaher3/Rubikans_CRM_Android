package com.devartlab.ui.main.ui.eShopping.ticket

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.ticket.model.addRate.AddRateRequest
import com.devartlab.ui.main.ui.eShopping.ticket.model.addRate.AddRateResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket.AddTicketRequest
import com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket.AddTicketRsponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.deleteMessages.DeleteMessagesResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.deleteTickets.DeleteTicketsResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.fetchMessages.FetchMessagesResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.getContacts.GetContactsResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.sendMessages.SendMessagesResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var getContactsResponse: MutableLiveData<GetContactsResponse?>
        protected set
    var fetchMessagesResponse: MutableLiveData<FetchMessagesResponse?>
        protected set
    var addTicketRsponse: MutableLiveData<AddTicketRsponse?>
        protected set
    var sendMessagesResponse: MutableLiveData<SendMessagesResponse?>
        protected set
    var addRateResponse: MutableLiveData<AddRateResponse?>
        protected set
    var deleteTicketsResponse: MutableLiveData<DeleteTicketsResponse?>
        protected set
    var deleteMessagesResponse: MutableLiveData<DeleteMessagesResponse?>
        protected set

    fun getGetContacts(status: String,q: String) {
        RetrofitClient.getApis4EShopping().getGetContacts("Bearer " + UserPreferenceHelper.getUser().token,status,q)!!
            .enqueue(object : Callback<GetContactsResponse?> {
                override fun onResponse(
                    call: Call<GetContactsResponse?>,
                    response: Response<GetContactsResponse?>
                ) {
                    if (response.isSuccessful) {
                        getContactsResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetContactsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getChatList(id: String) {
        RetrofitClient.getApis4EShopping().getChatList("Bearer " + UserPreferenceHelper.getUser().token, id)!!
            .enqueue(object : Callback<FetchMessagesResponse?> {
                override fun onResponse(
                    call: Call<FetchMessagesResponse?>,
                    response: Response<FetchMessagesResponse?>
                ) {
                    if (response.isSuccessful) {
                        fetchMessagesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FetchMessagesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun addTicket(request: AddTicketRequest) {
        RetrofitClient.getApis4EShopping()
            .addTicket("Bearer " + UserPreferenceHelper.getUser().token, request)!!
            .enqueue(object : Callback<AddTicketRsponse?> {
                override fun onResponse(
                    call: Call<AddTicketRsponse?>,
                    response: Response<AddTicketRsponse?>
                ) {
                    if (response.isSuccessful) {
                        addTicketRsponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddTicketRsponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun sendMessages(file: MultipartBody.Part, send: MutableMap<String, RequestBody>) {
        RetrofitClient.getApis4EShopping().SEND_MESSAGES(
            "Bearer " + UserPreferenceHelper.getUser().token, file, send)!!
            .enqueue(object : Callback<SendMessagesResponse?> {
                override fun onResponse(
                    call: Call<SendMessagesResponse?>,
                    response: Response<SendMessagesResponse?>
                ) {
                    if (response.isSuccessful()) {
                        sendMessagesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SendMessagesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun sendMessages( send: MutableMap<String, RequestBody>) {
        RetrofitClient.getApis4EShopping().SEND_MESSAGES(
            "Bearer " + UserPreferenceHelper.getUser().token, send)!!
            .enqueue(object : Callback<SendMessagesResponse?> {
                override fun onResponse(
                    call: Call<SendMessagesResponse?>,
                    response: Response<SendMessagesResponse?>
                ) {
                    if (response.isSuccessful()) {
                        sendMessagesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SendMessagesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun addRate(request: AddRateRequest) {
        RetrofitClient.getApis4EShopping()
            .addRate("Bearer " + UserPreferenceHelper.getUser().token, request)!!
            .enqueue(object : Callback<AddRateResponse?> {
                override fun onResponse(
                    call: Call<AddRateResponse?>,
                    response: Response<AddRateResponse?>
                ) {
                    if (response.isSuccessful) {
                        addRateResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddRateResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun deleteTicket(id: String) {
        RetrofitClient.getApis4EShopping().deleteTicket("Bearer " + UserPreferenceHelper.getUser().token, id)!!
            .enqueue(object : Callback<DeleteTicketsResponse?> {
                override fun onResponse(
                    call: Call<DeleteTicketsResponse?>,
                    response: Response<DeleteTicketsResponse?>
                ) {
                    if (response.isSuccessful) {
                        deleteTicketsResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeleteTicketsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun deleteMessages(id: String) {
        RetrofitClient.getApis4EShopping().deleteMessages("Bearer " + UserPreferenceHelper.getUser().token, id)!!
            .enqueue(object : Callback<DeleteMessagesResponse?> {
                override fun onResponse(
                    call: Call<DeleteMessagesResponse?>,
                    response: Response<DeleteMessagesResponse?>
                ) {
                    if (response.isSuccessful) {
                        deleteMessagesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeleteMessagesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {
        getContactsResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        fetchMessagesResponse = MutableLiveData()
        addTicketRsponse = MutableLiveData()
        sendMessagesResponse = MutableLiveData()
        addRateResponse= MutableLiveData()
        deleteTicketsResponse= MutableLiveData()
        deleteMessagesResponse=MutableLiveData()
    }
}