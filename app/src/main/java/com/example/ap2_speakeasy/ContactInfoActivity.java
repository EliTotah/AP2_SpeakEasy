package com.example.ap2_speakeasy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ContactInfoActivity extends AppCompatActivity {
    private ImageView imageView; // Declare the ImageView as a class member

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        CardView cardView = findViewById(R.id.card_view_profile_image);
        imageView = findViewById(R.id.profile_image); // Assign the ImageView reference

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Image selected from the gallery
                            Uri imageUri = data.getData();
                            imageView.setImageURI(imageUri);
                        }
                    }
                });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Photo captured from the camera
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            imageView.setImageBitmap(photo);
                        }
                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a dialog or activity to upload a new picture
                openUploadDialog();
            }
        });
        Button nextButton = findViewById(R.id.buttonNext);
        Button prevButton = findViewById(R.id.buttonPrev);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity
                Intent intent = new Intent(ContactInfoActivity.this, GenderActivity.class);
                startActivity(intent);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the previous activity
                Intent intent = new Intent(ContactInfoActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        return;
    }
    private void openUploadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Picture");

        // Set the dialog view with options to select an image from the gallery or take a photo
        builder.setItems(new CharSequence[]{"Choose from Gallery", "Take a Photo"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Choose from Gallery option
                        openGallery();
                        break;
                    case 1:
                        // Take a Photo option
                        openCamera();
                        break;
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
}

