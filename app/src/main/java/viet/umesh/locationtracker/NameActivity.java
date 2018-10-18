package viet.umesh.locationtracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {

    String email, password;
    EditText editName;
    CircleImageView circleImageView;

    Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        editName = findViewById(R.id.nameText);
        circleImageView = findViewById(R.id.profile_image);

        Intent intent = getIntent();

        if (intent != null) {
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
        }
    }

    public void generateCode(View v) {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String dateFormat = format.format(date);
        Random random = new Random();

        int n = 100000 + random.nextInt(900000);

        String code = String.valueOf(n);

        if (resultUri != null) {

            Intent intent = new Intent(NameActivity.this, InviteCodeActivity.class);
            intent.putExtra("name", editName.getText().toString());
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("date", dateFormat);
            intent.putExtra("isSharing", "false");
            intent.putExtra("code", code);
            intent.putExtra("imageUri", resultUri);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectImage(View v) {

        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 12 && resultCode == RESULT_OK && data != null){

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
