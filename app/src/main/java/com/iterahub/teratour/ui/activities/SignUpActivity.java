package com.iterahub.teratour.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.iterahub.teratour.R;

import com.iterahub.teratour.models.UserModel;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    PrefUtils prefUtils;

    @BindView(R.id.signup_button)
    Button SignUpBtn;
    @BindView(R.id.firstname_et)
    EditText FirstNameET;
    @BindView(R.id.lastname_et)
    EditText LastNameET;
    @BindView(R.id.user_et)
    EditText UserNameET;
    @BindView(R.id.email_et)
    EditText EmailET;
    @BindView(R.id.password_et)
    EditText PasswordET;
    @BindView(R.id.confirm_password_et)
    EditText ConfirmPasswordET;
    @BindView(R.id.progress_bar)
    View Progressbar;
    @BindView(R.id.shadow_layout)
    View ShadowLayout;

    AppViewModel appViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        prefUtils = new PrefUtils(this);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        onViewFocusChanged();

        SignUpBtn.setOnClickListener(view -> {
//            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
//            finish();
            validateUserInputs();
        });
    }

    private void onViewFocusChanged(){
        UserNameET.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                String username = UserNameET.getText().toString();
                SignUpBtn.setEnabled(false);
                if(!TextUtils.isEmpty(username) && username.length() >= 5){
                    appViewModel.isUsernameUnique(username).observe(this, data -> {
                        if(data != null){
                            UserNameET.setError("Username already exists");
                            return;
                        }
                        SignUpBtn.setEnabled(true);
                    });
                }else{
                    UserNameET.setError("Invalid username");
                    SignUpBtn.setEnabled(false);
                }
            }
        });
        EmailET.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                String email = EmailET.getText().toString();
                SignUpBtn.setEnabled(false);
                if(!TextUtils.isEmpty(email) ){
                    appViewModel.isEmailUnique(email).observe(this, data -> {
                        if(data != null){
                            EmailET.setError("Email already exists");
                            return;
                        }
                        SignUpBtn.setEnabled(true);
                    });
                }else{
                    EmailET.setError("Invalid username");
                    SignUpBtn.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.has_account)
    public void hasAccount(View view){
        startActivity(new Intent(this,LogInActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

    }

    private void validateUserInputs(){
        String firstname = FirstNameET.getText().toString(),
                lastname = LastNameET.getText().toString(),
                username = UserNameET.getText().toString(),
                email = EmailET.getText().toString(),
                password = PasswordET.getText().toString(),
                confirmPassword = ConfirmPasswordET.getText().toString();
        if(firstname.isEmpty()){
            FirstNameET.setError("Invalid input");
            return;
        }else if(firstname.length() < 2){
            FirstNameET.setError("FirstName cannot be less than 2 characters");
            return;
        }
        if(lastname.isEmpty()){
            LastNameET.setError("Invalid input");
            return;
        }else if(lastname.length() < 2){
            LastNameET.setError("LastName cannot be less than 2 characters");
            return;
        }
        if(username.isEmpty()){
            UserNameET.setError("Invalid input");
            return;
        }
        if(username.length() < 5){
            UserNameET.setError("Username cannot be less than 5 characters");
        }
        if(email.isEmpty()){
            EmailET.setError("Invalid input");
            return;
        }
        if(password.isEmpty()){
            PasswordET.setError("Invalid input");
            return;
        }else if(password.length() <=6 ){
            PasswordET.setError("Password cannot be less than 6 characters");
            return;
        }
        if(!confirmPassword.equals(password)){
            ConfirmPasswordET.setText("");
            PasswordET.setText("");
            PasswordET.setError("Passwords do not match");
            //PasswordET.requestFocus();
            return;
        }
        ShadowLayout.setVisibility(View.VISIBLE);
        Progressbar.setVisibility(View.VISIBLE);
        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setUsername(username);
        userModel.setPassword(password);
        userModel.setCreated_at(new Date());
        userModel.setUsername(username);

        processData(userModel);
    }


    private void processData(UserModel userModel){
       appViewModel.createNewUser(userModel).observe(this, isSuccessful -> {
           if(isSuccessful != null && isSuccessful){
               Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show();

               prefUtils.setFirstTime(false);
               prefUtils.setLogIn(true);
               prefUtils.setUserId(userModel.getId());
           }
       });
    }
}
