package com.example.root.keypairgenrationexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;
import java.security.KeyStoreException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private EditText editTextLoginId , editTextPassword;
    private Button mButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLoginId = (EditText) findViewById(R.id.login_email_adress);
        editTextPassword = (EditText) findViewById(R.id.login_email_password);

        mButton  = (Button)findViewById(R.id.login_button);
        mButton.setOnClickListener(this);
    }
    private void encrypetData( String login_id , String password ) throws KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
    }
    @Override
    public void onClick(View v){
        if(editTextLoginId.getText().toString().isEmpty()){
            Toast.makeText(this, "Provide Login id !!", Toast.LENGTH_LONG).show();
            return;
        }
        if(editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Provide password !!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private void  login(){
        Toast.makeText(this , "U are loged in now" , Toast.LENGTH_SHORT).show();
    }
}
