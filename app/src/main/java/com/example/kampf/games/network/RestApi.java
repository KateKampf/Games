package com.example.kampf.games.network;

import com.example.kampf.games.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi {

    private static final String BASE_URL = "https://www.giantbomb.com/api/";
    private static final long CONNECT_TIMEOUT = 60;
    private static final long READ_TIMEOUT = 60;

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .validateEagerly(true)
            .client(buildOkHttpClient())
            .addConverterFactory(createConverterFactory())
            .build();

    public static <S> S createService(Class<S> klass) {
        return retrofit.create(klass);
    }

    private static Converter.Factory createConverterFactory() {
        return GsonConverterFactory.create(
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
        );

    }

    private static OkHttpClient buildOkHttpClient() {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {

            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

        }

        return builder.build();

    }

}
