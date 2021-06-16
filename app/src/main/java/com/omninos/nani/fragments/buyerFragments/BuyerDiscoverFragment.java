package com.omninos.nani.fragments.buyerFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.activity.userActivity.BuyerDiscoverItemDetails;
import com.omninos.nani.activity.userActivity.SubscribeNaniActivity;
import com.omninos.nani.adapter.MainDiscoverAdapter;
import com.omninos.nani.modelClass.DiscoverModelClass;
import com.omninos.nani.myViewModel.DiscoverViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyerDiscoverFragment extends Fragment {

    private RecyclerView recyclerView;
    private MainDiscoverAdapter adapter;
    private DiscoverViewModel viewModel;


    public BuyerDiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_discover, container, false);

        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel.class);

        initView(view);
        SetUp(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void SetUp(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        getList();

    }

    private void getList() {
        viewModel.discoverModelClassLiveData(getActivity(), "", "2", App.getAppPreference().getUserDetails().getDetails().getId(), "", "").observe(getActivity(), new Observer<DiscoverModelClass>() {
            @Override
            public void onChanged(final DiscoverModelClass discoverModelClass) {
                if (discoverModelClass.getSuccess().equalsIgnoreCase("1")) {
                    adapter = new MainDiscoverAdapter(getActivity(), discoverModelClass.getDetails(), new MainDiscoverAdapter.Choose() {
                        @Override
                        public void Select(int position,int i) {
                            startActivity(new Intent(getActivity(), BuyerDiscoverItemDetails.class).putExtra("Details",discoverModelClass.getDetails().get(position).getPostList().get(i)));
                        }

                        @Override
                        public void SelectSub(int position, int i) {
                            startActivity(new Intent(getActivity(), SubscribeNaniActivity.class).putExtra("Details", discoverModelClass.getDetails().get(position).getPostList().get(i)).putExtra("Type","2"));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, discoverModelClass.getMessage());
                }
            }
        });
    }

}
