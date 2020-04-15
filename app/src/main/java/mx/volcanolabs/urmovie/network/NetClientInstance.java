package mx.volcanolabs.urmovie.network;

import android.content.Context;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static mx.volcanolabs.urmovie.Constants.BASE_URL;

public class NetClientInstance {
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(new ChuckInterceptor(context))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RestApi getRestApi(Context context) {
        return getRetrofitInstance(context).create(RestApi.class);
    }
}
