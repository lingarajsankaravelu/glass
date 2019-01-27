package lingaraj.hourglass.in.glass.injection.modules;

import dagger.Module;
import dagger.Provides;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.SMSDashBoardPresenter;

@Module
public class SMSDashboardModule {
  public SMSDashboardContracts.View view;

  public SMSDashboardModule(SMSDashboardContracts.View view){
    this.view = view;
  }

  @Provides SMSDashboardContracts.View view(){
    return this.view;
  }

  @Provides SMSDashBoardPresenter presenter(SMSDashboardContracts.View view){
    return new SMSDashBoardPresenter(view);
  }
}
