package com.votafore.earthporn;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;


import com.votafore.earthporn.activities.ActivityFullImage;
import com.votafore.earthporn.activities.ActivityGallery;
import com.votafore.earthporn.utils.RVAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity {

    private RecyclerView imageList;

    Button btn_getTop;
    Button btn_getNew;

    private int spanCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final App app = (App) getApplication();

        btn_getNew = findViewById(R.id.get_new_images);
        btn_getNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForNewImages();
            }
        });

        btn_getTop = findViewById(R.id.get_top_images);
        btn_getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForTopImages();
            }
        });

        imageList = findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setAdapter(app.getAdapter());

        app.getAdapter().setListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onClick(View item, int position) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");

                Intent openFullImg = new Intent(ActivityMain.this, ActivityFullImage.class);
                openFullImg.putExtra("imgIndex", position);

                startActivity(openFullImg, options.toBundle());
            }

            @Override
            public void onLongClick(View item, int position) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");

                Intent openFullImg = new Intent(ActivityMain.this, ActivityGallery.class);
                openFullImg.putExtra("imgIndex", position);

                startActivity(openFullImg, options.toBundle());
            }
        });


        ActivityCompat.setExitSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                if (ActivityGallery.selectedIndex < 0) {
                    // When transitioning out, use the view already specified in makeSceneTransition
                    return;
                }

                // When transitioning back in, use the thumbnail at index the user had swiped to in the pager activity
                sharedElements.put(names.get(0), ((RVAdapter)imageList.getAdapter()).getViewAtIndex(ActivityGallery.selectedIndex));
                ActivityGallery.selectedIndex = -1;
            }
        });
    }
}
