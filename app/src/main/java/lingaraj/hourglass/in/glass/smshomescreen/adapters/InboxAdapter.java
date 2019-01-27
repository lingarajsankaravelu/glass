package lingaraj.hourglass.in.glass.smshomescreen.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import lingaraj.hourglass.in.glass.R;
import lingaraj.hourglass.in.glass.databinding.SingleSmsSmallLayoutBinding;
import lingaraj.hourglass.in.glass.databinding.SmsCategoryLayoutBinding;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;
import lingaraj.hourglass.in.glass.smshomescreen.ShortMessage;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

  private final int TYPE_SMS = 0;
  private final int TYPE_Heading = 1;
  private Context mContext;
  private ArrayList<MessageDataModel> messages = new ArrayList<>();

  public InboxAdapter(Context context){
    this.mContext = context;
  }


  @NonNull @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(this.mContext);
    if (viewType==TYPE_Heading){
      SmsCategoryLayoutBinding category_binding = DataBindingUtil.inflate(inflater,R.layout.sms_category_layout,parent,false);
      return new ViewHolder(category_binding);
    }
    else {
      SingleSmsSmallLayoutBinding sms_layout_binding = DataBindingUtil.inflate(inflater, R.layout.single_sms_small_layout,parent,false);
      return new ViewHolder(sms_layout_binding);
    }
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     MessageDataModel record = messages.get(position);
     if (record.isHeading()){
       holder.catgory_binding.categoryText.setText(record.getHeadingText());
     }
     else {
       ShortMessage message = record.getMessage();
       if (message!=null){
         holder.sms_binding.smsSender.setText(message.getAddress());
         holder.sms_binding.smsContent.setText(message.getBody());
         holder.sms_binding.time.setText(message.getDate());

       }
     }
  }

  @Override public int getItemCount() {
    return this.messages.size();
  }

  @Override public int getItemViewType(int position) {
    if (messages.get(position).isHeading()){
      return TYPE_Heading;

    }
    else {
      return TYPE_SMS;
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    SingleSmsSmallLayoutBinding sms_binding;
    SmsCategoryLayoutBinding catgory_binding;

    public ViewHolder(SingleSmsSmallLayoutBinding binding) {
      super(binding.getRoot());
      sms_binding = binding;
    }

    public ViewHolder(SmsCategoryLayoutBinding binding) {
      super(binding.getRoot());
      catgory_binding = binding;
    }
  }

  public void setData(@NonNull ArrayList<MessageDataModel> data){
    this.messages = data;
    notifyDataSetChanged();
  }
}
