package com.votafore.earthporn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.votafore.earthporn.utils.RVAdapter;
import com.votafore.earthporn.views.IImageListActivity;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity { //implements IImageListActivity{

    private RecyclerView imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final App app = (App) getApplication();

        imageList = findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageList.setAdapter(app.getAdapter());

        Button btn_getNew = findViewById(R.id.get_new_images);
        btn_getNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForNewImages();
            }
        });

        Button btn_getTop = findViewById(R.id.get_top_images);
        btn_getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForTopImages();
            }
        });
    }


//    @Override
//    public void bindImageListToAdapter(RVAdapter adapter) {
//        imageList.setAdapter(adapter);
//    }
}
