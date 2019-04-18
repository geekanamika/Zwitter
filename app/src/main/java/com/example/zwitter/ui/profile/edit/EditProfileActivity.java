package com.example.zwitter.ui.profile.edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zwitter.R;
import com.example.zwitter.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etFullUserName;
    private TextInputEditText etBio;
    private EditProfileViewModel editProfileViewModel;
    private String bio;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getIntentData();

        init();
    }

    private void init() {
        etFullUserName = findViewById(R.id.et_user_name);
        TextInputEditText etEmail = findViewById(R.id.et_user_email);
        etBio = findViewById(R.id.et_user_bio);
        Button btSave = findViewById(R.id.action_save_profile);
        Button btCancel = findViewById(R.id.action_cancel_profile);
        RoundedImageView avatarView = findViewById(R.id.edit_avatar);

        // Todo set bio too taking from user class
        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
        etFullUserName.setText(name);
        etEmail.setText(editProfileViewModel.getUserEmail());
        etBio.setText(bio);
        Picasso.get().load(editProfileViewModel.getProfilePicture()).into(avatarView);

        btCancel.setOnClickListener(this);
        btSave.setOnClickListener(this);
    }


    /**
     * checks how post activity was started
     */
    private void getIntentData () {
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                bio = data.getString(Constants.INTENT_BIO_TAG);
                name = data.getString(Constants.INTENT_NAME_TAG);
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
            case R.id.action_save_profile : validateDataAndPost();
                break;
            case R.id.action_cancel_profile : finish();
                break;
        }
    }

    private void validateDataAndPost() {
        if (TextUtils.isEmpty(etFullUserName.getText()) || TextUtils.isEmpty(etBio.getText())) {
            Toast.makeText(this, "Please add message!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etFullUserName.getText() != null && etBio.getText() != null) {
            editProfileViewModel.updateUserDetails(etFullUserName.getText().toString(), etBio.getText().toString());
            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
