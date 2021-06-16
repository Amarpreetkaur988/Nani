package com.omninos.nani.activity.naniActivity;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omninos.nani.BuildConfig;
import com.omninos.nani.R;
import com.omninos.nani.activity.ChangePasswordActivity;
import com.omninos.nani.activity.SearchPlaceActivity;
import com.omninos.nani.adapter.AllSpecialitiesAdapter;
import com.omninos.nani.adapter.SpecialitiesListRecyclerAdapter;
import com.omninos.nani.modelClass.GetNaniProfile;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.modelClass.SpecialitiesListClass;
import com.omninos.nani.myViewModel.ProfileViewModel;
import com.omninos.nani.myViewModel.SpecialitiesViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NaniEditProfile extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText, addressEditText;
    private CircleImageView edit_profile_user_image;
    private ProfileViewModel viewModel;
    private ImageView appbar_transparent_image1;
    private String latitude, longitude, name, address;
    private TextView ChangePasswordCardView;

    private RecyclerView specialities_list_recycler, AllSpecialitiesCycle;


    private Button save_button_edit_profile;

    private File photoFile;
    private static final int GALLERY_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    private Uri uri;
    private String imagepath;

    //specialities

    private List<String> specialitiesIds = new ArrayList<>();
    private List<SpecialitiesListClass.Detail> SpecialitiesPojoClassList = new ArrayList<>();
    private List<SpecialitiesListClass.Detail> SpecialitiesdetailList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private SpecialitiesListRecyclerAdapter specialitiesListRecyclerAdapter;
    private AllSpecialitiesAdapter allSpecialitiesAdapter;
    private EditText search;
    private TextView searchTextView;

    private SpecialitiesViewModel specialitiesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_nani_edit_profile);

        CommonUtils.CheckService(NaniEditProfile.this);
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        specialitiesViewModel = ViewModelProviders.of(this).get(SpecialitiesViewModel.class);
        initView();
        SetUp();

        getSpecialitiesList();

    }

    private void initView() {
        App.getSingleton().setNaniAddress(null);
        App.getSingleton().setNaniLng(null);
        App.getSingleton().setNaniLat(null);
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        edit_profile_user_image = findViewById(R.id.edit_profile_user_image);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);


        specialities_list_recycler = findViewById(R.id.specialities_list_recycler);
        AllSpecialitiesCycle = findViewById(R.id.AllSpecialitiesCycle);
        search = findViewById(R.id.search);
        searchTextView = findViewById(R.id.searchTextView);
        save_button_edit_profile = findViewById(R.id.save_button_edit_profile);

        ChangePasswordCardView = findViewById(R.id.ChangePassword);


    }

    private void SetUp() {
        searchTextView.setOnClickListener(this);
        addressEditText.setOnClickListener(this);
        edit_profile_user_image.setOnClickListener(this);
        appbar_transparent_image1.setOnClickListener(this);
        save_button_edit_profile.setOnClickListener(this);
        ChangePasswordCardView.setOnClickListener(this);

        viewModel.mySpecialityModelLiveData(NaniEditProfile.this, App.getAppPreference().getUserDetails().getDetails().getId()).observe(NaniEditProfile.this, new Observer<GetNaniProfile>() {
            @Override
            public void onChanged(GetNaniProfile getNaniProfile) {
                if (getNaniProfile.getSuccess().equalsIgnoreCase("1")) {
                    List<String> idsList = new ArrayList<String>(Arrays.asList(getNaniProfile.getDetails().getSpecialities().split(",")));
                    List<String> nameList = new ArrayList<String>(Arrays.asList(getNaniProfile.getDetails().getSpecialitiesss().split(",")));
                    for (int i = 0; i < idsList.size(); i++) {
                        specialitiesIds.add(idsList.get(i));
                    }

                    for (int i = 0; i < nameList.size(); i++) {
                        list.add(nameList.get(i));
                    }


                    nameEditText.setText(getNaniProfile.getDetails().getName());
                    addressEditText.setText(getNaniProfile.getDetails().getAddress());
                    latitude = getNaniProfile.getDetails().getLatitude();
                    longitude = getNaniProfile.getDetails().getLongitude();
                    if (!getNaniProfile.getDetails().getImage().isEmpty()) {
                        Glide.with(NaniEditProfile.this).load(getNaniProfile.getDetails().getImage()).into(edit_profile_user_image);
                    }

                    specialitiesListRecyclerAdapter.notifyDataSetChanged();

                } else {
                    CommonUtils.showSnackbarAlert(nameEditText, getNaniProfile.getMessage());
                }
            }
        });


        specialitiesListRecyclerAdapter = new SpecialitiesListRecyclerAdapter(this, list, new SpecialitiesListRecyclerAdapter.RemoveItem() {
            @Override
            public void Remove(int position) {
                list.remove(list.get(position));
                specialitiesIds.remove(specialitiesIds.get(position));
                specialitiesListRecyclerAdapter.notifyDataSetChanged();
            }
        });
        specialities_list_recycler.setAdapter(specialitiesListRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        specialities_list_recycler.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AllSpecialitiesCycle.setLayoutManager(linearLayoutManager1);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                if (editable.toString().isEmpty()) {
                    SpecialitiesdetailList = SpecialitiesPojoClassList;
                    AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                    allSpecialitiesAdapter.notifyDataSetChanged();
                } else {
                    AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                    filter(editable.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input


            }
        });
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        List<SpecialitiesListClass.Detail> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SpecialitiesListClass.Detail s : SpecialitiesPojoClassList) {
            //if the existing elements contains the search input
            if (s.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        SpecialitiesdetailList = filterdNames;
        //calling a method of the adapter class and passing the filtered list
        allSpecialitiesAdapter.filterList(filterdNames);
    }


    private void getSpecialitiesList() {
        specialitiesViewModel.specialitiesList().observe(this, new Observer<SpecialitiesListClass>() {
            @Override
            public void onChanged(@Nullable final SpecialitiesListClass specialitiesListClass) {
                if (specialitiesListClass.getSuccess().equalsIgnoreCase("1")) {

                    SpecialitiesPojoClassList = specialitiesListClass.getDetails();
                    SpecialitiesdetailList = specialitiesListClass.getDetails();

                    allSpecialitiesAdapter = new AllSpecialitiesAdapter(NaniEditProfile.this, SpecialitiesdetailList, new AllSpecialitiesAdapter.SelectItems() {
                        @Override
                        public void Select(String position, String ids) {

                            if (list.contains(position)) {
                                CommonUtils.showSnackbarAlert(search, "Already Added To Your Specialities.");
                            } else {
                                list.add(position);
                                specialitiesIds.add(ids);
                            }
                            specialitiesListRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    AllSpecialitiesCycle.setAdapter(allSpecialitiesAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;

            case R.id.addressEditText:
                startActivity(new Intent(NaniEditProfile.this, SearchPlaceActivity.class));
                break;
            case R.id.searchTextView:
                searchTextView.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_profile_user_image:
                CheckPermission();
                break;
            case R.id.save_button_edit_profile:
                SaveEditData();
                break;

            case R.id.ChangePassword:
                startActivity(new Intent(NaniEditProfile.this, ChangePasswordActivity.class).putExtra("Type", "1"));
                break;
        }
    }

    private void SaveEditData() {
        name = nameEditText.getText().toString();
        address = addressEditText.getText().toString();
        String allData = TextUtils.join(",", specialitiesIds);
        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter name");
        } else if (address.isEmpty()) {
            CommonUtils.showSnackbarAlert(addressEditText, "enter address");
        } else if (allData.isEmpty()) {
            CommonUtils.showSnackbarAlert(addressEditText, "choose specialities");
        } else {
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), address);
            RequestBody specialBody = RequestBody.create(MediaType.parse("text/plain"), allData);
            RequestBody latBody = RequestBody.create(MediaType.parse("text/plain"), latitude);
            RequestBody lngBody = RequestBody.create(MediaType.parse("text/plain"), longitude);
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

            viewModel.update(NaniEditProfile.this, nameBody, specialBody, addressBody, latBody, lngBody, user_image, idBody).observe(NaniEditProfile.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        App.getAppPreference().saveUserDetails(loginRegisterModelClass);
                        Toast.makeText(NaniEditProfile.this, loginRegisterModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        CommonUtils.showSnackbarAlert(nameEditText, loginRegisterModelClass.getMessage());
                    }
                }
            });
        }
    }


    private void OpenImageData() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NaniEditProfile.this);
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
                Uri uri = FileProvider.getUriForFile(NaniEditProfile.this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
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
                Glide.with(NaniEditProfile.this).load("file://" + imagepath).into(edit_profile_user_image);
            }

            if (requestCode == 1) {
                int camera = ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.CAMERA);
                int storage = ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int Writestorage = ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (camera != PackageManager.PERMISSION_GRANTED ||
                        storage != PackageManager.PERMISSION_GRANTED ||
                        Writestorage != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NaniEditProfile.this, "Mandatory permissions are not allowed", Toast.LENGTH_SHORT).show();
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
                Glide.with(NaniEditProfile.this).load("file://" + imagepath).into(edit_profile_user_image);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getSingleton().getNaniAddress() != null) {
            addressEditText.setText(App.getSingleton().getNaniAddress());
            latitude = App.getSingleton().getNaniLat();
            longitude = App.getSingleton().getNaniLng();
        }
    }


    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(NaniEditProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NaniEditProfile.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            return;
        } else {
            OpenImageData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                int count = 0;
                if (grantResults.length > 0)
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            count++;
                    }
//
                if (count == grantResults.length) {
                    OpenImageData();
                } else if ((Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(permissions[0])
                        && !shouldShowRequestPermissionRationale(permissions[1])
                        && !shouldShowRequestPermissionRationale(permissions[2]))) {
                    rationale();
                } else {
                    Toast.makeText(NaniEditProfile.this, "Permission Not granted", Toast.LENGTH_SHORT).show();
                    CheckPermission();
                }
            }
        }
    }

    void rationale() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(NaniEditProfile.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(NaniEditProfile.this);
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
}
