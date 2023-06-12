package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_speakeasy.ContactInfoActivity;

public class SignUpActivity extends AppCompatActivity {
    private boolean checkInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button nextButton = findViewById(R.id.buttonNext);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    // Start the next activity
                    Intent intent = new Intent(SignUpActivity.this, ContactInfoActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return;
    }
    private boolean isInputValid() {
        EditText editTextName = findViewById(R.id.Sign_up_name);
        EditText editTextUserName = findViewById(R.id.Sign_up_username);
        String inputName = editTextName.getText().toString().trim();
        String inputUserName = editTextUserName.getText().toString().trim();
        if ((!(inputName.isEmpty()))&&(!(inputUserName.isEmpty()))){
            checkInput = true;
        }
        return checkInput;
    }
}
