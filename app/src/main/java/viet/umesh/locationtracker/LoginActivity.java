package viet.umesh.locationtracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email_text, password_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email_text = findViewById(R.id.emailText);
        password_text =findViewById(R.id.passwordText);
        auth = FirebaseAuth.getInstance();

    }

    public void login(View v){
        auth.signInWithEmailAndPassword(email_text.getText().toString(),password_text.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MyNavigationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
