package lingaraj.hourglass.in.glass.injection.components;

import dagger.Component;
import lingaraj.hourglass.in.glass.contracts.HomeContracts;
import lingaraj.hourglass.in.glass.injection.ActivityScope;
import lingaraj.hourglass.in.glass.injection.modules.HomeActivityModule;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.HomeActivityPresenter;
import lingaraj.hourglass.in.glass.smshomescreen.view.HomeActivity;

@ActivityScope @Component(modules = HomeActivityModule.class)
public interface HomeActivityComponent {
  HomeContracts.View view();
  HomeActivityPresenter presenter();
  void inject(HomeActivity activity);

}
