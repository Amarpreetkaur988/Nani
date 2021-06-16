package com.omninos.nani.fragments.buyerFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.omninos.nani.R;
import com.omninos.nani.activity.StartActivity;
import com.omninos.nani.activity.userActivity.BuyerEditProfileActivity;
import com.omninos.nani.adapter.MyScribeAdapter;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.myViewModel.PostViewModel;
import com.omninos.nani.utils.App;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyerProfileFragment extends Fragment implements View.OnClickListener {

    private CircleImageView profile_user_image;
    private TextView nameTextView, edit_account_text_click;
    private RecyclerView profile_addresses_recycler;
    private PostViewModel viewModel;
    private MyScribeAdapter subscriptionPostAdapter;
    private GoogleSignInClient mgoogleSignInClient;
    private Button profile_logout;

    public BuyerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_profile, container, false);
        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        initView(view);
        SetUp(view);
        return view;
    }

    private void initView(View view) {
        profile_user_image = view.findViewById(R.id.profile_user_image);
        nameTextView = view.findViewById(R.id.nameTextView);
        profile_addresses_recycler = view.findViewById(R.id.profile_addresses_recycler);
        profile_logout = view.findViewById(R.id.profile_logout);
        edit_account_text_click = view.findViewById(R.id.edit_account_text_click);
    }

    private void SetUp(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        profile_addresses_recycler.setLayoutManager(linearLayoutManager);

        profile_logout.setOnClickListener(this);
        edit_account_text_click.setOnClickListener(this);
        getList();
    }

    private void getList() {
        viewModel.getSubscribedNaniPosts(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<GetNaniPostModel>() {
            @Override
            public void onChanged(GetNaniPostModel getNaniPostModel) {
                if (getNaniPostModel.getSuccess().equalsIgnoreCase("1")) {
                    subscriptionPostAdapter = new MyScribeAdapter(getActivity(), getNaniPostModel.getDetails(), new MyScribeAdapter.Choose() {
                        @Override
                        public void Select(int position) {

                        }
                    });
                    profile_addresses_recycler.setAdapter(subscriptionPostAdapter);
                } else {
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!App.getAppPreference().getUserDetails().getDetails().getImage().isEmpty()) {
            Glide.with(this).load(App.getAppPreference().getUserDetails().getDetails().getImage()).into(profile_user_image);
        }
        nameTextView.setText(App.getAppPreference().getUserDetails().getDetails().getName());
    }


    private void googleLogOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mgoogleSignInClient.signOut();
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_logout:
                googleLogOut();
                disconnectFromFacebook();
                App.getAppPreference().Logout();
                startActivity(new Intent(getActivity(), StartActivity.class));
                getActivity().finishAffinity();
                break;

            case R.id.edit_account_text_click:
                startActivity(new Intent(getActivity(), BuyerEditProfileActivity.class));
                break;
        }
    }
}
