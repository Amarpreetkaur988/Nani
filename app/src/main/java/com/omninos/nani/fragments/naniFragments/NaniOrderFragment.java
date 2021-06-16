package com.omninos.nani.fragments.naniFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.omninos.nani.adapter.MyOrderNaniAdapter;
import com.omninos.nani.modelClass.NaniOrderModelClass;
import com.omninos.nani.myViewModel.OrderViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaniOrderFragment extends Fragment {


    private RecyclerView recyclerView;
    private MyOrderNaniAdapter adapter;
    private OrderViewModel viewModel;


    public NaniOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nani_order, container, false);

        viewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

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
        viewModel.naniOrderModelClassLiveData(getActivity(), App.getAppPreference().getUserDetails().getDetails().getId()).observe(getActivity(), new Observer<NaniOrderModelClass>() {
            @Override
            public void onChanged(final NaniOrderModelClass naniOrderModelClass) {
                if (naniOrderModelClass.getSuccess().equalsIgnoreCase("1")) {
                    adapter = new MyOrderNaniAdapter(getActivity(), naniOrderModelClass.getDetails(), new MyOrderNaniAdapter.Select() {
                        @Override
                        public void ChooseStatus(int position, String status) {
                            UpdateStatus(naniOrderModelClass.getDetails().get(position).getId(), status);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private void ConfirmBox(final String idData, final String status) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                UpdateStatus(idData, status);
                dialog.cancel();

            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void UpdateStatus(String id, String status) {
        viewModel.statusUpdate(getActivity(), id, status).observe(getActivity(), new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    CommonUtils.showSnackbarAlert(recyclerView, map.get("message").toString());
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, map.get("message").toString());
                }
            }
        });
    }

}
