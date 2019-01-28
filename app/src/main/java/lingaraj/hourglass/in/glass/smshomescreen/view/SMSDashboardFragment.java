package lingaraj.hourglass.in.glass.smshomescreen.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import javax.inject.Inject;
import lingaraj.hourglass.in.glass.R;
import lingaraj.hourglass.in.glass.contracts.SMSDashboardContracts;
import lingaraj.hourglass.in.glass.databinding.FragmentInboxBinding;
import lingaraj.hourglass.in.glass.injection.components.DaggerSMSDashboardComponent;
import lingaraj.hourglass.in.glass.injection.modules.SMSDashboardModule;
import lingaraj.hourglass.in.glass.smshomescreen.MessageDataModel;
import lingaraj.hourglass.in.glass.smshomescreen.adapters.InboxAdapter;
import lingaraj.hourglass.in.glass.smshomescreen.presenter.SMSDashBoardPresenter;
import lingaraj.hourglass.in.glass.smshomescreen.tasks.ReadMobileSMSAsyncTask;

public class SMSDashboardFragment extends Fragment implements SMSDashboardContracts.View,
    View.OnClickListener {

  private final String TAG = "SMSHOMEFRAGMENT";
  private Activity mActivity;
  private FragmentInboxBinding binding;
  private InboxAdapter mAdapter;
  @Inject SMSDashBoardPresenter presenter;

  private AsyncTask<Void,Void,ArrayList<MessageDataModel>> read_sms_task;
  private ContentResolver content_resolver;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.mActivity = (Activity) context;
    this.content_resolver = this.mActivity.getContentResolver();
    DaggerSMSDashboardComponent.builder().sMSDashboardModule(new SMSDashboardModule(this)).build().inject(this);


  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inbox,container,false);
    binding.progressContainer.retry.setOnClickListener(this);
     return binding.getRoot();

  }


  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    presenter.getMessages();
  }

  @Override public void showLoader() {
    binding.progressContainer.progressBar.setVisibility(View.VISIBLE);
    binding.progressContainer.errorMesageView.setVisibility(View.VISIBLE);
    binding.progressContainer.errorView.setVisibility(View.GONE);
    binding.progressContainer.retry.setVisibility(View.GONE);
    binding.progressContainer.errorMesageView.setText(this.getString(R.string.common_loading_message));
    if (binding.viewSwitcher.getDisplayedChild()==1){
      binding.viewSwitcher.showPrevious();
    }
    Log.d(TAG,"Showing Loader");

  }

  @Override public void showError() {
    binding.progressContainer.progressBar.setVisibility(View.GONE);
    binding.progressContainer.errorMesageView.setVisibility(View.VISIBLE);
    binding.progressContainer.errorView.setVisibility(View.GONE);
    binding.progressContainer.retry.setVisibility(View.VISIBLE);
    binding.progressContainer.retry.setText(this.getString(R.string.retry));
    binding.progressContainer.errorMesageView.setText(this.getString(R.string.error_retrieving_messages));
    if (binding.viewSwitcher.getDisplayedChild()==1){
      binding.viewSwitcher.showPrevious();
    }
    Log.d(TAG,"Showing Loader");


  }

  @Override public void noMessagesToDisplay() {
    binding.progressContainer.progressBar.setVisibility(View.GONE);
    binding.progressContainer.errorMesageView.setVisibility(View.VISIBLE);
    binding.progressContainer.errorView.setVisibility(View.GONE);
    binding.progressContainer.retry.setVisibility(View.GONE);
    binding.progressContainer.errorMesageView.setText(this.getString(R.string.no_messages));
    if (binding.viewSwitcher.getDisplayedChild()==1){
      binding.viewSwitcher.showPrevious();
    }


  }

  @Override public void setMessages(ArrayList<MessageDataModel> messages) {
    if (mAdapter==null){
      mAdapter = new InboxAdapter(mActivity);
      binding.messages.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
      binding.messages.setHasFixedSize(false);
      binding.messages.setNestedScrollingEnabled(false);
      binding.messages.setAdapter(mAdapter);
      }
      mAdapter.setData(messages);
     if (binding.viewSwitcher.getDisplayedChild()==0){
       binding.viewSwitcher.showNext();
     }


  }

  @Override public void startFetchSMSAsyncTask(SMSDashboardContracts.Presenter.SmsContentProviderAccessCallbacks callbacks) {
     read_sms_task = new ReadMobileSMSAsyncTask(callbacks,content_resolver).execute();
     }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.retry:
        presenter.getMessages();break;

        default: break;

    }

  }

  @Override public void onPause() {
    super.onPause();
    if (read_sms_task!=null && read_sms_task.getStatus()==AsyncTask.Status.RUNNING){
      read_sms_task.cancel(true);
      showError();
    }
  }
}
