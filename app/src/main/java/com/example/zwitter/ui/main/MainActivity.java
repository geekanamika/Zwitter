package com.example.zwitter.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.zwitter.R;
import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.ui.login.LoginActivity;
import com.example.zwitter.ui.main.post.PostActivity;
import com.example.zwitter.ui.profile.view.ViewProfileActivity;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDataManger dataManger;
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManger = InjectorUtils.provideRepository();
        if (!dataManger.isSignedIn()) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        // instantiate variables
        init();

        // setUp Tabs and Viewpager
        setUpViewPager();
    }

    /**
     * Create adapter & set it to viewpager & tabLayout
     */
    private void setUpViewPager() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

        mainTabLayout.setupWithViewPager(mainViewPager);
        mainViewPager.setAdapter(mainViewPagerAdapter);

        int[] tabIcons = {
                R.drawable.ic_action_home_disable,
                R.drawable.ic_action_message_disable
        };
        mainTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mainTabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    /**
     * instantiate variables
     */
    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainTabLayout = findViewById(R.id.main_tabs);
        mainViewPager = findViewById(R.id.main_view_pager);
        FloatingActionButton zweetFab = findViewById(R.id.fab_zweet);
        zweetFab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_profile : startViewProfile();
                return true;
            case R.id.action_log_out : logOut();
        }
        return false;
    }

    /**
     * Sign out from firebase & configure & sign out from Google & start log-in screen
     */
    private void logOut() {
        // Firebase sign out
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI();
                    }
                });
    }

    /**
     * start log-in screen once logged out
     */
    private void updateUI() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * View user's profile in ViewProfileActivity
     */
    private void startViewProfile() {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra(Constants.INTENT_TAG, dataManger.getUserId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.fab_zweet : startPostActivity();
        }
    }

    /**
     * start post activity, clear tasks
     */
    private void startPostActivity() {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(Constants.INTENT_TAG, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}