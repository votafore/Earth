package com.votafore.earthporn.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.ObserverListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

public class FragmentRx extends Fragment {

    public static FragmentRx newInstance() {return new FragmentRx();}

    @BindView(R.id.current_data) TextView currentData;
    @BindView(R.id.list_observers) RecyclerView observersList;

    private ObserverListAdapter adapter;
    private Subject<String> currentSubject;

    private View.OnClickListener addSubjectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (currentSubject != null)
                currentSubject.onComplete();

            adapter.clearList();

            currentData.setText("");

            switch (v.getId()){
                case R.id.subject_publish:
                    currentSubject = PublishSubject.create();
                    break;
                case R.id.subject_behaviour:
                    currentSubject = BehaviorSubject.create();
                    break;
                case R.id.subject_replay:
                    currentSubject = ReplaySubject.create();
                    break;
                case R.id.subject_asynch:
                    currentSubject = AsyncSubject.create();
                    break;
            }
        }
    };

    private View.OnClickListener addDataListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (currentSubject == null)
                return;

            String value = "";

            switch (v.getId()){
                case R.id.add_1:
                    value = "1";
                    break;
                case R.id.add_2:
                    value = "2";
                    break;
                case R.id.add_3:
                    value = "3";
                    break;
                case R.id.add_4:
                    value = "4";
                    break;
            }

            currentData.setText(currentData.getText() + value);

            if (currentSubject.hasObservers())
                currentSubject.onNext(value);
        }
    };

    private View.OnClickListener addObserverListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (currentSubject == null) {
                Toast.makeText(getContext(), "установите subject", Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.addObserver(currentSubject);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rx, container, false);
        ButterKnife.bind(this, root);

        adapter = new ObserverListAdapter();

        observersList.setHasFixedSize(true);
        observersList.setLayoutManager(new LinearLayoutManager(getContext()));
        observersList.setItemAnimator(new DefaultItemAnimator());
        observersList.setAdapter(adapter);

        root.findViewById(R.id.subject_publish).setOnClickListener(addSubjectListener);
        root.findViewById(R.id.subject_behaviour).setOnClickListener(addSubjectListener);
        root.findViewById(R.id.subject_replay).setOnClickListener(addSubjectListener);
        root.findViewById(R.id.subject_asynch).setOnClickListener(addSubjectListener);

        root.findViewById(R.id.add_1).setOnClickListener(addDataListener);
        root.findViewById(R.id.add_2).setOnClickListener(addDataListener);
        root.findViewById(R.id.add_3).setOnClickListener(addDataListener);
        root.findViewById(R.id.add_4).setOnClickListener(addDataListener);

        root.findViewById(R.id.add_observer).setOnClickListener(addObserverListener);

        return root;
    }
}