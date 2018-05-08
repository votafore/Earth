package com.votafore.earthporn;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.votafore.earthporn.content.DataProvider;
import com.votafore.earthporn.utils.DataSet;
import com.votafore.earthporn.utils.FragmentRouter;
import com.votafore.earthporn.utils.LoadService;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityMain extends AppCompatActivity {

    @BindView(R.id.pages)    FrameLayout pages;
    @BindView(R.id.drawer)   DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView nav_view;

    public static int selectedIndex = -1;

    private FragmentRouter router;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        preferences = getSharedPreferences(getResources().getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);

        router = new FragmentRouter(getSupportFragmentManager());

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

        NavigationView.OnNavigationItemSelectedListener drawerListener = new NavigationView.OnNavigationItemSelectedListener() {

            private int lastSelectedItem = R.id.item_main;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawer.closeDrawers();

                if (lastSelectedItem == item.getItemId()){
                    return false;
                }

                lastSelectedItem = item.getItemId();

                item.setChecked(true);

                switch (lastSelectedItem){
                    case R.id.item_gallery:

                        router.goToGalleryFragment();
                        return true;

                    case R.id.item_main:

                        router.goToImageListFragment();
                        return true;


                    case R.id.item_database:
                        router.goToDataBaseFragment();
                        return true;

                    case R.id.item_service:
                        router.goToServiceFragment();
                        return true;

                    case R.id.item_broadcast:
                        router.goToBroadcastFragment();
                        return true;

                    default:
                        return false;
                }
            }
        };

        nav_view.setNavigationItemSelectedListener(drawerListener);

        if (getIntent().getBooleanExtra(LoadService.KEY_OPENSERVICEPAGE, false)){
            drawerListener.onNavigationItemSelected(nav_view.getMenu().findItem(R.id.item_service));
        } else {
            router.openImageListFragment();
        }
    }

    public FragmentRouter getRouter(){
        return router;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        int type = preferences.getInt(getResources().getString(R.string.shared_preferences_database_type), DataProvider.DATABASE_TYPE_SQL);

        switch (type) {
            case DataProvider.DATABASE_TYPE_SQL:
                menu.findItem(R.id.item_sql).setChecked(true);
                break;
            case DataProvider.DATABASE_TYPE_REALM:
                menu.findItem(R.id.item_realm).setChecked(true);
                break;
            case DataProvider.DATABASE_TYPE_ROOM:
                menu.findItem(R.id.item_room).setChecked(true);
                break;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()){
            case R.id.item_sql:
                preferences.edit()
                        .putInt(getResources().getString(R.string.shared_preferences_database_type), DataProvider.DATABASE_TYPE_SQL)
                        .apply();
                break;
            case R.id.item_realm:
                preferences.edit()
                        .putInt(getResources().getString(R.string.shared_preferences_database_type), DataProvider.DATABASE_TYPE_REALM)
                        .apply();
                break;
            case R.id.item_room:
                preferences.edit()
                        .putInt(getResources().getString(R.string.shared_preferences_database_type), DataProvider.DATABASE_TYPE_ROOM)
                        .apply();
                break;
        }

        Toast.makeText(this, "application must be restarted", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
