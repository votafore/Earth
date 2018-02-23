package com.votafore.earthporn;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.github.piasy.biv.view.BigImageView;
import com.votafore.earthporn.utils.RVAdapter;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity {

    private RecyclerView imageList;

    BigImageView imgView;

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

        imgView = findViewById(R.id.full_image);
        imgView.setVisibility(View.GONE);

        app.getAdapter().setListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onClick(View item, int position) {
                //imgView.showImage(Uri.parse(app.getAdapter().getImageItem(position).item.getUrl()));
                imgView.getSSIV().setImage(ImageSource.bitmap(app.getAdapter().getImageItem(position).image));
                imgView.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (imgView.getVisibility() == View.VISIBLE){
            imgView.setVisibility(View.GONE);
            return;
        }

        super.onBackPressed();
    }
}
