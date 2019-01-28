package lingaraj.hourglass.in.glass.smshomescreen.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import javax.inject.Inject;
import lingaraj.hourglass.in.glass.R;
import lingaraj.hourglass.in.glass.contracts.HomeContracts;
import lingaraj.hourglass.in.glass.databinding.ActivityMainBinding;
import lingaraj.hourglass.in.glass.injection.components.DaggerHomeActivityComponent;
import lingaraj.hourglass.in.glass.injection.modules.HomeActivityModule;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.HomeActivityPresenter;

public class HomeActivity extends AppCompatActivity implements HomeContracts.View,View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback {

  public static class Keys {
    public static final String Sender = "sendername";
  }

  private final String TAG = "MAINACT";
  private ActivityMainBinding binding;
  @Inject HomeActivityPresenter presenter;
  private static final int SMS_PERMISSION_CODE = 103;
  private static final String[] SMS_PERMISSIONS = { Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS };



  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    binding.progressContainer.retry.setOnClickListener(this);
    DaggerHomeActivityComponent.builder().homeActivityModule(new HomeActivityModule(this)).build().inject(this);

  }

  @Override public void showLoading() {
    Log.d(TAG,"Showing Loading");
    binding.progressContainer.progressBar.setVisibility(View.VISIBLE);
    binding.progressContainer.errorMesageView.setVisibility(View.VISIBLE);
    binding.progressContainer.errorMesageView.setText(this.getString(R.string.common_loading_message));
    binding.progressContainer.errorView.setVisibility(View.GONE);
    binding.progressContainer.retry.setVisibility(View.GONE);
    if (binding.viewSwitcher.getDisplayedChild()==1){
      binding.viewSwitcher.showPrevious();
    }

  }



  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.retry:
        presenter.handleInitialChecks();
        break;
      default:
        break;
    }

  }

  @Override public void permissionsGranted() {
    SMSDashboardFragment dashboardFragment = new SMSDashboardFragment();
    getSupportFragmentManager().beginTransaction().add(binding.frame.getId(),dashboardFragment).commit();
    if (binding.viewSwitcher.getDisplayedChild()==0){
      binding.viewSwitcher.showNext();
    }
    Log.d(TAG,"Permissions Granted by user and message Dashboard set");


  }

  @Override public void permissionsDenied() {

    Log.d(TAG,"Showing Views for Permission Denied");
    binding.progressContainer.progressBar.setVisibility(View.GONE);
    binding.progressContainer.errorMesageView.setVisibility(View.VISIBLE);
    binding.progressContainer.errorMesageView.setText(this.getString(R.string.error_permission_denied));
    binding.progressContainer.errorView.setVisibility(View.VISIBLE);
    binding.progressContainer.retry.setVisibility(View.VISIBLE);
    if (binding.viewSwitcher.getDisplayedChild()==1){
      binding.viewSwitcher.showPrevious();
    }
    Log.d(TAG,"Permissions  Denied By user");

  }

  @Override
  public void checkPermissions(){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
        ||(isPermissionGranted(Manifest.permission.READ_SMS) && isPermissionGranted(
        Manifest.permission.RECEIVE_SMS))){
        permissionsGranted();
      }
      else {
        requestSMSAppPermissions();
      }

   }

  private boolean isPermissionGranted(String permissionName){
    int result = ContextCompat.checkSelfPermission(HomeActivity.this,permissionName);
    boolean PERMISSION_STATUS = (result == PackageManager.PERMISSION_GRANTED);
    Log.d(TAG,permissionName+":"+PERMISSION_STATUS);
    return PERMISSION_STATUS;

  }


  @Override
  public void requestSMSAppPermissions() {
    ActivityCompat.requestPermissions(HomeActivity.this, SMS_PERMISSIONS, SMS_PERMISSION_CODE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode){
      case SMS_PERMISSION_CODE:
        if (grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
          Log.d(TAG,"Permission Granted");
          permissionsGranted();
        }
        else {
          Log.d(TAG,"Permission Denied");
          permissionsDenied();
        }
        break;
      default:
        break;
    }
  }



}
