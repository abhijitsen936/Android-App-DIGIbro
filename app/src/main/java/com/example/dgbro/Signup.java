package com.example.dgbro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private EditText mUsername, mPassword, mConfirmPassword;
    private RadioGroup mRadioGroup;
    private RadioButton mRecruiter, mCandidate;
    private Button mSignupButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Authentication and Realtime Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        // Initialize UI elements
        mUsername = findViewById(R.id.id1);
        mPassword = findViewById(R.id.id2);
        mConfirmPassword = findViewById(R.id.editTextTextPassword2);
        mRadioGroup = findViewById(R.id.radioGroup);
        mRecruiter = findViewById(R.id.radioButton);
        mCandidate = findViewById(R.id.radioButton2);
        mSignupButton = findViewById(R.id.login);

        // Set click listener for Signup button
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                int selectedId = mRadioGroup.getCheckedRadioButtonId();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedId == -1) {
                    Toast.makeText(Signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    String userType = "";
                    if (selectedId == mRecruiter.getId()) {
                        userType = "Recruiter";
                    } else if (selectedId == mCandidate.getId()) {
                        userType = "Candidate";
                    }

                    // Create new user in Firebase Authentication
                    String finalUserType = userType;
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Save user details in Firebase Realtime Database
                                        String userId = mAuth.getCurrentUser().getUid();
                                        mDatabase.child(userId).child("UserType").setValue(finalUserType);
                                        Toast.makeText(Signup.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Signup.this, "Signup failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
