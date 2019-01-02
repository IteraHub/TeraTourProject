package com.iterahub.teratour.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.iterahub.teratour.R;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.password)
    EditText PasswordET;
    @BindView(R.id.user_et)
    EditText UserNameET;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.login_button)
    Button loginBtn;

    AppViewModel appViewModel;
    PrefUtils prefUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
       if(getSupportActionBar() != null){
           getSupportActionBar().setDisplayShowTitleEnabled(false);
       }
       appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
       prefUtils = new PrefUtils(this);

    }

    @OnClick(R.id.no_account)
    public void noAccount(View v){
        startActivity(new Intent(this,SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.login_button)
    public void loginClicked(View view){
        validateInputs();
    }

    private void validateInputs(){
        String username = UserNameET.getText().toString();
        String password = PasswordET.getText().toString();
        if(username.isEmpty()){
            UserNameET.setError("Field cannot be empty");
            return;
        }
        if(password.isEmpty()){
            PasswordET.setError("Field cannot be empty");
            return;
        }
        processLogIn(username,password);

    }

    private void processLogIn(String username, String password){
        mProgressBar.setVisibility(View.VISIBLE);
        loginBtn.setEnabled(false);
        appViewModel.loginUser(username,password).observe(this, userModel -> {
            if(userModel != null){
                prefUtils.setLogIn(true);
                prefUtils.setFirstTime(false);
                prefUtils.setUserId(userModel.getId());
                startActivity(new Intent(LogInActivity.this,MainActivity.class));
                finish();
            }else{
                mProgressBar.setVisibility(View.GONE);
                loginBtn.setEnabled(true);
                Toast.makeText(this, "Login was not successful. Try again later", Toast.LENGTH_SHORT).show();

            }
        });
    }

}