package com.omninos.nani.fragments.naniFragments;


import android.Manifest;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.BuildConfig;
import com.omninos.nani.R;
import com.omninos.nani.adapter.AddItemRecyclerAdapter;
import com.omninos.nani.adapter.AllergiesAdapter;
import com.omninos.nani.modelClass.AllergiesModel;
import com.omninos.nani.modelClass.MyAllergiesModel;
import com.omninos.nani.modelClass.MySpecialityModel;
import com.omninos.nani.myViewModel.AddPostViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ImageUtil;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment implements View.OnClickListener {

    private CheckBox contains_meat;
    private LinearLayout meat_products;
    private RecyclerView addItemRecycler, allergiesRecyclerView;
    private int quantity;
    private TextView Quantity;
    private RelativeLayout minus, add;
    private ImageView add_item_upload_image;


    private File photoFile;
    private static final int GALLERY_REQUEST = 101;
    private static final int CAMERA_REQUEST = 102;
    private Uri uri;
    private String imagepath, SpecialId;
    private List<String> imageList = new ArrayList<>();
    private AddItemRecyclerAdapter adapter;
    private Button post_button_add_item;

    //    private AddPostViewModel viewModel;
    private Spinner listdata, allergies_et, collectionDay;

    private List<String> listIds = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private List<String> AllergiesList = new ArrayList<>();
    private List<String> AllergiesIds = new ArrayList<>();

    private AddPostViewModel viewModel;
    private AllergiesAdapter specialityAdapter;

    private EditText item_name_et, item_description_et, quanity_per_unit_et, price_per_unit_et, list_of_ingredients_et, source_of_meat_products_et, preparation_et;
    private String name, description, QuantityperUnit, PricePerUnit, Ingredient, MealProduction = "", Prepration, Allegies, CollactionDay = "";
    private List<MyAllergiesModel> myAllergiesList = new ArrayList<>();
    private List<String> idsList = new ArrayList<>();
    private List<String> positionList = new ArrayList<>();


    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        viewModel = ViewModelProviders.of(this).get(AddPostViewModel.class);
        initView(view);

        Setups();
        getListData();
        SetUpRecyclerView(view);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_item_upload_image:
//                OpenImageData();
                CheckPermission();
                break;
            case R.id.post_button_add_item:
//                String data = TextUtils.join(",", idsList);
//                System.out.println("Data: " + data);
                AddPost();
                break;
        }

    }

    private void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
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
                    Toast.makeText(getActivity(), "Permission Not granted", Toast.LENGTH_SHORT).show();
                    CheckPermission();
                }
            }
        }
    }

    void rationale() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Mandatory Permissions")
                .setMessage("Manually allow permissions in App settings")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void AddPost() {
        name = item_name_et.getText().toString();
        description = item_description_et.getText().toString();
        QuantityperUnit = quanity_per_unit_et.getText().toString();
        PricePerUnit = price_per_unit_et.getText().toString();
        Ingredient = list_of_ingredients_et.getText().toString();
        MealProduction = source_of_meat_products_et.getText().toString();
        Prepration = preparation_et.getText().toString();
        String data = TextUtils.join(",", idsList);
//        Allegies = allergies_et.getText().toString();

        if (imageList.isEmpty()) {
            CommonUtils.showSnackbarAlert(item_description_et, "Choose item images");
        } else if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(item_name_et, "enter title");
        } else if (description.isEmpty()) {
            CommonUtils.showSnackbarAlert(item_description_et, "enter description");
        } else if (CollactionDay.equalsIgnoreCase("-Select-")) {
            CommonUtils.showSnackbarAlert(item_description_et, "Choose Collection of item Available");
        } else if (QuantityperUnit.isEmpty()) {
            CommonUtils.showSnackbarAlert(quanity_per_unit_et, "enter quantity");
        } else if (PricePerUnit.isEmpty()) {
            CommonUtils.showSnackbarAlert(quanity_per_unit_et, "enter price per item");
        } else if (Ingredient.isEmpty()) {
            CommonUtils.showSnackbarAlert(list_of_ingredients_et, "enter integedients");
        } else if (Prepration.isEmpty()) {
            CommonUtils.showSnackbarAlert(preparation_et, "enter preparation");
        } else if (data.isEmpty()) {
            CommonUtils.showSnackbarAlert(allergies_et, "choose allergies");
        } else {


            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
            RequestBody collectionBody = RequestBody.create(MediaType.parse("text/plain"), CollactionDay);
            RequestBody MeasureBody = RequestBody.create(MediaType.parse("text/plain"), QuantityperUnit);
            RequestBody PriceBody = RequestBody.create(MediaType.parse("text/plain"), PricePerUnit);
            RequestBody integradient = RequestBody.create(MediaType.parse("text/plain"), Ingredient);
            RequestBody QuantityBody = RequestBody.create(MediaType.parse("text/plain"), Quantity.getText().toString());
            RequestBody MealRequest = RequestBody.create(MediaType.parse("text/plain"), MealProduction);
            RequestBody PreprationBody = RequestBody.create(MediaType.parse("text/plain"), Prepration);
            RequestBody AllegiesBody = RequestBody.create(MediaType.parse("text/plain"), data);
            RequestBody SpecialIdBody = RequestBody.create(MediaType.parse("text/plain"), SpecialId);
            RequestBody naniIdBody = RequestBody.create(MediaType.parse("text/palin"), App.getAppPreference().getUserDetails().getDetails().getId());

            if (imageList.size() <= 0) {
                CommonUtils.showSnackbarAlert(allergies_et, "select Images");
            } else {
                MultipartBody.Part[] imagesData = new MultipartBody.Part[imageList.size()];
                for (int i = 0; i < imageList.size(); i++) {
                    File file1 = new File(imageList.get(i));
                    final RequestBody rf1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                    imagesData[i] = MultipartBody.Part.createFormData("images[]", file1.getName(), rf1);
                }

                viewModel.post(getActivity(), naniIdBody, nameBody, descriptionBody, QuantityBody, MeasureBody, PriceBody, integradient, MealRequest, PreprationBody, AllegiesBody, collectionBody, SpecialIdBody, imagesData).observe(getActivity(), new Observer<Map>() {
                    @Override
                    public void onChanged(Map map) {
                        if (map.get("success").toString().equalsIgnoreCase("1")) {
                            Toast.makeText(getActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                            item_name_et.setText("");
                            item_description_et.setText("");
                            quanity_per_unit_et.setText("");
                            price_per_unit_et.setText("");
                            list_of_ingredients_et.setText("");
                            source_of_meat_products_et.setText("");
                            preparation_et.setText("");
                            if (imageList != null) {
                                imageList.clear();
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            CommonUtils.showSnackbarAlert(item_description_et, map.get("message").toString());
                        }
                    }
                });
            }
        }
    }


    private void getListData() {
        if (list != null) {
            list.clear();
        }

        if (listIds != null) {
            listIds.clear();
        }
        if (App.getAppPreference().getUserDetails() != null) {
            viewModel.modelLiveData(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<MySpecialityModel>() {
                @Override
                public void onChanged(@Nullable MySpecialityModel mySpecialityModel) {
                    if (mySpecialityModel.getSuccess().equalsIgnoreCase("1")) {
                        for (int i = 0; i < mySpecialityModel.getDetails().size(); i++) {
                            list.add(mySpecialityModel.getDetails().get(i).getTitle());
                            listIds.add(mySpecialityModel.getDetails().get(i).getId());
                        }


                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, list);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listdata.setAdapter(adapter1);
                        listdata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println("Data: " + parent.getItemAtPosition(position).toString());
                                System.out.println("Data: " + listIds.get(position));
                                SpecialId = listIds.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            });
        }


        if (AllergiesList != null) {
            AllergiesList.clear();
        }
        if (AllergiesIds != null) {
            AllergiesIds.clear();
        }

        viewModel.allergiesModelLiveData(getActivity()).observe(getActivity(), new Observer<AllergiesModel>() {
            @Override
            public void onChanged(@Nullable AllergiesModel allergiesModel) {
                if (allergiesModel.getSuccess().equalsIgnoreCase("1")) {
                    for (int i = 0; i < allergiesModel.getDetails().size(); i++) {
                        AllergiesList.add(allergiesModel.getDetails().get(i).getTitle());
                        AllergiesIds.add(allergiesModel.getDetails().get(i).getId());
                    }


                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, AllergiesList);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    allergies_et.setAdapter(adapter1);
                    allergies_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("Data: " + parent.getItemAtPosition(position).toString());
                            System.out.println("Data: " + AllergiesIds.get(position));
                            if (position != 0) {
                                if (positionList.contains("0")) {
                                    if (myAllergiesList != null) {
                                        myAllergiesList.clear();
                                        positionList.clear();
                                        idsList.clear();
                                    }
                                    Allegies = AllergiesIds.get(position);
                                    positionList.add(String.valueOf(position));
                                    MyAllergiesModel myAllergiesModel = new MyAllergiesModel(parent.getItemAtPosition(position).toString(), AllergiesIds.get(position));
                                    myAllergiesList.add(myAllergiesModel);
                                    idsList.add(AllergiesIds.get(position));
                                    specialityAdapter.notifyDataSetChanged();
                                } else {
                                    Allegies = AllergiesIds.get(position);
                                    positionList.add(String.valueOf(position));
                                    MyAllergiesModel myAllergiesModel = new MyAllergiesModel(parent.getItemAtPosition(position).toString(), AllergiesIds.get(position));
                                    myAllergiesList.add(myAllergiesModel);
                                    idsList.add(AllergiesIds.get(position));
                                    specialityAdapter.notifyDataSetChanged();
                                }

                            } else {
                                if (myAllergiesList != null) {
                                    myAllergiesList.clear();
                                    idsList.clear();
                                    positionList.clear();
                                }
                                Allegies = AllergiesIds.get(position);
                                positionList.add(String.valueOf(position));
                                MyAllergiesModel myAllergiesModel = new MyAllergiesModel(parent.getItemAtPosition(position).toString(), AllergiesIds.get(position));
                                myAllergiesList.add(myAllergiesModel);
                                idsList.add(AllergiesIds.get(position));
                                specialityAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
        });


    }

    private void SetUpRecyclerView(View view) {
        addItemRecycler = view.findViewById(R.id.add_item_recycler_view);
        addItemRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddItemRecyclerAdapter(getActivity(), imageList, new AddItemRecyclerAdapter.CloseImage() {
            @Override
            public void Select(String url) {
                if (imageList.contains(url)) {
                    imageList.remove(url);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        addItemRecycler.setAdapter(adapter);

        allergiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        specialityAdapter = new AllergiesAdapter(getActivity(), myAllergiesList, new AllergiesAdapter.Choose() {
            @Override
            public void Select(MyAllergiesModel myAllergiesModel, String id) {
                if (myAllergiesList.contains(myAllergiesModel)) {
                    myAllergiesList.remove(myAllergiesModel);
                    idsList.remove(id);
                }
                specialityAdapter.notifyDataSetChanged();
            }
        });

        allergiesRecyclerView.setAdapter(specialityAdapter);

    }

    private void Setups() {
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(Quantity.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    Quantity.setText(String.valueOf(quantity));
                } else {
                    Toast.makeText(getContext(), "Least", Toast.LENGTH_SHORT).show();
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(Quantity.getText().toString());
                quantity++;
                Quantity.setText(String.valueOf(quantity));
            }
        });


        contains_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contains_meat.isChecked()) {
                    meat_products.setVisibility(View.VISIBLE);
                } else {
                    meat_products.setVisibility(View.GONE);


                }
            }
        });

        add_item_upload_image.setOnClickListener(this);

        post_button_add_item.setOnClickListener(this);

        collectionDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CollactionDay = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View view) {

        contains_meat = view.findViewById(R.id.contains_meat_products_check);
        meat_products = view.findViewById(R.id.contains_meat_products_layout);
        Quantity = view.findViewById(R.id.quantity_t_add_item);
        minus = view.findViewById(R.id.minus_quantity_add_item);
        add = view.findViewById(R.id.add_quantity_add_item);


        add_item_upload_image = view.findViewById(R.id.add_item_upload_image);
        item_name_et = view.findViewById(R.id.item_name_et);
        item_description_et = view.findViewById(R.id.item_description_et);
        quanity_per_unit_et = view.findViewById(R.id.quanity_per_unit_et);
        price_per_unit_et = view.findViewById(R.id.price_per_unit_et);
        list_of_ingredients_et = view.findViewById(R.id.list_of_ingredients_et);
        source_of_meat_products_et = view.findViewById(R.id.source_of_meat_products_et);
        preparation_et = view.findViewById(R.id.preparation_et);
        allergies_et = view.findViewById(R.id.allergies_et);

        post_button_add_item = view.findViewById(R.id.post_button_add_item);

        listdata = view.findViewById(R.id.listdata);
        collectionDay = view.findViewById(R.id.collectionDay);

        allergiesRecyclerView = view.findViewById(R.id.allergiesRecyclerView);


    }


    private void OpenImageData() {
//        final CharSequence[] items = {"Take Photo", "Choose from Library",
//                "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("Take Photo")) {
//                    cameraIntent();
//
//                } else if (items[item].equals("Choose from Library")) {
//                    galleryIntent();
//
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(getActivity());
        CropImage.activity().start(getContext(), AddItemFragment.this);

    }

    private void cameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //Create a file to store the image
            photoFile = null;
            photoFile = ImageUtil.getTemporaryCameraFile();
            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST);
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

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == GALLERY_REQUEST) {
//                onSelectFromGalleryResult(data);
//            } else if (requestCode == CAMERA_REQUEST) {
//                //set image path
//                imagepath = ImageUtil.compressImage(photoFile.getAbsolutePath());
//                //App.getAppPreference().SaveString(activity, ConstantData.IMAGEPATH, ImageUtil.compressImage(photoFile.getAbsolutePath()));
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//                Bitmap mBitmapInsurance = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
//                //set image preview on imageView
//                Matrix matrix = new Matrix();
//                matrix.postRotate(90);
//                Bitmap rotated = Bitmap.createBitmap(mBitmapInsurance, 0, 0, mBitmapInsurance.getWidth(), mBitmapInsurance.getHeight(),
//                        matrix, true);
//                imageList.add(imagepath);
//                adapter.notifyDataSetChanged();
//            }
//
//            if (requestCode == 1) {
//                int camera = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
//                int storage = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//                int Writestorage = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                if (camera != PackageManager.PERMISSION_GRANTED ||
//                        storage != PackageManager.PERMISSION_GRANTED ||
//                        Writestorage != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getActivity(), "Mandatory permissions are not allowed", Toast.LENGTH_SHORT).show();
//                    rationale();
//                } else {
//                    getActivity().finish();
//                }
//
//            }
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                // startActivity(new Intent(MainActivity.this,ChooseA_CategoryActivity.class));
                imagepath = result.getUri().getPath();
//                Glide.with(getActivity()).load("file://" + imagepath).into(add_item_upload_image);

                imageList.add(imagepath);
                adapter.notifyDataSetChanged();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == 1) {
            int camera = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            int storage = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int Writestorage = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (camera != PackageManager.PERMISSION_GRANTED ||
                    storage != PackageManager.PERMISSION_GRANTED ||
                    Writestorage != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Mandatory permissions are not allowed", Toast.LENGTH_SHORT).show();
                rationale();
            } else {
                getActivity().finish();
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            uri = data.getData();
            if (uri != null) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                int column_index_data = cursor.getColumnIndex(projection[0]);
                cursor.moveToFirst();
                imagepath = cursor.getString(column_index_data);
                imageList.add(imagepath);
                adapter.notifyDataSetChanged();
            }

        }
    }

}
