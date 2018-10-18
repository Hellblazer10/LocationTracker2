package viet.umesh.locationtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {

    String name, email, password, date_str, isSharing, code_str, userId;

    Uri imageUri;

    ProgressDialog progressDialog;

    TextView code_text;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        code_text = findViewById(R.id.inviteCodeText);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        if (intent != null) {
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            date_str = intent.getStringExtra("date");
            isSharing = intent.getStringExtra("isSharing");
            code_str = intent.getStringExtra("code");
            imageUri = intent.getParcelableExtra("imageUri");
        }

        code_text.setText(code_str);
    }

    public void registerUser(View v){


        progressDialog.setMessage("Please wait");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            CreateUser createUser = new CreateUser(name, email, password, code_str, "false","na","na","na");

                            firebaseUser = auth.getCurrentUser();

                            userId = firebaseUser.getUid();

                            databaseReference.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            if(task.isSuccessful()){
                                                progressDialog.dismiss();

                                                Toast.makeText(InviteCodeActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();


                                            }else{
                                                progressDialog.dismiss();

                                                Toast.makeText(InviteCodeActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                        }
                    }
                });

    }
}
