package com.iterahub.teratour.ui.activities;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itera.teratour.view.UnityLaunch;
import com.iterahub.teratour.DummyActivity;
import com.iterahub.teratour.R;
import com.iterahub.teratour.models.SlackMessage;
import com.iterahub.teratour.services.SendToSlackService;
import com.iterahub.teratour.ui.fragments.HomeFragment;
import com.iterahub.teratour.ui.fragments.InboxFragment;
import com.iterahub.teratour.ui.fragments.SearchFragment;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.utils.ShowUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import com.itera.teratour.view.UnityPlayerActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SearchManager.OnDismissListener{

    private ImageView profileImage;
    private TextView profileName;
    private DrawerLayout drawer;
    PrefUtils prefUtils;
    private AppViewModel appViewModel;


    @BindView(R.id.starting_ar)
    ProgressBar progressBar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager Tab_ViewPager;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();

        //startActivity(new Intent(this, UnityPlayerActivity.class));

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        prefUtils = new PrefUtils(this);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View HeaderLayout = navigationView.getHeaderView(0);

        profileImage = HeaderLayout.findViewById(R.id.profile_image_nav_bar);
        profileName = HeaderLayout.findViewById(R.id.profile_name_nav_bar);
        drawer = findViewById(R.id.drawer_layout);

        profileName.setOnClickListener(view -> {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this,UserProfileActivity.class));
        });
        profileImage.setOnClickListener(view -> {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(this,UserProfileActivity.class));
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //get data of loggedIn User
        getUserData(prefUtils.getUserId());


        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.search_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ar_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.inbox_icon));

        if(fragment == null){
            fragment = new HomeFragment();
            ShowUtils.loadFragment(this,fragment,R.id.content_app);

            if(tabLayout != null){
                TabLayout.Tab tab = tabLayout.getTabAt(0);
               if(tab != null){
                   tab.select();
               }
            }
        }

//        tabLayout.setupWithViewPager(Tab_ViewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Tab_ViewPager.setCurrentItem(tab.getPosition());
                switch(tab.getPosition()){
                    case 0: {
                        fragment = new HomeFragment();
                        ShowUtils.loadFragment(MainActivity.this,fragment,R.id.content_app);
                        break;
                    }
                    case 1:{
                        fragment = new SearchFragment();
                        ShowUtils.loadFragment(MainActivity.this,fragment,R.id.content_app);

                        onSearchRequested();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(MainActivity.this, UnityPlayerActivity.class));
                        break;
                    }
                    case 3:{
                        fragment = new InboxFragment();
                        ShowUtils.loadFragment(MainActivity.this,fragment,R.id.content_app);
                        break;
                    }
                    default: {break;}

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1)
                    onSearchRequested();
            }
        });
    }

    private void getUserData(String userId){
        appViewModel.getUserById(userId).observe(this,userModel -> {
            if(userModel != null){
                if(profileImage != null){
                    ShowUtils.setImage(this,profileImage,userModel.getImage_url());
                }
                if(profileName != null){
                    profileName.setText(String.valueOf(userModel.getFirstname() + " " + userModel.getLastname()));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else{
            super.onBackPressed();
        }

    }

    private void sendMessageToSlack(){
        SlackMessage slackMessage = new SlackMessage();
        SlackMessage.Attachment attachment0 = new SlackMessage.Attachment();
        SlackMessage.Attachment attachment1 = new SlackMessage.Attachment();
        SlackMessage.Attachment attachment2 = new SlackMessage.Attachment();

        attachment0.setTitle("From Teratour App");
        attachment1.setTitle("Dummy Title 1");
        attachment1.setText("Dummy Message 1");
        attachment2.setTitle("Dummy Title 2");
        attachment2.setText("Dummy message 2");

        List<SlackMessage.Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment0);
        attachmentList.add(attachment1);
        attachmentList.add(attachment2);

        slackMessage.setAttachments(attachmentList);

        Intent intent = new Intent(this, SendToSlackService.class);
        intent.putExtra(Constants.SLACK_MESSAGE, Parcels.wrap(slackMessage));
        startService(intent);
        Log.e(MainActivity.class.getSimpleName(),"Service started");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_user:{
                startActivity(new Intent(this,UserProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_logout:{
                prefUtils.setLogIn(false);
                startActivity(new Intent(this,LogInActivity.class));
                finish();
            }
        }

        return true;
    }

    @Override
    public void onDismiss() {
//        fragment = new HomeFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_app,fragment,fragment.getClass().getSimpleName())
//                .addToBackStack(null).commit();
    }
}
