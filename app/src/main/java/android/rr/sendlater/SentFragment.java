package android.rr.sendlater;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SentFragment extends Fragment {

    public static SentFragment newInstance() {
        return new SentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sent, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    
}