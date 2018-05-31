package com.votafore.earthporn.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.earthporn.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

public class ObserverListAdapter extends RecyclerView.Adapter<ObserverListAdapter.ViewHolder> {

    private List<Item> observers = new ArrayList<>();

    public void addObserver(Subject<String> subject){
        observers.add(new Item(subject, observers.size()));
        notifyItemInserted(observers.size() - 1);
    }

    public void clearList(){
        while (observers.size() > 0){
            observers.get(0).clear();
            observers.remove(0);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_observer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(observers.get(position));
    }

    @Override
    public int getItemCount() {
        return observers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView currentData;

        public ViewHolder(View itemView) {
            super(itemView);

            currentData = itemView.findViewById(R.id.current_data);
        }

        public void bindData(Item item){
            currentData.setText(item.data);
        }
    }

    private class Item {

        private String data = "";
        private Disposable disposable;

        public Item(Subject<String> subject, final int index){

            disposable = subject.subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    data = data.concat(s);
                    notifyItemChanged(index);
                }
            });
        }

        public void clear(){
            disposable.dispose();
            data = "";
        }
    }
}
