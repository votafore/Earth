package com.votafore.earthporn.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.votafore.earthporn.R;


public class FragmentBroadcast extends Fragment {

    private BroadcastReceiver orderedReceiver1;
    private BroadcastReceiver orderedReceiver2;
    private BroadcastReceiver receiver1;
    private BroadcastReceiver receiver2;


    private TextView result1;
    private TextView result2;

    private Switch switcher;

    private String ACTION = "com.votafore.earthporn.testbroadcast";

    private String KEY_DATA = "data";
    private String DATA     = "test string";

    public static FragmentBroadcast newInstance(){
        return new FragmentBroadcast();
    }

    @Override
    public void onStart() {
        super.onStart();

        orderedReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle args = getResultExtras(true);

                String result = intent.getStringExtra(KEY_DATA);

                result1.setText(result);

                if (switcher.isChecked()){
                    args.putString(KEY_DATA, null);
                } else {
                    args.putString(KEY_DATA, result);
                }

                setResultExtras(args);
            }
        };

        IntentFilter filter1 = new IntentFilter(ACTION);
        filter1.setPriority(5);

        getContext().registerReceiver(orderedReceiver1, filter1);

        orderedReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle args = getResultExtras(true);

                result2.setText(args.getString(KEY_DATA, ""));
            }
        };

        IntentFilter filter2 = new IntentFilter(ACTION);
        filter2.setPriority(0);

        getContext().registerReceiver(orderedReceiver2, filter2);




        IntentFilter filter = new IntentFilter(ACTION);

        receiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result1.setText(intent.getStringExtra(KEY_DATA));
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver1, filter);

        receiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                result2.setText(intent.getStringExtra(KEY_DATA));
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver2, filter);
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().unregisterReceiver(orderedReceiver1);
        getActivity().unregisterReceiver(orderedReceiver2);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver1);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_broadcast, container, false);

        result1 = root.findViewById(R.id.result1);
        result2 = root.findViewById(R.id.result2);

        switcher = root.findViewById(R.id.switcher);

        Button sendBroadcast        = root.findViewById(R.id.send_broadcast);
        Button sendOrderedBroadcast = root.findViewById(R.id.send_ordered_broadcast);

        sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result1.setText("");
                result2.setText("");

                Intent testIntent = new Intent();

                testIntent.setAction(ACTION);
                testIntent.putExtra(KEY_DATA, DATA);

                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(testIntent);
            }
        });

        sendOrderedBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result1.setText("");
                result2.setText("");

                Intent testIntent = new Intent();

                testIntent.setAction(ACTION);
                testIntent.putExtra(KEY_DATA, DATA);

                getContext().sendOrderedBroadcast(testIntent, null);
            }
        });

        return root;
    }

}
