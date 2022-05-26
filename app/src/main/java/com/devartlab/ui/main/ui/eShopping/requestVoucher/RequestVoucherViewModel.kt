package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.compaignVouchers.CompaignVouchersResponse
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.deliverVoucher.DeliverVoucherRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.deliverVoucher.DeliverVoucherResponse
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.doctors.GetDoctorsResponse
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.getVoucher.GetVoucherResponse
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.myVoucherRequest.MyVoucherRequestResponse
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestRequest
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.voucherRequest.VoucherRequestResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestVoucherViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var compaignVouchersResponse: MutableLiveData<CompaignVouchersResponse?>
        protected set
    var getDoctorsResponse: MutableLiveData<GetDoctorsResponse?>
        protected set
    var voucherRequestResponse: MutableLiveData<VoucherRequestResponse?>
        protected set
    var myVoucherRequestResponse: MutableLiveData<MyVoucherRequestResponse?>
        protected set
    var getVoucherResponse: MutableLiveData<GetVoucherResponse?>
        protected set
    var deliverVoucherResponse: MutableLiveData<DeliverVoucherResponse?>
        protected set
    var dataManager: DataManager
    fun getCompaignVouchers() {
        RetrofitClient(dataManager).apis4EShopping.getCompaignVouchers("Bearer "+ UserPreferenceHelper.getUser().token,dataManager.getLang())!!
            .enqueue(object : Callback<CompaignVouchersResponse?> {
                override fun onResponse(
                    call: Call<CompaignVouchersResponse?>,
                    response: Response<CompaignVouchersResponse?>
                ) {
                    if (response.isSuccessful) {
                        compaignVouchersResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CompaignVouchersResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getDoctors(q:String) {
        RetrofitClient(dataManager).apis4EShopping.getDoctors("Bearer "+ UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<GetDoctorsResponse?> {
                override fun onResponse(
                    call: Call<GetDoctorsResponse?>,
                    response: Response<GetDoctorsResponse?>
                ) {
                    if (response.isSuccessful) {
                        getDoctorsResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetDoctorsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getVoucherRequest(voucherRequestRequest:VoucherRequestRequest) {
        RetrofitClient(dataManager).apis4EShopping.getVoucherRequest("Bearer "+ UserPreferenceHelper.getUser().token,voucherRequestRequest)!!
            .enqueue(object : Callback<VoucherRequestResponse?> {
                override fun onResponse(
                    call: Call<VoucherRequestResponse?>,
                    response: Response<VoucherRequestResponse?>
                ) {
                    if (response.isSuccessful) {
                        voucherRequestResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<VoucherRequestResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getMyVoucherRequest() {
        RetrofitClient(dataManager).apis4EShopping.getMyVoucherRequest("Bearer "+ UserPreferenceHelper.getUser().token)!!
            .enqueue(object : Callback<MyVoucherRequestResponse?> {
                override fun onResponse(
                    call: Call<MyVoucherRequestResponse?>,
                    response: Response<MyVoucherRequestResponse?>
                ) {
                    if (response.isSuccessful) {
                        myVoucherRequestResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MyVoucherRequestResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getVouchers(id:String) {
        RetrofitClient(dataManager).apis4EShopping.getVouchers("Bearer "+ UserPreferenceHelper.getUser().token,id)!!
            .enqueue(object : Callback<GetVoucherResponse?> {
                override fun onResponse(
                    call: Call<GetVoucherResponse?>,
                    response: Response<GetVoucherResponse?>
                ) {
                    if (response.isSuccessful) {
                        getVoucherResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetVoucherResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getDeliverVoucher(voucherRequestRequest: DeliverVoucherRequest) {
        RetrofitClient(dataManager).apis4EShopping.getDeliverVoucher("Bearer "+ UserPreferenceHelper.getUser().token,voucherRequestRequest)!!
            .enqueue(object : Callback<DeliverVoucherResponse?> {
                override fun onResponse(
                    call: Call<DeliverVoucherResponse?>,
                    response: Response<DeliverVoucherResponse?>
                ) {
                    if (response.isSuccessful) {
                        deliverVoucherResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeliverVoucherResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        compaignVouchersResponse = MutableLiveData()
        getDoctorsResponse=MutableLiveData()
        voucherRequestResponse= MutableLiveData()
        myVoucherRequestResponse= MutableLiveData()
        getVoucherResponse= MutableLiveData()
        deliverVoucherResponse= MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        errorMessage = MutableLiveData()
    }
}