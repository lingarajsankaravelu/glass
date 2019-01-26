package lingaraj.hourglass.in.glass.contracts;

public interface HomeContracts {
  interface View  {
    void showLoading();

    void checkPermissions();

    void requestSMSAppPermissions();

    void permissionsGranted();

    void permissionsDenied();

  }


  interface Presenter {

    void handleInitialChecks();
  }

}
