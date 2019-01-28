package lingaraj.hourglass.in.glass.recievers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import lingaraj.hourglass.in.glass.R;
import lingaraj.hourglass.in.glass.smshomescreen.view.HomeActivity;

public class SMSReceiver extends BroadcastReceiver {


  private String TAG = "SMSRECEIVER";
  private Bundle bundle;
  private SmsMessage currentSMS;
  private int mNotificationId = 101;

  @Override
  public void onReceive(Context context, Intent intent) {

    if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {



        bundle = intent.getExtras();
        if (bundle != null) {
          Object[] pdu_Objects = (Object[]) bundle.get("pdus");
          if (pdu_Objects != null) {

            for (Object aObject : pdu_Objects) {

              currentSMS = getIncomingMessage(aObject, bundle);

              String senderNo = currentSMS.getDisplayOriginatingAddress();
              String message = currentSMS.getDisplayMessageBody();

              Log.d(TAG, "senderNum: " + senderNo + " :\n message: " + message);

              issueNotification(context, senderNo, message);
            }
            this.abortBroadcast();
          }
        }

      }
  }


  private void issueNotification(Context context, String senderNo, String message) {

    Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
        R.mipmap.ic_launcher);

    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(context)
            .setLargeIcon(icon)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(senderNo)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentText(message);

    Intent resultIntent = new Intent(context, HomeActivity.class);
    resultIntent.putExtra(HomeActivity.Keys.Sender,senderNo);
    PendingIntent resultPendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );

    mBuilder.setContentIntent(resultPendingIntent);

    try {
      NotificationManager mNotifyMgr =
          (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    catch (Exception e){
      Log.d(TAG,e.toString());
      e.printStackTrace();
    }


  }

  private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
    SmsMessage currentSMS;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      String format = bundle.getString("format");
      currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
    } else {
      currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
    }
    return currentSMS;
  }
}