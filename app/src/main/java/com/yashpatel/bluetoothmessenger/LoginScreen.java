package com.yashpatel.bluetoothmessenger;

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


public class LoginScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    TextView loginScreenTV;
    TextView signInPromptTV;
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.signInButton);
        signInButton.setColorScheme(1);
        signInButton.setVisibility(View.INVISIBLE);

        loginScreenTV = findViewById(R.id.loginScreenTV);
        signInPromptTV = findViewById(R.id.signInPromptTV);
        signInPromptTV.setVisibility(View.INVISIBLE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.signInButton):
                        signIn(mGoogleSignInClient);
                        break;
                }
            }
        });

        runAnimations();


    }

    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            setContentView(R.layout.activity_main_screen);
//
//        }
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
//            setContentView(R.layout.activity_main_screen);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        } catch (NullPointerException e) {
            Log.w("Error", e);
        }
    }

    private void runAnimations() {
        final Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_text_view_animations);
        textAnimation.reset();

        final Animation buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.sign_in_button_animations);
        buttonAnimation.reset();

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.sign_in_promt_animations);
        fadeInAnimation.reset();

        final Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_x);

        textAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                signInButton.setVisibility(View.VISIBLE);
                signInPromptTV.setVisibility(View.VISIBLE);
                signInButton.startAnimation(buttonAnimation);
                signInPromptTV.startAnimation(fadeInAnimation);
                loginScreenTV.startAnimation(translateAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loginScreenTV.startAnimation(textAnimation);
    }
}
