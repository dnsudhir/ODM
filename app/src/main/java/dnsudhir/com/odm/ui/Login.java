package dnsudhir.com.odm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import dnsudhir.com.odm.FirebaseAuthentications.EmailPassword;
import dnsudhir.com.odm.Home;
import dnsudhir.com.odm.R;

public class Login extends AppCompatActivity {

  private EmailPassword emailPassword;
  private TextView tvLblPassword, tvLblConfPassword, tvLblSignUp, tvHeaderTitle,
      tvLblForgotPassword;
  private EditText etEmail, etPassword, etConfPassword;
  private Button btnSignUp, btnLogin, btnResetEmail;
  private boolean isSignUp = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    emailPassword = new EmailPassword(this);

    tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
    tvLblPassword = findViewById(R.id.tvLblPassword);
    tvLblConfPassword = findViewById(R.id.tvLblConfPassword);
    tvLblSignUp = findViewById(R.id.tvLblSignUp);
    tvLblForgotPassword = findViewById(R.id.tvLblForgotPassword);
    etEmail = findViewById(R.id.etEmail);
    etPassword = findViewById(R.id.etPassword);
    etConfPassword = findViewById(R.id.etConfPassword);
    btnLogin = findViewById(R.id.btnLogin);
    btnSignUp = findViewById(R.id.btnSignUp);
    btnResetEmail = findViewById(R.id.btnResetEmail);

    btnSignUp.setOnClickListener(v -> {

      if (etEmail.getText().toString().trim().isEmpty()) {
        etEmail.setError("Please Enter Username");
        etEmail.requestFocus();
      } else if (etPassword.getText().toString().trim().isEmpty()) {
        etPassword.setError("Please Enter Password");
        etPassword.requestFocus();
      } else if (etConfPassword.getText().toString().trim().isEmpty()) {
        etConfPassword.setError("Please Enter Confirm Password");
        etConfPassword.requestFocus();
      } else if (!etPassword.getText()
          .toString()
          .trim()
          .contentEquals(etConfPassword.getText().toString().trim())) {
        etConfPassword.setError("Passwords Do Not Match");
        etConfPassword.requestFocus();
      } else {
        emailPassword.registerFirebase(etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim());
      }
    });

    btnLogin.setOnClickListener(v -> {

      if (etEmail.getText().toString().trim().isEmpty()) {
        etEmail.setError("Please Enter Email");
        etEmail.requestFocus();
      } else if (etPassword.getText().toString().trim().isEmpty()) {
        etPassword.setError("Please Enter Password");
        etPassword.requestFocus();
      } else {
        emailPassword.login(etEmail.getText().toString().trim(),
            etPassword.getText().toString().trim());
      }
    });

    tvLblSignUp.setOnClickListener(v -> {
      if (!isSignUp) {
        tvLblConfPassword.setVisibility(View.VISIBLE);
        etConfPassword.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        tvHeaderTitle.setText("Sign Up");
        tvLblSignUp.setText("Login");
        tvLblForgotPassword.setVisibility(View.GONE);
        isSignUp = true;
      } else {
        tvLblConfPassword.setVisibility(View.GONE);
        etConfPassword.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        tvHeaderTitle.setText("Login");
        tvLblSignUp.setText("Sign Up");
        tvLblForgotPassword.setVisibility(View.VISIBLE);
        isSignUp = false;
      }
    });

    tvLblForgotPassword.setOnClickListener(v -> {
      tvLblPassword.setVisibility(View.GONE);
      etPassword.setVisibility(View.GONE);
    });

    btnResetEmail.setOnClickListener(v -> {
      if (etEmail.getText().toString().trim().isEmpty()) {
        etEmail.setError("Please Enter Email");
        etEmail.requestFocus();
      } else {
        emailPassword.resetEmail(etEmail.getText().toString().trim());
      }
    });
  }

  @Override protected void onStart() {
    super.onStart();
    FirebaseUser currentUser = emailPassword.getCurrentUser();
    if (currentUser != null) updateUI(currentUser);
  }

  private void updateUI(FirebaseUser currentUser) {
    startActivity(new Intent(this, Home.class));
    finish();
  }
}
