package lingaraj.hourglass.in.glass;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import lingaraj.hourglass.in.glass.injection.components.AppComponent;
import lingaraj.hourglass.in.glass.injection.components.DaggerAppComponent;
import lingaraj.hourglass.in.glass.injection.modules.AppModule;

public class GlassApp extends Application {

  private ConnectivityManager mConnectivity;

  public AppComponent getAppComponent() {
    return appComponent;
  }

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    appComponent.inject(this);
   }

  public boolean isNetworkAvailable() {
    if (mConnectivity == null) {
      mConnectivity = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
      return mConnectivity.getActiveNetworkInfo() != null && mConnectivity.getActiveNetworkInfo().isConnected();
      }
      else {
      return false;
    }

  }



}
