package com.omninos.nani.activity.userActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

public class BuyerItemDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView appbar_transparent_image1;
    private TextView text, title_app_bar_transparent, name, address, collection, itemQuantity, itemPrice, itemDescription, textAvailable, textPerItem,
            itemIngredient, itemMeal, itemPreparation, itemAllergies;
    private RatingBar rating_bar_discover_nested_recycler_item;
    private GetNaniPostModel.Detail detail;
    private int quantity;
    private RecyclerView item_desc_images_recycler;
    private FoodImageAdapter adapter;
    private Button btInc, btDec, choose_address_btn_item_des;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_item_detail);

        CommonUtils.CheckService(BuyerItemDetailActivity.this);
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

        btInc = findViewById(R.id.item_desc_increment);
        btDec = findViewById(R.id.item_desc_decrement);
        text = findViewById(R.id.item_desc_quantity);

        choose_address_btn_item_des = findViewById(R.id.choose_address_btn_item_des);

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

        adapter = new FoodImageAdapter(BuyerItemDetailActivity.this, detail.getPostImage(), new FoodImageAdapter.Choose() {
            @Override
            public void Select(String path) {
                ShowImg(path);
            }
        });
        item_desc_images_recycler.setAdapter(adapter);


        btInc.setOnClickListener(this);
        btDec.setOnClickListener(this);
        choose_address_btn_item_des.setOnClickListener(this);


        App.getSingleton().setPriceData(detail.getPrice());
        App.getSingleton().setNameData(detail.getName());

    }

    public void ShowImg(String path) {
        View customView = LayoutInflater.from(BuyerItemDetailActivity.this).inflate(R.layout.image_popup, null);
        dialog = new AlertDialog.Builder(BuyerItemDetailActivity.this).create();
        dialog.setView(customView);
        dialog.setCanceledOnTouchOutside(false);

        ImageView cancelIcon4 = customView.findViewById(R.id.cancelIcon4);
        ZoomageView ViewedImage = customView.findViewById(R.id.ViewedImage);
        Glide.with(BuyerItemDetailActivity.this).load(path).into(ViewedImage);

        cancelIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;
            case R.id.item_desc_increment:
                quantity = Integer.parseInt(text.getText().toString());
                if (quantity < Integer.parseInt(detail.getQuantity())) {
                    quantity = quantity + 1;
                    text.setText(quantity + "");
                } else {
                    Toast.makeText(this, "You can choose maximum " + detail.getQuantity() + " items", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.item_desc_decrement:
                quantity = Integer.parseInt(text.getText().toString());
                if (quantity > 1) {
                    quantity = quantity - 1;
                    text.setText(quantity + "");
                }
                break;

            case R.id.choose_address_btn_item_des:
                App.getSingleton().setQuantityData(text.getText().toString());
                App.getSingleton().setOrderPostId(detail.getId());
                App.getSingleton().setFoodImg(detail.getPostImage().get(0).getImage());
                startActivity(new Intent(BuyerItemDetailActivity.this, ChooseAddressActivity.class));
                break;

        }
    }
}
