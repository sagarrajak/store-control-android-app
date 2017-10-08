package com.example.sagar.myapplication.element.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sagar.myapplication.R;
import com.example.sagar.myapplication.api.LoginApi;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.element.employee.MainActivity;

public class login_activity extends AppCompatActivity {

    private Button button;
    private ProgressBar mProgressBar;
    private EditText login,password;
    private LoginApi mLogingApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( !getApplicationContext().getSharedPreferences("SETTING" , Context.MODE_PRIVATE).getString("token" ,"").isEmpty()  ){
            Token.setToken(getApplicationContext().getSharedPreferences("SETTING" , Context.MODE_PRIVATE).getString("token" ,""));
            login();
        }
        setContentView(R.layout.activity_login_activity);
        button = (Button) findViewById(R.id.login_button);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        login = (EditText) findViewById(R.id.login_email_adress);
        password = (EditText) findViewById(R.id.login_email_password);
        mProgressBar.setVisibility(View.GONE);
        mLogingApi = new LoginApi(getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mProgressBar.setVisibility(View.VISIBLE);
                String login_id =  login.getText().toString();
                String login_password  = password.getText().toString();
                if(login_id.isEmpty()){
                    Toast.makeText( getApplicationContext() , "Pls enter email address" ,Toast.LENGTH_LONG ).show();
                    mProgressBar.setVisibility(View.GONE);
                }
                else if(login_password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pls enter password", Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.GONE);
                }
                else{
                    mLogingApi.login( login_id , login_password , mProgressBar , login_activity.this );
                }
            }
        });
    }
    private void login(){
        Intent inten = new Intent(getApplicationContext() , MainActivity.class );
        startActivity(inten);
        finish();
    }
}
