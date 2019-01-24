package lingaraj.hourglass.in.glass.injection.modules;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lingaraj.hourglass.in.glass.GlassApp;
import lingaraj.hourglass.in.glass.BuildConfig;
import lingaraj.hourglass.in.glass.Constants;
import lingaraj.hourglass.in.glass.models.BaseAppSharedPreference;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

   GlassApp app;
   BaseAppSharedPreference baseSharedPreference;
   Gson gson;

  public AppModule(GlassApp glassApp){
    this.app = glassApp;
    this.baseSharedPreference = new BaseAppSharedPreference(this.app);
    this.gson = new Gson();
  }

  @Provides GlassApp provideApp(){
    return this.app;
  }

  @Provides BaseAppSharedPreference providesBaseSharedPreference(){
    return this.baseSharedPreference;
  }

  @Provides Retrofit providesRetrofit(final BaseAppSharedPreference sharedPreference){
    OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
    okHttpClient.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder request_builder = original.newBuilder().addHeader("Authorization", "Bearer " +sharedPreference.getToken());
        Request request = request_builder.build();
        return chain.proceed(request);
      }
    })
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(12,TimeUnit.SECONDS)
        .writeTimeout(12,TimeUnit.SECONDS)
        .addNetworkInterceptor(getLogInterceptor());
    //OkHttpClient okHttpClient = new OkHttpClient();
    return new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build()).build();
  }

  @Provides Gson providesGson(){
    return this.gson;
  }


  private HttpLoggingInterceptor getLogInterceptor(){
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG){
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    else {
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

    }
    return httpLoggingInterceptor;

  }

}
