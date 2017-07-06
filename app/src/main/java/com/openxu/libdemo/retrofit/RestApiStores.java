package com.openxu.libdemo.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * autour: openXu
 * date: 2017/4/27 16:29 
 * className:  RestApiStores
 * version: 
 * description:
 *
 * 1. Rest API是指服务器端的API，一般会暴露get，post方法;
 *    RestApiStores其实是对Rest API的一个映射关系，在实际开发中，
 *    我们可以定义：public interface ClientService，\
 *    里面包含post ,get 方法。
 *
 * 2. 接口中的方法使用了Retrofit的注解，Retrofit这个库给了我们很多注解
 * 3. getWeather这个方法表示：一个get请求获取给定URL的Repo集合。
 *    getWeather传入的参数为我们需要get的url的动态部分。
 *    这里的Repo为我们自己定义的java bean的类：Repo.class用于封装获取的Jason数据。
 *    //注意：此处Retrofit又帮我们省掉了很多工作，只需要我们自己定义业务对应的实体类，
 *     而Jason数据的转换和封装则帮我们封装好了只需我们调用。
 */
public interface RestApiStores {
    // 天气预报url     baseUrl must end in /:
    public static final String WEATHER = "http://wthrcdn.etouch.cn/";

//    @GET("users/{cityName}/repos")
//    Call<List<Repo>> listRepos(@Path("cityName") String user);
// you can add some other meathod


    //weather_mini/{city} 等价于 weather_mini?city=xxx
    @GET("weather_mini/{city}")      // here is the other url part.best way is to start using /
    Call<WeatherBean> getWeather(@Path("city") String cityId);
    // string user is for passing values from edittext for eg: user=basil2style,google
    // response is the response from the server which is now in the POJO

   /* //加载天气
    @GET("adat/sk/{cityId}.html")
    Call<MainModel> loadDataByRetrofit(@Path("cityId") String cityId);

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxjava(@Path("cityId") String cityId);*/


}
