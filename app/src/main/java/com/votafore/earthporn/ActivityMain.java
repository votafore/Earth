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
import android.support.v7.widget.LinearLayoutManager;
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


public class ActivityMain extends AppCompatActivity {

    public static int selectedIndex = -1;

    private DrawerLayout drawer;
    private NavigationView nav_view;

    private Fragment fragmentList    = FragmentList.newInstance();
    private Fragment fragmentGallery = FragmentGallery.newInstance();

    private FrameLayout pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;
        ActionBarDrawerToggle toggle;

        pages    = findViewById(R.id.pages);
        drawer   = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        toolbar  = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0){

            private ImageView headImg   = nav_view.getHeaderView(0).findViewById(R.id.header_img);
            private DataSet   dataSet   = DataSet.getInstance();
            private Random    generator = new Random(System.currentTimeMillis());

            private boolean isLoading = false;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                pages.setTranslationX(80 * slideOffset);

                if(slideOffset == 0){
                    isLoading = false;
                    headImg.setImageBitmap(null);
                    return;
                }

                if(isLoading)
                    return;

                List<ImageItem> list = dataSet.getList();

                if (list.size() == 0)
                    return;

                list.get(generator.nextInt(list.size())).setImageToImageView(ActivityMain.this, new WeakReference<>(headImg));

                isLoading = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                headImg.setImageBitmap(null);
                isLoading = false;
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

                        RecyclerView rv_view = (RecyclerView) fragmentList.getView();
                        GridLayoutManager lManager = (GridLayoutManager) rv_view.getLayoutManager();

                        selectedIndex = lManager.findFirstCompletelyVisibleItemPosition();

                        View itemView = lManager.findViewByPosition(selectedIndex);

                        // since it is root element... find ImageView
                        itemView = itemView.findViewById(R.id.img);

                        fragmentList.getChildFragmentManager().beginTransaction()
                                .replace(R.id.pages, fragmentGallery)
                                .setReorderingAllowed(true)
                                .addSharedElement(itemView, ViewCompat.getTransitionName(itemView))
                                .addToBackStack(fragmentGallery.toString())
                                .commit();

                        return true;

                    case R.id.item_main:

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pages, fragmentList)
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
                    .add(R.id.pages, fragmentList)
                    .commit();

        }
    }
}
