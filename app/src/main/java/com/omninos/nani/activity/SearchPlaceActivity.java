package com.omninos.nani.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.adapter.PlaceAdapter;
import com.omninos.nani.modelClass.GetLatLngModel;
import com.omninos.nani.modelClass.PlaceSearchModel;
import com.omninos.nani.myViewModel.PlaceViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPlaceActivity extends AppCompatActivity {

    private EditText search_bar;
    final List<String> addressesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlaceViewModel viewModel;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        viewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);


        CommonUtils.CheckService(SearchPlaceActivity.this);
        initView();
        SetUps();

    }

    private void initView() {
        search_bar = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recyclerView);

    }

    private void SetUps() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    recyclerView.setVisibility(View.VISIBLE);
//                    Toast.makeText(SearchPlaceActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    findPlace(s.toString());
                } else {
                    recyclerView.setVisibility(View.GONE);
                    addressesList.clear();
                }
            }
        };


        search_bar.addTextChangedListener(textWatcher);
    }

    private void findPlace(String s) {
        Map<String, String> location = new HashMap<>();

        location.put("input", s);
        location.put("key", SearchPlaceActivity.this.getResources().getString(R.string.map_key));

        viewModel.placeSearchModelLiveData(SearchPlaceActivity.this, location).observe(SearchPlaceActivity.this, new Observer<PlaceSearchModel>() {
            @Override
            public void onChanged(@Nullable PlaceSearchModel placeSearchModel) {
                if (placeSearchModel.getStatus().equalsIgnoreCase("OK")) {

                    if (addressesList != null) {
                        addressesList.clear();
                    }
                    int size = placeSearchModel.getPredictions().size();

                    for (int i = 0; i < size; i++) {
                        addressesList.add(placeSearchModel.getPredictions().get(i).getDescription());
                    }
                    adapter = new PlaceAdapter(SearchPlaceActivity.this, addressesList, new PlaceAdapter.SelectPlace() {
                        @Override
                        public void Choose(final int position) {
                            Map<String, String> distanceMatrix = new HashMap<>();
                            distanceMatrix.put("address", addressesList.get(position));
                            distanceMatrix.put("key", SearchPlaceActivity.this.getResources().getString(R.string.map_key));

                            viewModel.getLatLngModelLiveData(SearchPlaceActivity.this, distanceMatrix).observe(SearchPlaceActivity.this, new Observer<GetLatLngModel>() {
                                @Override
                                public void onChanged(@Nullable GetLatLngModel getLatLngModel) {
                                    if (getLatLngModel.getStatus().equalsIgnoreCase("OK")) {
                                        App.getSingleton().setNaniAddress(addressesList.get(position));
                                        App.getSingleton().setNaniLat(String.valueOf(getLatLngModel.getResults().get(0).getGeometry().getLocation().getLat()));
                                        App.getSingleton().setNaniLng(String.valueOf(getLatLngModel.getResults().get(0).getGeometry().getLocation().getLng()));
                                        onBackPressed();
                                    }
                                }
                            });
                        }
                    });
                    recyclerView.setAdapter(adapter);

                } else {

                }
            }
        });

    }
}
