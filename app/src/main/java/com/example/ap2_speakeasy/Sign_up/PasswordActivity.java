package com.example.ap2_speakeasy.Sign_up;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_speakeasy.AP2_SpeakEasy;
import com.example.ap2_speakeasy.API.UserAPI;
import com.example.ap2_speakeasy.LoginActivity;
import com.example.ap2_speakeasy.R;
import com.example.ap2_speakeasy.databinding.ActivitySignUpBinding;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {
    private String selectedImage;
    private String username;
    private String displayName;
    private String password;
    private ActivitySignUpBinding binding;
    private ImageView imageView; // Declare the ImageView as a class member

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private TextView errorMessageTextView;
    private ImageView passwordVisibility1;
    private ImageView passwordVisibility2;

    private boolean showPassword1 = false;
    private boolean showPassword2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            displayName = intent.getStringExtra("name");
            selectedImage = intent.getStringExtra("selectedImage");
        }
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        errorMessageTextView = findViewById(R.id.error_msg);
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

        Button submitButton = findViewById(R.id.buttonSubmit);
        Button prevButton = findViewById(R.id.buttonPrev);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordActivity.this, GenderActivity.class);
                startActivity(intent);
            }
        });
        passwordVisibility1 = findViewById(R.id.visibility_password);
        passwordVisibility2 = findViewById(R.id.visibility_confirm_password);

        passwordEditText.addTextChangedListener(passwordTextWatcher);
        confirmPasswordEditText.addTextChangedListener(confirmPasswordTextWatcher);

        passwordVisibility1.setOnClickListener(v -> {
            showPassword1 = !showPassword1;
            togglePasswordVisibility(passwordEditText, passwordVisibility1, showPassword1);
        });

        passwordVisibility2.setOnClickListener(v -> {
            showPassword2 = !showPassword2;
            togglePasswordVisibility(confirmPasswordEditText, passwordVisibility2, showPassword2);
        });

        submitButton.setOnClickListener(v -> validatePasswords());
        return;
    }

    private void togglePasswordVisibility(EditText editText, ImageView visibilityImageView, boolean showPassword) {
        if (showPassword) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            visibilityImageView.setImageResource(R.drawable.ic_action_eye);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            visibilityImageView.setImageResource(R.drawable.ic_action_eye);
        }
        editText.setSelection(editText.getText().length()); // Maintain cursor position at the end
    }

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            errorMessageTextView.setText("");
        }
    };

    private TextWatcher confirmPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            errorMessageTextView.setText("");
        }
    };

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

    private void validatePasswords() {
        int validPassword=0;
        password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!password.equals(confirmPassword)) {
            errorMessageTextView.setText(R.string.error_passwords_do_not_match);
            errorMessageTextView.setTextColor(getResources().getColor(R.color.black, null));
            return;
        }

        int caps = password.replaceAll("[^A-Z]", "").length();
        int small = password.replaceAll("[^a-z]", "").length();
        int num = password.replaceAll("[^0-9]", "").length();
        int specialSymbol = password.replaceAll("[^@$!%*?&]", "").length();

        if (caps < 1) {
            errorMessageTextView.setText(R.string.error_missing_uppercase);
            errorMessageTextView.setTextColor(getResources().getColor(R.color.black, null));
        } else if (small < 1) {
            errorMessageTextView.setText(R.string.error_missing_lowercase);
            errorMessageTextView.setTextColor(getResources().getColor(R.color.black, null));
        } else if (num < 1) {
            errorMessageTextView.setText(R.string.error_missing_number);
            errorMessageTextView.setTextColor(getResources().getColor(R.color.black, null));
        } else if (specialSymbol < 1) {
            errorMessageTextView.setText(R.string.error_missing_special_symbol);
            errorMessageTextView.setTextColor(getResources().getColor(R.color.black, null));
        } else {
            validPassword=1;
            validateAll(validPassword);
            // Passwords match and meet the requirements
            // Proceed with further logic, such as saving the password or registering the user
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
        }
    }
    private void validateAll(int validPassword){
        UserAPI userAPI = new UserAPI();
        if (validPassword==1){
                Call<Void> signupCall = userAPI.signup(username, password, displayName, selectedImage);
//                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
//                    String firebaseToken = instanceIdResult.getToken();
                    signupCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull retrofit2.Response<Void> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                //binding.editUsername.setError("Username already exists");
                            }
                        }


                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            //binding.editTextUsername.setError(getString(R.string.connection_error));
                        }
                    });
            }
        }

}

