package lingaraj.hourglass.in.glass.contracts;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;

public interface SMSDashboardContracts {

  interface View  {

    void showLoader();

    void showError();

    void noMessagesToDisplay();

    void setMessages(ArrayList<MessageDataModel> messages);

    void startFetchSMSAsyncTask(SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks callbacks);
  }

  public interface Presenter {

    void getMessages();

    public interface SmsContentProviderAccessCallbacks {
      void onMessageRetrievalCompletion(@Nullable ArrayList<MessageDataModel> messages);
    }

  }
}
