package lingaraj.hourglass.in.glass.smshomescreen.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class SMSDashboardFragment extends Fragment {

  private final String TAG = "SMSHOMEFRAGMENT";
  private Activity mActivity;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.mActivity = (Activity) context;

  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }



}
