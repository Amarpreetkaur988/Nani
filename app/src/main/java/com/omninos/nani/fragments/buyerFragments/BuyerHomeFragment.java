package com.omninos.nani.fragments.buyerFragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.cache_database.InternalStorage;
import com.omninos.nani.R;
import com.omninos.nani.activity.userActivity.BuyerItemDetailActivity;
import com.omninos.nani.activity.userActivity.SubscribeNaniActivity;
import com.omninos.nani.adapter.BuyerPostAdapter;
import com.omninos.nani.adapter.MyScribeAdapter;
import com.omninos.nani.modelClass.CheckRatingModel;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.myViewModel.PostViewModel;
import com.omninos.nani.myViewModel.RatingViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BuyerHomeFragment extends Fragment {

    private RecyclerView home_frag_feed_horizontal_recycler_view, home_frag_feed_vertical_recycler_view;

    private BuyerPostAdapter adapter;
    private MyScribeAdapter subscriptionPostAdapter;


    private PostViewModel viewModel;
    private TextView subText;

    private RatingViewModel ratingViewModel;
    private TextView mainData;
    String rat;



    public BuyerHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_home, container, false);
        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        ratingViewModel = ViewModelProviders.of(this).get(RatingViewModel.class);
        initView(view);
        SetUp(view);

        return view;
    }

    private void initView(View view) {
        home_frag_feed_vertical_recycler_view = view.findViewById(R.id.home_frag_feed_vertical_recycler_view);
        home_frag_feed_horizontal_recycler_view = view.findViewById(R.id.home_frag_feed_horizontal_recycler_view);
        mainData = view.findViewById(R.id.mainData);
        subText = view.findViewById(R.id.subText);
    }

    private void SetUp(View view) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_frag_feed_vertical_recycler_view.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        home_frag_feed_horizontal_recycler_view.setLayoutManager(linearLayoutManager1);

        checkrating();



    }

    private void checkrating() {
        ratingViewModel.checkRatingModelLiveData(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<CheckRatingModel>() {
            @Override
            public void onChanged(CheckRatingModel checkRatingModel) {
                if (checkRatingModel.getSuccess().equalsIgnoreCase("1")) {
                    ShowBox(checkRatingModel.getDetails());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSubscriptionList();
    }

    private void ShowBox(final CheckRatingModel.Details details) {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View congDialogBox = factory.inflate(R.layout.rating_pop, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        RoundedImageView imageData = congDialogBox.findViewById(R.id.imageData);
        TextView dishName = congDialogBox.findViewById(R.id.dishName);
        final RatingBar ratingData = congDialogBox.findViewById(R.id.ratingData);
        dishName.setText(details.getName());
        List<String> list = new ArrayList<String>(Arrays.asList(details.getImages().split(",")));
        Glide.with(getActivity()).load(list.get(0)).into(imageData);
        imageData.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Button submitButton = congDialogBox.findViewById(R.id.submitButton);
        dialog.setView(congDialogBox);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ratingData.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rat = String.valueOf(ratingData.getRating());
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GiveRate(details.getBookingId(), rat);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void GiveRate(String id, String rat) {
        ratingViewModel.RatingData(getActivity(), id, rat).observe(getActivity(), new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                CommonUtils.ShowMsg(getActivity(), map.get("message").toString());
            }
        });
    }

    private void getSubscriptionList() {
        viewModel.getSubscribedNaniPosts(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<GetNaniPostModel>() {
            @Override
            public void onChanged(final GetNaniPostModel getNaniPostModel) {
                if (getNaniPostModel.getSuccess().equalsIgnoreCase("1")) {
                    mainData.setVisibility(View.GONE);
                    home_frag_feed_vertical_recycler_view.setVisibility(View.VISIBLE);
                    home_frag_feed_horizontal_recycler_view.setVisibility(View.VISIBLE);
                    subText.setVisibility(View.VISIBLE);

                    getList(getNaniPostModel.getDetails().get(0).getNaniId());

                    subscriptionPostAdapter = new MyScribeAdapter(getActivity(), getNaniPostModel.getDetails(), new MyScribeAdapter.Choose() {
                        @Override
                        public void Select(int position) {
                            getList(getNaniPostModel.getDetails().get(position).getNaniId());
                        }
                    });
//                    subscriptionPostAdapter = new BuyerSubscriptionPostAdapter(getActivity(), getNaniPostModel.getDetails(), new BuyerPostAdapter.Choose() {
//                        @Override
//                        public void Select(int position) {
//
//                        }
//
//                        @Override
//                        public void Subscribe(int position) {
//
//                        }
//                    });
                    home_frag_feed_horizontal_recycler_view.setAdapter(subscriptionPostAdapter);
                } else {
                    subText.setVisibility(View.GONE);
                    mainData.setVisibility(View.VISIBLE);
                    home_frag_feed_vertical_recycler_view.setVisibility(View.GONE);
                    home_frag_feed_horizontal_recycler_view.setVisibility(View.GONE);
                }
            }
        });


    }

    private void getList(String userId) {
        viewModel.getNaniPostModelLiveData(getActivity(), userId, "").observe(getActivity(), new Observer<GetNaniPostModel>() {
            @Override
            public void onChanged(final GetNaniPostModel getNaniPostModel) {
                if (getNaniPostModel.getSuccess().equalsIgnoreCase("1")) {

                    adapter = new BuyerPostAdapter(getActivity(), getNaniPostModel.getDetails(), new BuyerPostAdapter.Choose() {
                        @Override
                        public void Select(int position) {
                            startActivity(new Intent(getActivity(), BuyerItemDetailActivity.class).putExtra("Details", getNaniPostModel.getDetails().get(position)));
                        }

                        @Override
                        public void Subscribe(int position) {
                            startActivity(new Intent(getActivity(), SubscribeNaniActivity.class).putExtra("Details", getNaniPostModel.getDetails().get(position)).putExtra("Type", "1"));
                        }
                    });

                    home_frag_feed_vertical_recycler_view.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(home_frag_feed_vertical_recycler_view, getNaniPostModel.getMessage());
                }
            }
        });
    }


}
