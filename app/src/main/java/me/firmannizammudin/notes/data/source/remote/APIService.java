package me.firmannizammudin.notes.data.source.remote;

import java.util.concurrent.TimeUnit;

import me.firmannizammudin.notes.data.Note;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Firman on 05-Oct-16.
 */

public interface APIService {

    String BASE_URL = "https://private-07abdb-todoapp11.apiary-mock.com/";

    @GET("tasks")
    Observable<Note.Notes> getNotes();

    class factory {
        public static APIService create() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(APIService.class);
        }
    }
}
