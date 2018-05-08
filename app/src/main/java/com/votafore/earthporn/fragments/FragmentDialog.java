package com.votafore.earthporn.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.votafore.earthporn.R;

import org.w3c.dom.Text;

public class FragmentDialog extends Fragment {

    public static FragmentDialog newInstance() {
        FragmentDialog fragment = new FragmentDialog();
        return fragment;
    }

    private Toast customToast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dialog, container, false);

        https://stackoverflow.com/questions/11288475/custom-toast-on-android-a-simple-example
        customToast = Toast.makeText(getActivity(), "hello )", Toast.LENGTH_SHORT);

        TextView toastText = customToast.getView().findViewById(android.R.id.message);
        toastText.setTextSize(25);
        toastText.setTextColor(Color.GREEN);
        toastText.setGravity(Gravity.CENTER_HORIZONTAL);
        toastText.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.ic_dialog_email, 0, 0, 0);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setView(R.layout.test_dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customToast.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customToast.show();
                    }
                });

        Button test = root.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        return root;
    }
}
