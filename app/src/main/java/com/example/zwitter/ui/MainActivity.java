package com.example.zwitter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zwitter.R;
import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.ui.view_profile.ViewProfileActivity;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppDataManger dataManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManger = InjectorUtils.provideRepository(this);
        if (!dataManger.isSignedIn()) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.view_profile : startViewProfile();
                return true;
        }
        return false;
    }

    private void startViewProfile() {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra(Constants.INTENT_TAG, dataManger.getUserEmail());
        startActivity(intent);
    }


}
