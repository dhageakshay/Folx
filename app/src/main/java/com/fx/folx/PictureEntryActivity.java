package com.fx.folx;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fx.folx.ui.glide.GlideApp;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PictureEntryActivity extends AppCompatActivity {


    private static final String TAG = "PICTUREENTRYACTIVITY";
    public static final int REQUEST_IMAGE = 100;
    private static  final String IMAGEID = "imageid";

    private ImageView imageView1,imageView3,imageView4,imageView5,imageView6,imageView7;
    private User user;

    private Integer[] imageList = {new Integer(R.id.imageView1),new Integer(R.id.imageView3), new Integer(R.id.imageView4), new Integer(R.id.imageView5), new Integer(R.id.imageView6), new Integer(R.id.imageView)};
    private HashMap<Integer,Uri> imageUrl;


    //a Uri object to store file path
    //TODO: upload 404 error image and initialize the url here
    private Uri downloadUri = null;

    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_entry);

        Button imgContinue = findViewById(R.id.imgContinue);
        user = (User) getIntent().getSerializableExtra("New User");
        imageView1 = findViewById(R.id.imageView1);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);

        Log.d(TAG, "init id "+imageList[1]);
        Log.d(TAG, "init ida "+R.id.imageView3);

        // Firebase Stoorage Reference
        storageRef = FirebaseStorage.getInstance().getReference();

        imageUrl = new HashMap<>();

        imageView1.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[0]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());

        imageView3.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[1]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());

        imageView4.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[2]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());

        imageView5.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[3]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());

        imageView6.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[4]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());

        imageView7.setOnClickListener(v -> Dexter.withActivity(PictureEntryActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions(imageList[5]);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check());




        imgContinue.setOnClickListener(v -> {
            Intent i = new Intent(PictureEntryActivity.this, MemeEntryActivity.class);
            i.putExtra("New User",user);
            startActivity(i);
        });
    }


    private void showImagePickerOptions(Integer imageId) {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent(imageId);
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent(imageId);
            }
        });
    }

    private void launchCameraIntent(Integer imageId) {
        Intent intent = new Intent(PictureEntryActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, false);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        // Add image id to the intent so that it can be used to display image on the respective view
        Log.d(TAG, "cam id "+imageId);
        intent.putExtra(IMAGEID,imageId);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent(Integer imageId) {
        Intent intent = new Intent(PictureEntryActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // Add image id to the intent so that it can be used to display image on the respective view
        Log.d(TAG, "gallery id "+imageId);
        intent.putExtra(IMAGEID,imageId);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getParcelableExtra("path");
                }
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    if (uri != null) {
                        Log.d(TAG, "activity result id "+data.getIntExtra(IMAGEID,R.id.imageView1));
                        loadProfile(uri,data.getIntExtra(IMAGEID,R.id.imageView1));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(Uri url, Integer id) {
        Log.d(TAG, "Image cache path: " + url.toString());
        ImageView view = findViewById(id);

        view.setAlpha((float) 1.0);
        GlideApp.with(this).load(url.toString())
                .into(view);
        Uri resultUri = uploadImage(url);
        imageUrl.put(id,resultUri);
    }

    private Uri uploadImage(Uri uri){
        // Images are stored in a unique folder for every unique user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference imgRef = storageRef.child("images/"+user.getUid()+"/"+uri.getLastPathSegment());
        UploadTask task = imgRef.putFile(uri);


        // Register observers to listen for when the download is done or if it fails
        task.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Toast.makeText(PictureEntryActivity.this,"Image Failed to uppload. Please Select the image again", Toast.LENGTH_SHORT).show();
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(PictureEntryActivity.this,"Upload Success",Toast.LENGTH_SHORT).show();
            }
        });

        //Get Download URL from the storage
        Task<Uri> urlTask = task.continueWithTask(task1 -> {
            if (!task1.isSuccessful()) {
                throw Objects.requireNonNull(task1.getException());
            }

            // Continue with the task to get the download URL
            return imgRef.getDownloadUrl();
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();

                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        return downloadUri;
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PictureEntryActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}
