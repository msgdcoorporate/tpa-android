package com.michelle.goldwin.tpamobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.michelle.goldwin.tpamobile.R;
import com.michelle.goldwin.tpamobile.chatinstructor.ChatInstructorFragment;
import com.michelle.goldwin.tpamobile.googlemaps.GoogleMapsFragment;
import com.michelle.goldwin.tpamobile.todolist.TodoListFragment;
import com.michelle.goldwin.tpamobile.viewpager.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView lblUserFullname;
    private TextView lblUserEmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* BEGIN INTIIALIZE */
        tabLayout       = (TabLayout) findViewById(R.id.tabLayout);
        viewPager       = (ViewPager) findViewById(R.id.viewPager);

        /**
         * There are lblUserFullname and lblUserEmail must be inflated from nav_header_home.xml
         * Because there are in different layout .xml
         */
        View viewNavHeaderHome = LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_header_home,null);
        lblUserFullname = (TextView) viewNavHeaderHome.findViewById(R.id.lblUserFullname);
        lblUserEmail    = (TextView) viewNavHeaderHome.findViewById(R.id.lblUserEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        Toast.makeText(this, lblUserEmail.getText().toString(), Toast.LENGTH_SHORT).show();


        /* END INITIALIZE */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* CALL `ViewPagerAdapter` */
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TodoListFragment(),"Missions");
        viewPagerAdapter.addFragment(new GoogleMapsFragment(),"Gym Location");
        viewPagerAdapter.addFragment(new ChatInstructorFragment(),"Instructor");
        /* END CALL */


        lblUserEmail.setText("TEST");
        /* COMBINE */
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_insert_food) {
            // Handle the camera action
        } else if (id == R.id.nav_calorie_history) {

        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}