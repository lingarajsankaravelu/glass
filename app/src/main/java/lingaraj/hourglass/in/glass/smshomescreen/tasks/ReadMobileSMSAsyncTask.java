package lingaraj.hourglass.in.glass.smshomescreen.tasks;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lingaraj.hourglass.in.glass.BuildConfig;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;
import lingaraj.hourglass.in.glass.smshomescreen.ShortMessage;

public class ReadMobileSMSAsyncTask extends AsyncTask<Void,Void,ArrayList<MessageDataModel>>{

  private final String TAG = "ReadMobileSMSAsync";
  private SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks callbacks;
  private ContentResolver content_resolver;
  public ReadMobileSMSAsyncTask(SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks contentProviderAccessCallbacks,ContentResolver contentResolver){
    this.callbacks = contentProviderAccessCallbacks;
    this.content_resolver = contentResolver;
  }

  @Override protected ArrayList<MessageDataModel> doInBackground(Void... voids) {

    String [] columns = {"address","date","body","person"};
    int hours[] = {1,2,3,6,12,24};
    int length = hours.length;
    ArrayList<MessageDataModel> messages = new ArrayList<>();
    try {
      for (int index = 0; index <length; index++) {
        int hour = hours[index];
        int last_processed = index==0?0:hours[index-1];
        Cursor cursor = content_resolver.query(Telephony.Sms.CONTENT_URI,columns,query(hour,last_processed),null,null);
        parseCursor(cursor,messages,hour);
        if (BuildConfig.DEBUG){
          Log.d(TAG,"Data:\n"+new Gson().toJson(messages));
        }
      }


    }
    catch (Exception e){
        messages = null;
        if (BuildConfig.DEBUG){
          e.printStackTrace();
        }
    }

    return messages;

  }


  @Override protected void onPostExecute(@Nullable ArrayList<MessageDataModel> messageDataModels) {
    super.onPostExecute(messageDataModels);
      this.callbacks.onMessageRetrievalCompletion(messageDataModels);

  }

  /**
   *
   * @param cursor   - cursor obtained from query
   * @param messages - variable to hold the read data
   * @param hour - difference in hours
   */
  private void parseCursor(@Nullable Cursor cursor, ArrayList<MessageDataModel> messages,int hour) {
    String heading = "Recieved in last "+String.valueOf(hour)+"hour";
    ArrayList<MessageDataModel> data = new ArrayList<MessageDataModel>();
    if (cursor!=null){
      int count = cursor.getCount();
      Log.d(TAG,"Cursor Count:"+count);
      if (count>0){
        cursor.moveToFirst();
        do {
          long date_long = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
          String date = convertLongToDate(date_long);
          String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
          String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
          ShortMessage message = new ShortMessage(date,address,body);
          MessageDataModel messageDataModel = new MessageDataModel(false,null,message);
          data.add(messageDataModel);
        }
        while (cursor.moveToNext());
        cursor.close();
      }
      if (data.size()>0){
        MessageDataModel messageDataModel = new MessageDataModel(true,heading,null);
        messages.add(messageDataModel);
        messages.addAll(data);
      }
    }



  }


  private long convertDateToLong(String dbDate) {
    long date_to_long = 0;
    try {
      // DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat date_formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // date_formate.setTimeZone(TimeZone.getTimeZone("IST"));
      Date date = date_formate.parse(dbDate);
      date_to_long = date.getTime();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }


    return date_to_long;
  }

  /**
   *
   * @param value - takes long value of date and time
   *
   */
  private String convertLongToDate(long value) {
    Date date=new Date(value);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //sdf.setTimeZone(TimeZone.getTimeZone("IST"));
    return sdf.format(date);
  }

  /**
   *
   * @param hour           -
   * @param lastProcessed
   * @return
   */
  private String query(int hour, int lastProcessed){
    String start_date = getDate(lastProcessed);
    String end_date = getDate(hour);
    Log.d(TAG,"Start Date:"+start_date);
    Log.d(TAG,"End Date:"+end_date);
    String whereAddress = "address = ?";
    String whereDate = "date >=" + convertDateToLong(end_date) +
        " AND date <" + convertDateToLong(start_date);
    String where = DatabaseUtils.concatenateWhere(whereAddress, whereDate);
    Log.d(TAG,"query:"+whereDate);
    return whereDate;
  }

  private String getDate(int minusHour){
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR_OF_DAY,-minusHour);
    date = calendar.getTime();
    return dateFormat.format(date);

  };



}
