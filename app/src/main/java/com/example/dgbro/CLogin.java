package com.example.dgbro;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseUploader {
    private static Object GoogleCredentials;

    public static void main(String[] args) throws IOException {
        // Initialize FirebaseApp with credentials
        FileInputStream serviceAccount = new FileInputStream("path/to/firebase-adminsdk.json");
        FirebaseOptions.Builder builder = new FirebaseOptions.Builder();
        FirebaseOptions.Builder builder1;
        builder1 = builder.setApiKey(GoogleCredentials.toString(serviceAccount));
        builder.setDatabaseUrl("https://your-firebase-project-id.firebaseio.com/");
        FirebaseOptions options = builder
                .build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

        // Get reference to Firebase database node
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        // Create a new user object with name, age, and resume fields
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Your Name");
        user.put("age", 25);
        user.put("resume", "path/to/resume.pdf");
        user.put("timestamp", ServerValue.TIMESTAMP);

        // Push the user object to Firebase database
        DatabaseReference newRef = ref.push();
        newRef.setValue(user);

        // Print success message
        System.out.println("User data uploaded to Firebase.");
    }
}
