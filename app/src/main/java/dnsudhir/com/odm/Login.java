package dnsudhir.com.odm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

  private FirebaseAuth mAuth;
  private TextView tvLblPassword, tvLblConfPassword, tvLblSignUp, tvHeaderTitle,
      tvLblForgotPassword;
  private EditText etEmail, etPassword, etConfPassword;
  private Button btnSignUp, btnLogin, btnResetEmail;
  private boolean isSignUp = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();

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

    btnSignUp.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

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
          registerFirebase(etEmail.getText().toString().trim(),
              etPassword.getText().toString().trim());
        }
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        if (etEmail.getText().toString().trim().isEmpty()) {
          etEmail.setError("Please Enter Email");
          etEmail.requestFocus();
        } else if (etPassword.getText().toString().trim().isEmpty()) {
          etPassword.setError("Please Enter Password");
          etPassword.requestFocus();
        } else {
          mLogin(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
        }
      }
    });

    tvLblSignUp.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
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
      }
    });

    tvLblForgotPassword.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        tvLblPassword.setVisibility(View.GONE);
        etPassword.setVisibility(View.GONE);
      }
    });

    btnResetEmail.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (etEmail.getText().toString().trim().isEmpty()) {
          etEmail.setError("Please Enter Email");
          etEmail.requestFocus();
        } else {
          mPasswordResetEmail(etEmail.getText().toString().trim());
        }
      }
    });
  }

  private void mPasswordResetEmail(String email) {
    mAuth.sendPasswordResetEmail(email);
  }





  private void mLogin(String email, String password) {
    mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(Login.this, Home.class));
              finish();
            } else {
              Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  private void registerFirebase(String email, String password) {

    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              FirebaseUser currentUser = mAuth.getCurrentUser();
              Toast.makeText(Login.this, "Registration Successful", Toast.LENGTH_SHORT).show();
              tvLblSignUp.callOnClick();
            } else {
              Toast.makeText(Login.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  @Override protected void onStart() {
    super.onStart();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) updateUI(currentUser);
  }

  private void updateUI(FirebaseUser currentUser) {
    startActivity(new Intent(this, Home.class));
    finish();
  }
}
