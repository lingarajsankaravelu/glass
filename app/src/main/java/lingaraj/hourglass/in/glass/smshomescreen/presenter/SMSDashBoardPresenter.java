package lingaraj.hourglass.in.glass.smshomescreen.presenter;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;

public class SMSDashBoardPresenter implements SMSDashboardContracts.Presenter,SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks {


  SMSDashboardContracts.View view;
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

}
