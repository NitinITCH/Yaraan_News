package com.app.yaraan.retrofit;




import com.app.yaraan.activities.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class RestClient {
//    private static ApiService apiService = null;
//    private static String BaseUrl = null;
//  Interceptor interceptor =new Interceptor() {
//        @Override
//        public Response intercept(Interceptor.Chain chain) throws IOException {
//            Request original = chain.request();
//
//            Request request = original.newBuilder()
//                    .header("User-Agent", "Your-App-Name")
//                    .header("Accept", "application/vnd.yourapi.v1.full+json")
//                    .method(original.method(), original.body())
//                    .build();
//
//            return chain.proceed(request);
//        }

    public static ApiService getServices() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiService.class);
    }
//    public static ApiService getLocalServices() {
//
//        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
//                .baseUrl(Constants.Base)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//       ApiService apiService = retrofit.create(ApiService.class);
//        return  apiService;
//    }

//public static  ApiService getRxService(){
//    retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(RxJavaCallAdapterFactory.create())
//            .build();
//    return retrofit.create(ApiService.class);
//}
}
