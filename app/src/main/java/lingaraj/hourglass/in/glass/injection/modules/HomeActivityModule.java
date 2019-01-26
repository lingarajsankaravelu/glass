package lingaraj.hourglass.in.glass.injection.modules;

import dagger.Module;
import dagger.Provides;
import lingaraj.hourglass.in.glass.contracts.HomeContracts;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.HomeActivityPresenter;

@Module
public class HomeActivityModule {

  HomeContracts.View view;

  public HomeActivityModule(HomeContracts.View view){
    this.view = view;
    }

  @Provides HomeContracts.View view(){
    return this.view;
  }

  @Provides HomeActivityPresenter presenter(HomeContracts.View view){
    return new HomeActivityPresenter(view);

  }




}
