package com.omninos.nani.fragments.naniFragments;


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
import com.omninos.nani.activity.naniActivity.ItemDescriptionActivity;
import com.omninos.nani.adapter.NaniPostAdapter;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.myViewModel.PostViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaniPostFragment extends Fragment {

    private RecyclerView recyclerView;
    private NaniPostAdapter adapter;
    private PostViewModel viewModel;


    public NaniPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nani_post, container, false);

        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);

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
        viewModel.getNaniPostModelLiveData(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId(),"").observe(getActivity(), new Observer<GetNaniPostModel>() {
            @Override
            public void onChanged(final GetNaniPostModel getNaniPostModel) {
                if (getNaniPostModel.getSuccess().equalsIgnoreCase("1")) {

                    adapter = new NaniPostAdapter(getActivity(), getNaniPostModel.getDetails(), new NaniPostAdapter.Choose() {
                        @Override
                        public void Select(int position) {
                            startActivity(new Intent(getActivity(), ItemDescriptionActivity.class).putExtra("Details",getNaniPostModel.getDetails().get(position)));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, getNaniPostModel.getMessage());
                }
            }
        });
    }

}
