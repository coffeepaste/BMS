package com.example.bms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bms.R;
import com.example.bms.fragment.OccupancyByTVFragment;
import com.example.bms.fragment.OccupancyDetailFragment;
import com.example.bms.fragment.OccupancyIndustryFragment;
import com.example.bms.fragment.SummaryFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout mdrawerLayout;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle toggle;
    private String token;

    private String title = "";

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        //token = getIntent().getStringExtra("token");
        token = getSharedPreferences("TOKEN", 0)
                .getString("x", "");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        */

        mNavigation = findViewById(R.id.navigation_view);
        mdrawerLayout = findViewById(R.id.drawer_layout);

        mNavigation.setNavigationItemSelectedListener(this);

        navigationDrawer();

        //main layout
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SummaryFragment())
                    .commit();
            title = ("Summary 4 TV");
            mNavigation.setCheckedItem(R.id.menu_summary);
            //end main layout

        }
    }

    //navigation drawer
    private void navigationDrawer() {

        toggle = new ActionBarDrawerToggle(HomeActivity.this, mdrawerLayout, toolbar,
                R.string.open, R.string.close);
        mdrawerLayout.setDrawerListener(toggle);
        //toolbar.setNavigationIcon(R.drawable.ic_account); //custome icon navigation
        toggle.syncState(); //use navigation icon burger
    }

    //implement method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = (item.getItemId());

        switch (id) {
            case R.id.menu_summary:
                title = ("Summary 4 TV");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SummaryFragment())
                        .commit();
                break;

            case R.id.menu_occupancyByTv:
                title = ("Occupancy By TV");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyByTVFragment())
                        .commit();
                break;

            case R.id.menu_occupancyDetail:
                title = ("Occupancy Detail");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyDetailFragment())
                        .commit();
                break;

            case R.id.menu_occupancy_industry:
                title = ("Occupancy Industry");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyIndustryFragment())
                        .commit();
                break;

            case R.id.menu_logout:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

                getSharedPreferences("TOKEN", 0)
                        .edit()
                        .clear()
                        .apply();
        }

        // open or close the drawer if home button is pressed
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //close navDrawer
    @Override
    public void onBackPressed() {

        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //close navDrawer
}
