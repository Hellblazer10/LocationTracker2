package viet.umesh.locationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if(firebaseUser == null){
            setContentView(R.layout.activity_login);
        }
        else{
            Intent intent = new Intent(MainActivity.this, MyNavigationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void getToLogin(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void getToRegister(View v){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
