package lingaraj.hourglass.in.glass.smshomescreen.presenter;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import javax.inject.Inject;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;

public class SMSDashBoardPresenter implements SMSDashboardContracts.Presenter,SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks {


  SMSDashboardContracts.View view;

  @Inject
  public SMSDashBoardPresenter(SMSDashboardContracts.View view){
    this.view = view;

  }

  @Override
  public void onMessageRetrievalCompletion(@Nullable ArrayList<MessageDataModel> messages) {
      if (messages==null){
        view.showError();
      }
      else {
        if (messages.size()==0){
          view.noMessagesToDisplay();
        }
        else {
          view.setMessages(messages);
        }
      }
  }

  @Override
  public void getMessages() {
      view.showLoader();
      view.startFetchSMSAsyncTask(this);
  }
}
