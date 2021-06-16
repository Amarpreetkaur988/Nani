package com.omninos.nani.activity.userActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.omninos.nani.BuildConfig;
import com.omninos.nani.R;
import com.omninos.nani.activity.ChangePasswordActivity;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.ProfileViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ImageUtil;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BuyerEditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView edit_profile_user_image;
    private EditText nameEditText, emailEditText, numberEditText;

    private ImageView appbar_transparent_image1;
    private Button save_button_edit_profile;

    private File photoFile;
    private static final int GALLERY_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    private Uri uri;
    private String imagepath, name;

    private ProfileViewModel viewModel;
    private TextView ChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_edit_profile);


        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        initView();
        SetUp();
    }

    private void initView() {
        edit_profile_user_image = findViewById(R.id.edit_profile_user_image);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);

        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);

        numberEditText = findViewById(R.id.numberEditText);

        save_button_edit_profile = findViewById(R.id.save_button_edit_profile);
        ChangePassword = findViewById(R.id.ChangePassword);

    }

    private void SetUp() {
        appbar_transparent_image1.setOnClickListener(this);
        save_button_edit_profile.setOnClickListener(this);
        edit_profile_user_image.setOnClickListener(this);
        ChangePassword.setOnClickListener(this);

        if (!App.getAppPreference().getUserDetails().getDetails().getImage().isEmpty()) {
            Glide.with(BuyerEditProfileActivity.this).load(App.getAppPreference().getUserDetails().getDetails().getImage()).into(edit_profile_user_image);
        }
        nameEditText.setText(App.getAppPreference().getUserDetails().getDetails().getName());
        emailEditText.setText(App.getAppPreference().getUserDetails().getDetails().getEmail());
        numberEditText.setText(App.getAppPreference().getUserDetails().getDetails().getPhone());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;

            case R.id.save_button_edit_profile:
                SubmitData();
                break;
            case R.id.edit_profile_user_image:
                CheckPermission();
                break;
            case R.id.ChangePassword:
                startActivity(new Intent(BuyerEditProfileActivity.this, ChangePasswordActivity.class).putExtra("Type", "2"));
                break;

        }
    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BuyerEditProfileActivity.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            return;
        } else {
            OpenImageData();
        }
    }

    private void OpenImageData() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BuyerEditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            photoFile = ImageUtil.getTemporaryCameraFile();
            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(BuyerEditProfileActivity.this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == CAMERA_REQUEST) {
                //set image path
                imagepath = ImageUtil.compressImage(photoFile.getAbsolutePath());
                Glide.with(BuyerEditProfileActivity.this).load("file://" + imagepath).into(edit_profile_user_image);
            }

            if (requestCode == 1) {
                int camera = ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.CAMERA);
                int storage = ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int Writestorage = ActivityCompat.checkSelfPermission(BuyerEditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (camera != PackageManager.PERMISSION_GRANTED ||
                        storage != PackageManager.PERMISSION_GRANTED ||
                        Writestorage != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(BuyerEditProfileActivity.this, "Mandatory permissions are not allowed", Toast.LENGTH_SHORT).show();
                    rationale();
                } else {
                    finish();
                }

            }

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            uri = data.getData();
            if (uri != null) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index_data = cursor.getColumnIndex(projection[0]);
                cursor.moveToFirst();
                imagepath = cursor.getString(column_index_data);
                Glide.with(BuyerEditProfileActivity.this).load("file://" + imagepath).into(edit_profile_user_image);
            }

        }
    }

    //Choose from gallery
    private void galleryIntent() {
        //gallery intent
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("profilePic/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    void rationale() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(BuyerEditProfileActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(BuyerEditProfileActivity.this);
        }
        builder.setTitle("Mandatory Permissions")
                .setMessage("Manually allow permissions in App settings")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void SubmitData() {
        name = nameEditText.getText().toString();
        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(appbar_transparent_image1, "enter name");
        } else {
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody specialBody = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody latBody = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody lngBody = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), App.getAppPreference().getUserDetails().getDetails().getId());

            MultipartBody.Part user_image = null;
            if (imagepath != null) {
                File file = new File(imagepath);
                final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                user_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            } else {
                final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                user_image = MultipartBody.Part.createFormData("image", "", requestFile);
            }

            viewModel.update(BuyerEditProfileActivity.this, nameBody, specialBody, addressBody, latBody, lngBody, user_image, idBody).observe(BuyerEditProfileActivity.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        App.getAppPreference().saveUserDetails(loginRegisterModelClass);
                        Toast.makeText(BuyerEditProfileActivity.this, loginRegisterModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        CommonUtils.showSnackbarAlert(nameEditText, loginRegisterModelClass.getMessage());
                    }
                }
            });
        }
    }
}
