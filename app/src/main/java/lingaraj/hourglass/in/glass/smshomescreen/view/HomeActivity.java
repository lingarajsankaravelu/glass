package lingaraj.hourglass.in.glass.smshomescreen.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import lingaraj.hourglass.in.glass.GlassApp;
import lingaraj.hourglass.in.glass.R;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;
import lingaraj.hourglass.in.glass.smshomescreen.tasks.ReadMobileSMSAsyncTask;

public class HomeActivity extends AppCompatActivity  {

  private final String TAG = "MAINACT";
  GlassApp app;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    app = (GlassApp) getApplication();
  }

}
