package com.codeGeekerz.project.pollutionTracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codeGeekerz.project.pollutionTracker.utils.ApplicationUIUtils;
import com.codeGeekerz.project.pollutionTracker.utils.SessionManager;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String userName;
    private SharedPreferences sharedPref;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        _loginButton = (Button) findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink = (TextView) findViewById(R.id.link_signup);
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        HashMap<String, String> user = session.getUserDetails();
        final String userName = user.get(SessionManager.KEY_NAME);
        String userEmail = user.get(SessionManager.KEY_EMAIL);
        String userPassword = user.get(SessionManager.KEY_PASSWORD);
        if (!email.equals(userEmail) && !password.equals(userPassword)) {
            finish();
        } else {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            if (!onLoginSuccess(userName, email, password)) {
                                onLoginFailed();
                            }
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                Intent intent = new Intent(this, MenuDisplayActivity.class);
                intent.putExtra("userName", userName);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public boolean onLoginSuccess(String userName, String email, String password) {
        _loginButton.setEnabled(true);
        session.createLoginSession(userName, email, password);
        Intent intent = new Intent(this, MenuDisplayActivity.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        return true;
    }

    public void onLoginFailed() {
        ApplicationUIUtils.showAlertDialog(getApplicationContext(), "Login failed", "Your userName Passwor is incorrect! Please Try again", false);
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
