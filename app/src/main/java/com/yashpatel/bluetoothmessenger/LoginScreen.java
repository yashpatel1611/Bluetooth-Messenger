package com.yashpatel.bluetoothmessenger;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

// Import all libraries needed to allow Google Sign-In authentication


public class LoginScreen extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001; //Request code for google sign in

    TextView loginScreenTV; // TextView object for holding the welcome text on activity
    TextView signInPromptTV; // TextView object for holding the sign in prompt on activity
    SignInButton signInButton; // SignInButton object - this is the actual sign in button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen); // Show the login screen activity

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build(); // This allows me to start the google sign in intent

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso); //assign client to client variable

        signInButton = findViewById(R.id.signInButton); // Hook the button on xml activity to java variable
        signInButton.setColorScheme(1); // Set the colour scheme of the button to light - just looks better in my opinion
        signInButton.setVisibility(View.INVISIBLE); // Set it to be invisible for now, will be made visible

        loginScreenTV = findViewById(R.id.loginScreenTV); // Hook the welcome text on activity to java variable
        signInPromptTV = findViewById(R.id.signInPromptTV); // Hook the sign in prompt text to variable
        signInPromptTV.setVisibility(View.INVISIBLE); // Set the visibility of prompt off - allows for easier animations

        // Below is a method call to set a function on clicking the sign in button => starts sign in process
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.signInButton) {
                    signIn(mGoogleSignInClient);
                }
            }
        });

        runAnimations(); // Backgroup setups are finished -> start runAnimations function for user


    }
    //Function called when the activity is started
    protected void onStart() {
        super.onStart();
        //Get the last signed in account for the application
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //If the account returned is not empty, i.e. there is an account present
        //start the switch to the main activity
        if (account != null) {
            mainActivitySwitch();

        }
    }

    //Function to switch to the main activity
    private void mainActivitySwitch() {
        //Ask for permissions first for the application
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 199);
        Intent mainActivityIntent = new Intent(this, com.yashpatel.bluetoothmessenger.MainActivity.class);
        startActivity(mainActivityIntent);
        this.finish();
    }

    private void signIn(GoogleSignInClient mGoogleSignInClient) {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.e("account", account.getDisplayName() + account.getEmail() + account.getAccount());
            mainActivitySwitch();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        } catch (NullPointerException e) {
            Log.w("Error", e);
        }
    }

    // This procedure handles all the animations presented to the user
    private void runAnimations() {
        // Create variable for the welcome text animation and reset it to the start
        final Animation loginScreenTVAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_text_view_animations);
        loginScreenTVAnimation.reset();

        // Create variable for the sign in button animation and reset it to the start
        final Animation buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.sign_in_button_animations);
        buttonAnimation.reset();

        // Create variable for the fade in animation and reset it to the start -> This is different as it needs to be offset
        final Animation signInPromptTVAnimations = AnimationUtils.loadAnimation(this, R.anim.sign_in_promt_animations);
        signInPromptTVAnimations.reset();

        // Create variable for the movement animation and reset it to the start
        final Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_y);
        translateAnimation.reset();

        loginScreenTV.setVisibility(View.VISIBLE); // Make the welcome text visible
        loginScreenTV.startAnimation(loginScreenTVAnimation); // Start the fade in animation
        loginScreenTV.startAnimation(translateAnimation); // Start the movement animation
        signInPromptTV.setVisibility(View.VISIBLE); // Make the sign in prompt visible
        signInButton.setVisibility(View.VISIBLE); // Make the sign in button visible
        signInPromptTV.startAnimation(signInPromptTVAnimations); // Start the animation for the sign in prompt
        signInButton.startAnimation(buttonAnimation); // Start the animations for the sign in button
    }
}
