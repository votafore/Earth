package com.votafore.earthporn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.votafore.earthporn.fragments.FragmentList;


/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager mFragmentManager = getSupportFragmentManager();

        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.pages);

        if (currentFragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.pages, FragmentList.newInstance())
                    .commit();

        }
    }
}
