package com.example.zwitter.ui.profile.edit_profile.view_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zwitter.R;
import com.example.zwitter.data.AppDataManger;
import com.example.zwitter.ui.profile.edit_profile.EditProfileActivity;
import com.example.zwitter.utils.Constants;
import com.example.zwitter.utils.InjectorUtils;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEditFollowProfile;
    String userEmail;
    AppDataManger dataManger;
    ViewProfileViewModel viewProfileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dataManger = InjectorUtils.provideRepository(this);
        viewProfileViewModel = ViewModelProviders.of(this).get(ViewProfileViewModel.class);
        btnEditFollowProfile  = findViewById(R.id.followOrEditButton);
        btnEditFollowProfile.setOnClickListener(this);
        checkIfFollowOrEdit();


    }

    private void checkIfFollowOrEdit() {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                userEmail = data.getString(Constants.INTENT_TAG);
                if (userEmail.equals(viewProfileViewModel.getUserEmail())) {
                    btnEditFollowProfile.setText(getString(R.string.label_edit_profile));
                } else {
                    // Todo check if already following or not
                    btnEditFollowProfile.setText(getString(R.string.label_follow));
                }
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.followOrEditButton : if (btnEditFollowProfile.getText().equals(getString(R.string.label_edit_profile))) {
                startEditProfile();
            }
        }
    }

    private void startEditProfile() {
        startActivity(new Intent(this, EditProfileActivity.class));
    }
}
