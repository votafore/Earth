package com.votafore.earthporn.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.LoadService;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentService extends Fragment {


    public static FragmentService newInstance(){
        return new FragmentService();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_service, container, false);

        Button btnStartService = root.findViewById(R.id.btn_start_service);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), LoadService.class);
                getActivity().startService(start);
            }
        });

        Button btnStopService = root.findViewById(R.id.btn_stop_service);

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stop = new Intent(getActivity(), LoadService.class);
                getActivity().stopService(stop);
            }
        });

        return root;
    }

}
