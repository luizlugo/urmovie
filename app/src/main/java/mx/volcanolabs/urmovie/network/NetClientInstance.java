package mx.volcanolabs.urmovie.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/";

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RestApi getRestApi() {
        return getRetrofitInstance().create(RestApi.class);
    }
}
