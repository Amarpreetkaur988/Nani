package com.omninos.nani.activity.naniActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.omninos.nani.R;
import com.omninos.nani.adapter.FoodImageAdapter;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.utils.CommonUtils;

public class ItemDescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView appbar_transparent_image1;
    private TextView title_app_bar_transparent, name, address, collection, itemQuantity, itemPrice, itemDescription, textAvailable, textPerItem,
            itemIngredient, itemMeal, itemPreparation, itemAllergies;
    private RatingBar rating_bar_discover_nested_recycler_item;
    private GetNaniPostModel.Detail detail;

    private RecyclerView item_desc_images_recycler;
    private FoodImageAdapter adapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_item_description);

        CommonUtils.CheckService(ItemDescriptionActivity.this);
        initView();
        SetUp();
    }

    private void initView() {
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
        title_app_bar_transparent = findViewById(R.id.title_app_bar_transparent);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        collection = findViewById(R.id.collection);
        itemQuantity = findViewById(R.id.itemQuantity);
        itemPrice = findViewById(R.id.itemPrice);
        rating_bar_discover_nested_recycler_item = findViewById(R.id.rating_bar_discover_nested_recycler_item);
        itemDescription = findViewById(R.id.itemDescription);
        textAvailable = findViewById(R.id.textAvailable);
        textPerItem = findViewById(R.id.textPerItem);
        itemIngredient = findViewById(R.id.itemIngredient);
        itemMeal = findViewById(R.id.itemMeal);
        itemPreparation = findViewById(R.id.itemPreparation);
        itemAllergies = findViewById(R.id.itemAllergies);

        item_desc_images_recycler = findViewById(R.id.item_desc_images_recycler);


    }

    private void SetUp() {
        detail = (GetNaniPostModel.Detail) getIntent().getSerializableExtra("Details");
        appbar_transparent_image1.setOnClickListener(this);
        title_app_bar_transparent.setText("Food Detail");

        name.setText(detail.getName());
        address.setText(detail.getNaniAddress());
        collection.setText("Collection/Delivery :" + detail.getCollectionDay());
        itemQuantity.setText("1 Item");
        itemPrice.setText("R " + detail.getPrice());
        rating_bar_discover_nested_recycler_item.setRating(Float.parseFloat(detail.getRating()));
        itemDescription.setText(detail.getDescription());
        textAvailable.setText(detail.getQuantity() + " Items");
        textPerItem.setText(detail.getUnitOfMesurement());
        itemIngredient.setText(detail.getIngredints());

        if (detail.getMeatProduct().isEmpty()) {
            itemMeal.setText("No Meat Contents");
        } else {
            itemMeal.setText(detail.getMeatProduct());
        }
        itemPreparation.setText(detail.getPreparationInstructions());
        itemAllergies.setText(detail.getAllergyTitle());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        item_desc_images_recycler.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(item_desc_images_recycler);

        adapter = new FoodImageAdapter(ItemDescriptionActivity.this, detail.getPostImage(), new FoodImageAdapter.Choose() {
            @Override
            public void Select(String path) {
                ShowImg(path);
            }
        });
        item_desc_images_recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;
        }
    }

    public void ShowImg(String path) {
        View customView = LayoutInflater.from(ItemDescriptionActivity.this).inflate(R.layout.image_popup, null);
        dialog = new AlertDialog.Builder(ItemDescriptionActivity.this).create();
        dialog.setView(customView);
        dialog.setCanceledOnTouchOutside(false);

        ImageView cancelIcon4 = customView.findViewById(R.id.cancelIcon4);
        ZoomageView ViewedImage = customView.findViewById(R.id.ViewedImage);
        Glide.with(ItemDescriptionActivity.this).load(path).into(ViewedImage);

        cancelIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
