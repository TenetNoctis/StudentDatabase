/**
 * SplashActivity.java

 * This activity displays a splash screen when the app is launched.
 * The splash screen appears for a specified duration before transitioning
 * to the LoginActivity.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: The duration of the splash screen is set to 3 seconds.
 */

package com.example.studentdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen in milliseconds (3 seconds)
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar); // Set theme for splash screen (no action bar)
        setContentView(R.layout.activity_splash); // Set layout for the splash screen

        // Create a Handler to delay the transition to the LoginActivity
        new Handler().postDelayed(() -> {
            // After the splash duration, transition to LoginActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent); // Start LoginActivity
            finish(); // Close SplashActivity so the user cannot return to it
        }, SPLASH_DURATION); // Delay of 3 seconds (SPLASH_DURATION)
    }
}
