package viet.umesh.locationtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {

    EditText register_email;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_email = findViewById(R.id.emailTextregister);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }

    public void gotoPasswordActivity(View v) {

        dialog.setMessage("Checking email");
        dialog.show();

        auth.fetchSignInMethodsForEmail(register_email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            boolean check = !task.getResult().getSignInMethods().isEmpty();

                            if (!check) {

                                Intent intent = new Intent(RegisterActivity.this, PasswordActivity.class);
                                intent.putExtra("email",register_email.getText().toString());
                                startActivity(intent);
                                finish();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
