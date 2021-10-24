package com.devartlab.ui.main.ui.contactlist.data.retrofit;



import com.devartlab.ui.main.ui.contactlist.pojo.Request;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiServices1 {


    String  BASE_URL = " https://script.google.com/macros/s/";



    @GET("AKfycbzJhaIFNft5G4SGUUxCayA1OS8l8aMXklmIq6D_7lJkhCe3faowkgKhJftesvqt1bSf/exec?action=readAll")
    Single<Request> getcontact();
}
