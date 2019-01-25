package lingaraj.hourglass.in.glass.contracts;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;

public interface SMSDashboardContracts {

  public interface View extends BaseView {

    void noMessagesToDisplay();

    void setMessages(ArrayList<MessageDataModel> messages);
  }

  public interface Presenter {


    public interface SmsContentProviderAccessCallbacks {
      void onComplete(@Nullable ArrayList<MessageDataModel> messages);
    }

  }
}
