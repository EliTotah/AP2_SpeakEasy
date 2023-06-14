package com.example.ap2_speakeasy.Sign_up;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.databinding.ActivitySignUpBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private Bitmap selectedImageBitmap;
    private boolean checkInput = false;
    private ImageView imageView; // Declare the ImageView as a class member

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button nextButton = binding.buttonNext;
        CardView cardView = binding.cardViewProfileImage;
        imageView = binding.profileImage;// Assign the ImageView reference

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Image selected from the gallery
                            Uri imageUri = data.getData();
                            try {
                                // Convert Uri to Bitmap
                                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                                imageView.setImageBitmap(bitmap);
                                selectedImageBitmap = bitmap;
                                // Use the bitmap as needed
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
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
                            selectedImageBitmap = photo;
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInputValid()) {
                    EditText editTextName = binding.SignUpName;
                    EditText editTextUserName = binding.SignUpUsername;
                    String inputName = editTextName.getText().toString().trim();
                    String inputUserName = editTextUserName.getText().toString().trim();
                    // Start the next activity
                    Intent intent = new Intent(SignUpActivity.this, ContactInfoActivity.class);
                    intent.putExtra("name", inputName);
                    intent.putExtra("username", inputUserName);
                    if (selectedImageBitmap != null) {
                        Log.e("photo", selectedImageBitmap.toString());
                        intent.putExtra("imageBitmap", selectedImageBitmap.toString());
                    }
                    else {
                        imageView.setDrawingCacheEnabled(true); // Enable the drawing cache
                        imageView.buildDrawingCache(); // Build the drawing cache
                        Bitmap bitmap = imageView.getDrawingCache();
                        imageView.setDrawingCacheEnabled(false);
                        Log.e("photo", bitmap.toString());
                        intent.putExtra("imageBitmap", bitmap.toString());
                    }
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isInputValid() {
        EditText editTextName = binding.SignUpName;
        EditText editTextUserName = binding.SignUpUsername;
        String inputName = editTextName.getText().toString().trim();
        String inputUserName = editTextUserName.getText().toString().trim();
        if ((!inputName.isEmpty()) && (!inputUserName.isEmpty())){
            checkInput = true;
        }
        return checkInput;
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
    private Uri saveImageToGallery(Bitmap imageBitmap) {
        // Save the image to a temporary file
        File tempFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg");
        try (OutputStream os = new FileOutputStream(tempFile)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the image to the gallery
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(tempFile);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        return contentUri;
    }

}
