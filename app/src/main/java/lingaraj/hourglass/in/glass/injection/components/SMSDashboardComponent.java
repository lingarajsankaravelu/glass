package lingaraj.hourglass.in.glass.injection.components;

import dagger.Component;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.injection.ActivityScope;
import lingaraj.hourglass.in.glass.injection.modules.SMSDashboardModule;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.SMSDashBoardPresenter;
import lingaraj.hourglass.in.glass.smshomescreen.view.SMSDashboardFragment;

@ActivityScope  @Component (modules = SMSDashboardModule.class)
public interface SMSDashboardComponent {
  SMSDashboardContracts.View  view();
  SMSDashBoardPresenter presenter();
  void inject(SMSDashboardFragment fragment);
}
