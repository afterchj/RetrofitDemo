package scut.carson_ho.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Carson_Ho on 17/3/20.
 */

public interface GetRequest_Interface {


    @GET("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    Call<Translation1> getCall(@Query("i") String i);
    // 注解里传入 网络请求 的部分URL地址
    // getCall()是接受网络请求数据的方法
}
