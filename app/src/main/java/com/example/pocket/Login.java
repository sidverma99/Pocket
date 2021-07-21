package com.example.pocket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText mobileNumber;
    private TextView register;
    private Button submit;
    private String id,passcode;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mobileNumber=(EditText) findViewById(R.id.phone_number);
        register=(TextView) findViewById(R.id.register);
        submit=(Button) findViewById(R.id.otp);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_no=mobileNumber.getText().toString();
                if (!phone_no.isEmpty() && phone_no.length()==10){
                    String complete_phone_no="+91"+phone_no;
                    DatabaseHelper databaseHelper=new DatabaseHelper(Login.this);
                    Cursor cursor=databaseHelper.userNameExist(complete_phone_no,1);
                    if(cursor.getCount()==0){
                        Toast.makeText(Login.this,"Unable to find the user",Toast.LENGTH_SHORT).show();
                        intent=new Intent(Login.this,OTP.class);
                    } else{
                        while (cursor.moveToNext()){
                            id=cursor.getString(0);
                            passcode=cursor.getString(3);
                        }
                        intent=new Intent(Login.this,Passcode.class);
                        intent.putExtra("id",id);
                        intent.putExtra("passcode",passcode);
                    }
                    intent.putExtra("user_number",complete_phone_no);
                    startActivity(intent);
                    finish();
                } else{
                    mobileNumber.requestFocus();
                    if(phone_no.isEmpty()){
                        Toast.makeText(Login.this,"Mobile Number is required",Toast.LENGTH_SHORT).show();
                        return;
                    } else if(phone_no.length()!=10){
                        Toast.makeText(Login.this,"Unvalid mobile number",Toast.LENGTH_SHORT).show();
                        return;
                    } else{
                        Toast.makeText(Login.this,"Require only 10 digits",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}
