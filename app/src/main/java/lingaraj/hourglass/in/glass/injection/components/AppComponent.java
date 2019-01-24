package lingaraj.hourglass.in.glass.injection.components;

import com.google.gson.Gson;
import dagger.Component;
import javax.inject.Singleton;
import lingaraj.hourglass.in.glass.GlassApp;
import lingaraj.hourglass.in.glass.injection.modules.AppModule;
import lingaraj.hourglass.in.glass.models.BaseAppSharedPreference;
import retrofit2.Retrofit;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {
   void inject(GlassApp app);
   GlassApp provideApp();
   BaseAppSharedPreference providesBaseSharedPreference();
   Retrofit providesRetrofit();
   Gson providesGson();
}
