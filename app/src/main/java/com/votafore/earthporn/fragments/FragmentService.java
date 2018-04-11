package com.votafore.earthporn.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.DataLoader;
import com.votafore.earthporn.utils.LoadService;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentService extends Fragment {

    private ResultReceiver receiver;
    private ProgressBar progressBar;

    public static FragmentService newInstance(){
        return new FragmentService();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new ResultReceiver(new Handler()){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {

                if(resultData.containsKey(DataLoader.KEY_MAXVALUE)){
                    progressBar.setMax(resultData.getInt(DataLoader.KEY_MAXVALUE));
                }

                if(resultData.containsKey(DataLoader.KEY_PROGRESS)){
                    progressBar.setProgress(resultData.getInt(DataLoader.KEY_PROGRESS));
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_service, container, false);

        Button btnStartService = root.findViewById(R.id.btn_start_service);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToService(true);
            }
        });

        progressBar = root.findViewById(R.id.progressBar);

        connectToService(false);

        return root;
    }

    private void connectToService(boolean startService){
        Intent start = new Intent(getActivity(), LoadService.class);
        start.putExtra(LoadService.KEY_RECEIVER, receiver);
        start.putExtra(LoadService.KEY_STARTLOADING, startService);
        getActivity().startService(start);
    }

}
