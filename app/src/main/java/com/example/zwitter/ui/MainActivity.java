package com.example.zwitter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.R;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AppDataManger dataManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManger = InjectorUtils.provideRepository(this);

        if (!dataManger.isSignedIn()) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginScreen.class));
            finish();
            return;
        } else {
            Log.d(Constants.MY_TAG, dataManger.getProfileName() + dataManger.getProfileDp());
        }
    }
}
