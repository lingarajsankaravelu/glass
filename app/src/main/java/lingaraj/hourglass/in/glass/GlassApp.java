package lingaraj.hourglass.in.glass;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import lingaraj.hourglass.in.glass.injection.components.AppComponent;
import lingaraj.hourglass.in.glass.injection.components.DaggerAppComponent;
import lingaraj.hourglass.in.glass.injection.modules.AppModule;

public class GlassApp extends Application {

  private ConnectivityManager mConnectivity;
  private ContentResolver contentResolver;

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

  public ContentResolver getContentResolver(){
    if (this.contentResolver==null){
      this.contentResolver = this.getBaseContext().getContentResolver();
    }
    return contentResolver;
  }



}
