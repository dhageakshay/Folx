package com.fx.folx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fx.folx.ui.glide.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.fx.folx.PictureEntryActivity.REQUEST_IMAGE;


public class ProfileActivity extends AppCompatActivity {

    private final String TAG="PROFILEACTIVITY";
    private static  final String IMAGEID = "imageid";


    private TextView moreText;
    private TextView nameText,professionText,companyText,universityText,ageText;
    private Switch maleSwitch,femaleSwitch,genderXSwitch;
    private ImageButton editProfession,editCompany,editUniversity,saveProfession,saveCompany,saveUniversity;
    private EditText professionEditText,companyEditText,universityEditText;
    private ArrayList<String> sexualOrientation, imageList;
    private ArrayList<ImageView> imageSetterList;
    private ImageView imageDp, profileImageView1,profileImageView2,profileImageView3,profileImageView4,profileImageView5,profileImageView6;

    private Integer[] imageListId = {new Integer(R.id.profileImg1),new Integer(R.id.profileImg2), new Integer(R.id.profileImg3), new Integer(R.id.profileImg4), new Integer(R.id.profileImg5), new Integer(R.id.profileImg6)};


    private String name,profession,company,university,email,phone;
    private Date dob;


    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference storageRef;
    private FirebaseUser user;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        moreText = findViewById(R.id.profileMore);
        nameText = findViewById(R.id.profileName);
        ageText = findViewById(R.id.profileAgeText);
        professionText = findViewById(R.id.profileProfession);
        companyText = findViewById(R.id.profileCompany);
        universityText = findViewById(R.id.profileUniversity);
        maleSwitch = findViewById(R.id.maleSwitch);
        femaleSwitch = findViewById(R.id.femaleSwitch);
        genderXSwitch = findViewById(R.id.theXSwitch);
        editProfession = findViewById(R.id.editProfession);
        editCompany = findViewById(R.id.editCompany);
        editUniversity = findViewById(R.id.editUniversity);
        saveProfession = findViewById(R.id.saveProfession);
        saveCompany = findViewById(R.id.saveCompany);
        saveUniversity = findViewById(R.id.saveUniversity);
        professionEditText = findViewById(R.id.profileProfessionEditText);
        companyEditText = findViewById(R.id.profileCompanyEditText);
        universityEditText = findViewById(R.id.profileUniversityEditText);
        imageDp = findViewById(R.id.imageView2);
        profileImageView1 = findViewById(R.id.profileImg1);
        profileImageView2 = findViewById(R.id.profileImg2);
        profileImageView3 = findViewById(R.id.profileImg3);
        profileImageView4 = findViewById(R.id.profileImg4);
        profileImageView5 = findViewById(R.id.profileImg5);
        profileImageView6 = findViewById(R.id.profileImg6);

        imageSetterList = new ArrayList<>();
        imageSetterList.add(profileImageView1);
        imageSetterList.add(profileImageView2);
        imageSetterList.add(profileImageView3);
        imageSetterList.add(profileImageView4);
        imageSetterList.add(profileImageView5);
        imageSetterList.add(profileImageView6);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();

        settings = getSharedPreferences("IMAGE_NAME",MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();

        Log.d(TAG,user.getEmail());

        ref = database.getReference("accounts");
         ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                sexualOrientation = new ArrayList();
                imageList = new ArrayList<>();


                email = snapshot.child("email").getValue(String.class);
                Log.d(TAG,"Email"+email);
                phone = snapshot.child("phone").getValue(String.class);
                name = snapshot.child("userName").getValue(String.class);
                profession = snapshot.child("profession").getValue(String.class);
                company = snapshot.child("company").getValue(String.class);
                university = snapshot.child("university").getValue(String.class);
                dob = snapshot.child("dob").getValue(Date.class);
                if(snapshot.child("sexualOrientation").getValue()!=null){
                    for(DataSnapshot o: snapshot.child("sexualOrientation").getChildren()){
                        sexualOrientation.add(o.getValue(String.class));
                    }
                }

                if(snapshot.child("imageList").getValue()!=null){
                    for(DataSnapshot o: snapshot.child("imageList").getChildren()){
                        imageList.add(o.getValue(String.class));
                    }
                }

                for(int counter = 0;counter<imageList.size();counter++){
                    imageSetterList.get(counter).setAlpha((float) 1.0);
                    GlideApp.with(ProfileActivity.this).load(imageList.get(counter).toString())
                            .into(imageSetterList.get(counter));
                }
                GlideApp.with(ProfileActivity.this).load(imageList.get(0))
                        .circleCrop().into(imageDp);
                imageDp.setBackground(null);

                Log.d(TAG,"dob"+dob);
                Log.d(TAG,"name "+name);


              nameText.setText(name);
              professionText.setText(profession);
              companyText.setText(company);
              universityText.setText(university);
              ageText.setText(String.valueOf(calculateAge(dob.getTime())));
              for(Object o :sexualOrientation){
                  if(o.equals("Male")){
                      maleSwitch.setChecked(true);
                  }
                  if(o.equals("Female")){
                      femaleSwitch.setChecked(true);
                  }
                  if(o.equals("GenderX")){
                      genderXSwitch.setChecked(true);
                  }
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profileImageView1.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[0]);
                            editor.commit();
                            showImagePickerOptions();
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

        profileImageView2.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[1]);
                            editor.commit();
                            showImagePickerOptions();
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

        profileImageView3.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[2]);
                            editor.commit();
                            showImagePickerOptions();
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

        profileImageView4.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[3]);
                            editor.commit();
                            showImagePickerOptions();
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

        profileImageView5.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[4]);
                            editor.commit();
                            showImagePickerOptions();
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

        profileImageView6.setOnClickListener(v -> Dexter.withActivity(ProfileActivity.this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            editor.putInt(IMAGEID,imageListId[5]);
                            editor.commit();
                            showImagePickerOptions();
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


        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MoreActivity.class));
            }
        });


        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("Male")){
                    sexualOrientation.add("Male");
                }
                else if(!isChecked){
                    sexualOrientation.remove("Male");
                }
                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });

        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("Female")){
                    sexualOrientation.add("Female");
                }
                else if(!isChecked){
                    sexualOrientation.remove("Female");
                }

                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });

        genderXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("GenderX")){
                    sexualOrientation.add("GenderX");
                }
                else if(!isChecked){
                    sexualOrientation.remove("GenderX");
                }
                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });


        editProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionText.setVisibility(View.GONE);
                editProfession.setVisibility(View.GONE);
                saveProfession.setVisibility(View.VISIBLE);
                professionEditText.setVisibility(View.VISIBLE);
                professionEditText.setText(professionText.getText());
            }
        });

        saveProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profession = professionEditText.getText().toString();
                if(TextUtils.isEmpty(profession)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    profession = professionText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("profession").setValue(profession);
                }

                saveProfession.setVisibility(View.GONE);
                professionEditText.setVisibility(View.GONE);
                professionText.setVisibility(View.VISIBLE);
                editProfession.setVisibility(View.VISIBLE);
                professionText.setText(profession);
            }
        });


        editCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyText.setVisibility(View.GONE);
                editCompany.setVisibility(View.GONE);
                saveCompany.setVisibility(View.VISIBLE);
                companyEditText.setVisibility(View.VISIBLE);
                companyEditText.setText(companyText.getText());
            }
        });

        saveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company = companyEditText.getText().toString();
                if(TextUtils.isEmpty(company)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    company = companyText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("company").setValue(company);
                }

                saveCompany.setVisibility(View.GONE);
                companyEditText.setVisibility(View.GONE);
                companyText.setVisibility(View.VISIBLE);
                editCompany.setVisibility(View.VISIBLE);
                companyText.setText(company);
            }
        });

        editUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universityText.setVisibility(View.GONE);
                editUniversity.setVisibility(View.GONE);
                saveUniversity.setVisibility(View.VISIBLE);
                universityEditText.setVisibility(View.VISIBLE);
                universityEditText.setText(universityText.getText());
            }
        });

        saveUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                university = universityEditText.getText().toString();
                if(TextUtils.isEmpty(university)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    university = universityText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("university").setValue(university);
                }

                saveUniversity.setVisibility(View.GONE);
                universityEditText.setVisibility(View.GONE);
                universityText.setVisibility(View.VISIBLE);
                editUniversity.setVisibility(View.VISIBLE);
                universityText.setText(university);
            }
        });



    }

    private int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(ProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, false);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(ProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

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
                        loadProfile(uri);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(Uri url) {
        Integer idx = getSharedPreferences("IMAGE_NAME",MODE_PRIVATE).getInt(IMAGEID,R.id.profileImg1);
        Log.d(TAG, "Image cache path: " + url.toString());
        Log.d(TAG, "Image view id: " + idx);
        ImageView view = findViewById(idx);

        view.setAlpha((float) 1.0);
        GlideApp.with(this).load(url.toString())
                .into(view);
        uploadImage(url);
    }

    private void uploadImage(Uri uri){
        // Images are stored in a unique folder for every unique user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference imgRef = storageRef.child("images/"+user.getUid()+"/"+uri.getLastPathSegment());
        UploadTask task = imgRef.putFile(uri);


        // Register observers to listen for when the download is done or if it fails
        task.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Toast.makeText(ProfileActivity.this,"Image Failed to uppload. Please Select the image again", Toast.LENGTH_SHORT).show();
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(ProfileActivity.this,"Upload Success",Toast.LENGTH_SHORT).show();
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
                    Uri downloadUri = task.getResult();
                    Log.d(TAG, "PicUrl "+downloadUri);
                    imageList.add(downloadUri.toString());
                    ref.child(user.getUid()+"/imageList").setValue(imageList);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
