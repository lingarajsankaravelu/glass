package lingaraj.hourglass.in.glass.smshomescreen.presenter;

import javax.inject.Inject;
import lingaraj.hourglass.in.glass.contracts.HomeContracts;

public class HomeActivityPresenter implements HomeContracts.Presenter {

  HomeContracts.View view;

  @Inject public HomeActivityPresenter(HomeContracts.View view){
    this.view = view;
    handleInitialChecks();
  }

  @Override public void handleInitialChecks() {
    view.showLoading();
    view.checkPermissions();

  }
}
