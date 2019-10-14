package com.example.it18078510;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.it18078510.Database.DBHelper;

public class ProfileManagement extends AppCompatActivity {

    EditText username, DOB, Password;
    RadioButton male, female;
    Button updateProfile, register;
    String Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);



        username = findViewById(R.id.usernameP);
        DOB = findViewById(R.id.DOBP);
        Password = findViewById(R.id.PasswordP);
        male = findViewById(R.id.radioButtonMale_P);
        female = findViewById(R.id.radioButtonFemale_P);

        updateProfile = findViewById(R.id.btnUpdateProfile);
        register = findViewById(R.id.btnRegister);


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper db = new DBHelper(getApplicationContext());

                if(male.isChecked()){
                    Gender = "Male";
                }else{
                    Gender = "Female";
                }

                String userName = username.getText().toString();
                String DateOfB = DOB.getText().toString();
                String password = Password.getText().toString();

                long addedID = db.addInfo(userName,DateOfB,Gender,password);

                if(addedID > 0 ){
                    Toast.makeText(ProfileManagement.this, "User Added Successfully ID : " +addedID , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileManagement.this, "Not added ", Toast.LENGTH_SHORT).show();
                }

                username.setText("");
                DOB.setText("");
                Password.setText("");
                male.setChecked(true);
                male.setChecked(false);
            }
        });


    }
}
