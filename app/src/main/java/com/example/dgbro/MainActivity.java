package com.example.dgbro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, signupButton;
    String savedUsername, savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Find the views by their IDs
        usernameEditText = findViewById(R.id.id1);
        passwordEditText = findViewById(R.id.id2);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.signup);

        // Set a click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password entered by the user
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                // Check if the entered username and password match the saved ones
                if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
                    // If they match, show a toast message and start the home activity
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CLogin.class);
                    startActivity(intent);
                } else {
                    // If they don't match, show a toast message
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set a click listener for the signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the signup activity
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        // Retrieve the saved username and password from the shared preferences
        savedUsername = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("username", "");
        savedPassword = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("password", "");
    }
}
