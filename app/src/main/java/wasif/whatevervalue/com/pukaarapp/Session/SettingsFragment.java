package wasif.whatevervalue.com.pukaarapp.Session;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wasif.whatevervalue.com.pukaarapp.R;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Log.d(TAG, "onCreateView: started.");

        return view;
    }
}
