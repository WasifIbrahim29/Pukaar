package wasif.whatevervalue.com.pukaarapp.Session;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.security.Permissions;

import wasif.whatevervalue.com.pukaarapp.R;

public class SessionFragment extends Fragment {

    private static final String TAG = "SessionFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        Log.d(TAG, "onCreateView: started.");

        return view;
    }




}
