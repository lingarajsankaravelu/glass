package lingaraj.hourglass.in.glass.smshomescreen;

import android.support.annotation.Nullable;
import java.io.Serializable;

public class MessageDataModel implements Serializable {
  private boolean heading;
 @Nullable private String headingText;
 @Nullable private ShortMessage message;


  public MessageDataModel(@Nullable Boolean isHeading, @Nullable String headingText,@Nullable ShortMessage message) {
    this.heading = isHeading==null?false:isHeading;
    this.headingText = headingText;
    this.message = message;
  }

  public boolean isHeading() {
    return heading;
  }

  @Nullable public String getHeadingText() {
    return headingText;
  }

  @Nullable public ShortMessage getMessage() {
    return message;
  }
}
