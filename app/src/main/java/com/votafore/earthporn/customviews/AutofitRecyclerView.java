package com.votafore.earthporn.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * https://stackoverflow.com/questions/29579811/changing-number-of-columns-with-gridlayoutmanager-and-recyclerview
 *
 */

public class AutofitRecyclerView extends RecyclerView {

    private GridLayoutManager manager;
    private int columnWidth = -1;
    private int spanCount = 1;

    private GridLayoutManager.SpanSizeLookup sslForList;
    private GridLayoutManager.SpanSizeLookup sslForGrid;

    private GridLayoutManager.SpanSizeLookup sslCurrent;

    public static int MODE_LIST = 0;
    public static int MODE_GRID = 1;

    private int currentMode = 1;

    public AutofitRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            int[] attrsArray = {android.R.attr.columnWidth};
            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
            columnWidth = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }

        manager = new GridLayoutManager(context, spanCount);

        sslForGrid = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                // structure of group
                // row 1: columnWidth * 2... as many as can be   amount = spanCount
                // row 2: columnWidth... as many as can be       amount = spanCount / 2

                int totalCountForItemsGroup = spanCount + spanCount / 2;

                int indexInGroup = position % totalCountForItemsGroup;

                // if this is a fist row
                if (indexInGroup < spanCount/2){
                    return Math.min(2, spanCount);
                }

                return 1;
            }
        };

        sslForList = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return spanCount;
            }
        };

//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//
//                // structure of group
//                // row 1: columnWidth * 2... as many as can be   amount = spanCount
//                // row 2: columnWidth... as many as can be       amount = spanCount / 2
//
//                int totalCountForItemsGroup = spanCount + spanCount / 2;
//
//                int indexInGroup = position % totalCountForItemsGroup;
//
//                // if this is a fist row
//                if (indexInGroup < spanCount/2){
//                    return Math.min(2, spanCount);
//                }
//
//                return 1;
//            }
//        });

        manager.setSpanSizeLookup(sslForGrid);

        setLayoutManager(manager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (columnWidth > 0) {
            spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
            spanCount *= 2;
            manager.setSpanCount(spanCount);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle args = new Bundle();
        args.putParcelable("state", super.onSaveInstanceState());
        args.putInt("mode", currentMode);

        return args;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle){
            Bundle args = (Bundle) state;
            currentMode = args.getInt("mode", MODE_GRID);
            state = args.getParcelable("state");
        }

        super.onRestoreInstanceState(state);

        setMode();
    }

    private void setMode(){
        sslCurrent = currentMode == MODE_LIST ? sslForList : sslForGrid;
        manager.setSpanSizeLookup(sslCurrent);

        getAdapter().notifyDataSetChanged();
    }

    public int getMode(){
        return currentMode;
    }

    public void changeMode(int newMode){
        currentMode = newMode;
        setMode();
    }
}
