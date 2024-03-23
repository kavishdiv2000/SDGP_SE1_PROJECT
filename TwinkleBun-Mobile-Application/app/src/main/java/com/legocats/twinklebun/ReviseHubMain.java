package com.legocats.twinklebun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviseHubMain extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private ImageView ivImage;
    private TextView tvResult;
    private Button btnSelectImage, btnTakePicture;
    private UCrop.Options uOptions;
    private TextRecognizer textRecognizer;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_hub_main);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ReviseHub");
        }

        uOptions = new UCrop.Options();
        uOptions.setToolbarColor(ContextCompat.getColor(this, R.color.primary_blue));
        uOptions.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_blue));
        uOptions.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.white));
        uOptions.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.primary_blue));


        ivImage = findViewById(R.id.ivImage);
//        tvResult = findViewById(R.id.tvResult);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnTakePicture = findViewById(R.id.btnTakePicture);

        // Initialize the Text Recognizer
        TextRecognizerOptions options = new TextRecognizerOptions.Builder().build();
        textRecognizer = TextRecognition.getClient(options);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            startCropActivity(imageUri);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            File imageFile = new File(currentPhotoPath);
            if (imageFile.exists()) {
                Uri imageUri = Uri.fromFile(imageFile);
                startCropActivity(imageUri);
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri croppedImageUri = UCrop.getOutput(data);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedImageUri);
                ivImage.setImageBitmap(bitmap);
                recognizeText(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startCropActivity(@NonNull Uri imageUri) {
        UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), "cropped.jpg")))
                .withOptions(uOptions)
                .start(this);

    }

    private void recognizeText(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        Task<com.google.mlkit.vision.text.Text> result =
                textRecognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<com.google.mlkit.vision.text.Text>() {
                            @Override
                            public void onSuccess(com.google.mlkit.vision.text.Text visionText) {
                                StringBuilder resultText = new StringBuilder();
                                for (com.google.mlkit.vision.text.Text.TextBlock block : visionText.getTextBlocks()) {
                                    resultText.append(block.getText()).append("\n");
                                }
//                                tvResult.setText(resultText.toString());
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ReviseHubMain.this, "Failed to recognize text", Toast.LENGTH_SHORT).show();
                                    }
                                });
    }
}