package com.example.zwitter.ui.profile.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;

import com.example.zwitter.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText etFullUserName;
    TextInputEditText etEmail;
    TextInputEditText etBio;
    Button btSave;
    Button btCancel;
    EditProfileViewModel editProfileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
    }

    private void init() {
        etFullUserName = findViewById(R.id.et_user_name);
        etEmail = findViewById(R.id.et_user_email);
        etBio = findViewById(R.id.et_user_bio);
        btSave = findViewById(R.id.action_save_profile);
        btCancel = findViewById(R.id.action_cancel_profile);

        // Todo set bio too taking from user class
        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
        etFullUserName.setText(editProfileViewModel.getFullUserName());
        etEmail.setText(editProfileViewModel.getUserEmail());
    }
}
