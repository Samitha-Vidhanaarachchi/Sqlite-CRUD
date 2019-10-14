package com.example.it18078510;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.it18078510.Database.DBHelper;

import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText username, dob, pass;
    RadioButton male, female;
    Button Edit,Delete,search;
    String Gender;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        username = findViewById(R.id.UsernameE);
        dob = findViewById(R.id.DOBE);
        pass = findViewById(R.id.passwordE);
        male = findViewById(R.id.radioButtonMale_E);
        female = findViewById(R.id.radioButtonFemale_E);
        Edit = findViewById(R.id.btnEdit);
        Delete = findViewById(R.id.btnDelete);
        search = findViewById(R.id.btnSearchE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper db = new DBHelper(getApplicationContext());
                String search_result = username.getText().toString();

                List SearchList = db.readAll(search_result);

                if(SearchList.isEmpty()){
                    Toast.makeText(EditProfile.this, "No such User", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProfile.this, "User " + SearchList.get(0).toString() +" found", Toast.LENGTH_SHORT).show();
                    username.setText(SearchList.get(0).toString());
                    dob.setText(SearchList.get(1).toString());
                    pass.setText(SearchList.get(3).toString());

                    if(SearchList.get(2).toString().equals("Male")){
                        male.setChecked(true);
                    }else{
                        female.setChecked(true);
                    }

                }

                username.setText("");
                dob.setText("");
                pass.setText("");
                male.setChecked(true);
                male.setChecked(false);

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper db = new DBHelper(getApplicationContext());

                if(male.isChecked()){
                    Gender = "Male";
                }else{
                    Gender = "Female";
                }



                Boolean result = db.updateinfo(username.getText().toString(),dob.getText().toString(),Gender,pass.getText().toString());

                if(result == true){
                    Toast.makeText(EditProfile.this, "User Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditProfile.this, "User Not Updated", Toast.LENGTH_SHORT).show();
                }


                username.setText("");
                dob.setText("");
                pass.setText("");
                male.setChecked(true);
                male.setChecked(false);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());

                int result = db.deleteInfo(username.getText().toString());
                Toast.makeText(EditProfile.this, "User "+ result + " deleted", Toast.LENGTH_SHORT).show();


                username.setText("");
                dob.setText("");
                pass.setText("");
                male.setChecked(true);
                male.setChecked(false);
            }
        });
    }
}
