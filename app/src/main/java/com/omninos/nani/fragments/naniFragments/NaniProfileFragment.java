package com.omninos.nani.fragments.naniFragments;


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
import com.omninos.nani.activity.naniActivity.NaniEditProfile;
import com.omninos.nani.adapter.SpecialityAdapter;
import com.omninos.nani.modelClass.GetNaniProfile;
import com.omninos.nani.myViewModel.ProfileViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaniProfileFragment extends Fragment implements View.OnClickListener {


    private CircleImageView profile_user_image;
    private TextView nameTextView, totalnumberItem, revenue, followers,edit_account_text_click;

    private ProfileViewModel viewModel;
    private RecyclerView profile_addresses_recycler;
    private SpecialityAdapter adapter;
    private Button profile_logout;
    private GoogleSignInClient mgoogleSignInClient;
    public NaniProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nani_profile, container, false);

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        initView(view);
        SetUp(view);

        return view;
    }

    private void initView(View view) {
        profile_user_image = view.findViewById(R.id.profile_user_image);
        nameTextView = view.findViewById(R.id.nameTextView);
        profile_addresses_recycler = view.findViewById(R.id.profile_addresses_recycler);

        totalnumberItem = view.findViewById(R.id.totalnumberItem);
        revenue = view.findViewById(R.id.revenue);
        followers = view.findViewById(R.id.followers);

        profile_logout = view.findViewById(R.id.profile_logout);
        edit_account_text_click=view.findViewById(R.id.edit_account_text_click);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!App.getAppPreference().getUserDetails().getDetails().getImage().isEmpty()) {
            Glide.with(this).load(App.getAppPreference().getUserDetails().getDetails().getImage()).into(profile_user_image);
        }
        nameTextView.setText(App.getAppPreference().getUserDetails().getDetails().getName());

    }

    private void SetUp(View view) {
        edit_account_text_click.setOnClickListener(this);
        profile_logout.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        profile_addresses_recycler.setLayoutManager(linearLayoutManager);

        getList();
    }

    private void getList() {
        viewModel.mySpecialityModelLiveData(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<GetNaniProfile>() {
            @Override
            public void onChanged(GetNaniProfile mySpecialityModel) {
                if (mySpecialityModel.getSuccess().equalsIgnoreCase("1")) {
//                    List<String> fixedLenghtList = Arrays.asList(mySpecialityModel.getDetails().getSpecialitiesss());
                    List<String> list = new ArrayList<String>(Arrays.asList(mySpecialityModel.getDetails().getSpecialitiesss().split(",")));

                    adapter = new SpecialityAdapter(getActivity(), list);
                    profile_addresses_recycler.setAdapter(adapter);

                    totalnumberItem.setText(mySpecialityModel.getDetails().getTotalItems());
                    revenue.setText(mySpecialityModel.getDetails().getTotalRevenue());
                    followers.setText(mySpecialityModel.getDetails().getTotalSubscription());
                } else {
                    CommonUtils.showSnackbarAlert(nameTextView, mySpecialityModel.getMessage());
                }
            }
        });
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
                startActivity(new Intent(getActivity(), NaniEditProfile.class));
                break;
        }
    }
}
