package lingaraj.hourglass.in.glass.models;

import android.content.Context;
import android.content.SharedPreferences;
import lingaraj.hourglass.in.glass.Constants;

public class BaseAppSharedPreference {

  private SharedPreferences mSharedPreferences;
  final String TOKEN = "APITOKEN";

  public BaseAppSharedPreference(Context context) {
    this.mSharedPreferences = context.getSharedPreferences(Constants.BASE_SHARED_PREF,Context.MODE_PRIVATE) ;
  }

  public void setToken(String token){
     this.mSharedPreferences.edit().putString(TOKEN,token).apply();
  }
  public String getToken(){
    return this.mSharedPreferences.getString(TOKEN,"");
  }
}