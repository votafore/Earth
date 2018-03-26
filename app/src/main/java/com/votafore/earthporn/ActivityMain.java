package com.votafore.earthporn;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.votafore.earthporn.fragments.FragmentGallery;
import com.votafore.earthporn.fragments.FragmentList;
import com.votafore.earthporn.models.ImageItem;
import com.votafore.earthporn.utils.DataSet;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends AppCompatActivity {

    @BindView(R.id.pages)    FrameLayout pages;
    @BindView(R.id.drawer)   DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView nav_view;

    public static int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar;
        ActionBarDrawerToggle toggle;

        toolbar  = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0){

            private ImageView headImg   = nav_view.getHeaderView(0).findViewById(R.id.header_img);
            private DataSet   dataSet   = DataSet.getInstance();
            private Random    generator = new Random(System.currentTimeMillis());

            private boolean isLoadingAllowed = true;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                pages.setTranslationX(80 * slideOffset);

                if (!isLoadingAllowed || dataSet.getList().size() == 0){
                    return;
                }

                int listSize = dataSet.getList().size();

                dataSet.getList().get(generator.nextInt(listSize)).setImageToImageView(ActivityMain.this, new WeakReference<>(headImg));

                isLoadingAllowed = false;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                headImg.setImageBitmap(null);
                isLoadingAllowed = true;
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setCheckedItem(R.id.item_main);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawer.closeDrawers();
                item.setChecked(true);

                switch (item.getItemId()){
                    case R.id.item_gallery:

                        FragmentList fragmentList;

                        try {
                            fragmentList = (FragmentList) getSupportFragmentManager().findFragmentById(R.id.pages);
                        }catch (ClassCastException e) {
                            // it seems that menu item selected twice (or more)
                            return false;
                        }

                        RecyclerView rv_view = fragmentList.getView().findViewById(R.id.image_list);
                        GridLayoutManager layoutManager = (GridLayoutManager) rv_view.getLayoutManager();

                        ActivityMain.selectedIndex = layoutManager.findFirstCompletelyVisibleItemPosition();

                        View itemView = layoutManager.findViewByPosition(ActivityMain.selectedIndex).findViewById(R.id.img);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pages, FragmentGallery.newInstance())
                                .setReorderingAllowed(true)
                                .addSharedElement(itemView, ViewCompat.getTransitionName(itemView))
                                .commit();

                        return true;

                    case R.id.item_main:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pages, FragmentList.newInstance())
                                .commit();

                        return true;

                    default:
                        return false;
                }
            }
        });

        FragmentManager mFragmentManager = getSupportFragmentManager();

        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.pages);

        if (currentFragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.pages, FragmentList.newInstance())
                    .commit();

        }
    }
}
