package com.omninos.nani.activity.naniActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.adapter.AllSpecialitiesAdapter;
import com.omninos.nani.adapter.SpecialitiesListRecyclerAdapter;
import com.omninos.nani.modelClass.SpecialitiesListClass;
import com.omninos.nani.myViewModel.SpecialitiesViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SpecialitiesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private RecyclerView specialities_list_recycler, AllSpecialitiesCycle;
    private Activity activity;
    private List<String> specialitieslist = new ArrayList<>();
    private List<String> specialitiesIds = new ArrayList<>();
    private List<SpecialitiesListClass.Detail> SpecialitiesPojoClassList = new ArrayList<>();
    private List<SpecialitiesListClass.Detail> SpecialitiesdetailList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private SpecialitiesListRecyclerAdapter specialitiesListRecyclerAdapter;
    private AllSpecialitiesAdapter allSpecialitiesAdapter;
    private EditText search;
    private TextView searchTextView;

    private SpecialitiesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_specialities);

        CommonUtils.CheckService(SpecialitiesActivity.this);

        viewModel = ViewModelProviders.of(this).get(SpecialitiesViewModel.class);

        iniitIds();
        performActions();
        getSpecialitiesList();


        specialitiesListRecyclerAdapter = new SpecialitiesListRecyclerAdapter(this, list, new SpecialitiesListRecyclerAdapter.RemoveItem() {
            @Override
            public void Remove(int position) {
                list.remove(list.get(position));
                specialitiesIds.remove(specialitiesIds.get(position));
                specialitiesListRecyclerAdapter.notifyDataSetChanged();
            }
        });
        specialities_list_recycler.setAdapter(specialitiesListRecyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        specialities_list_recycler.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AllSpecialitiesCycle.setLayoutManager(linearLayoutManager1);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence editable, int i, int i1, int i2) {
                if (editable.toString().isEmpty()) {
                    SpecialitiesdetailList = SpecialitiesPojoClassList;
                    AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                    allSpecialitiesAdapter.notifyDataSetChanged();
                } else {
                    AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                    filter(editable.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input


            }
        });
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<SpecialitiesListClass.Detail> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SpecialitiesListClass.Detail s : SpecialitiesPojoClassList) {
            //if the existing elements contains the search input
            if (s.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        SpecialitiesdetailList = filterdNames;
        //calling a method of the adapter class and passing the filtered list
        allSpecialitiesAdapter.filterList(filterdNames);
    }


    private void iniitIds() {
        specialities_list_recycler = findViewById(R.id.specialities_list_recycler);
        AllSpecialitiesCycle = findViewById(R.id.AllSpecialitiesCycle);
        next = findViewById(R.id.next_button_specialities);
        search = findViewById(R.id.search);
        searchTextView = findViewById(R.id.searchTextView);
    }

    private void performActions() {
        next.setOnClickListener(this);
        searchTextView.setOnClickListener(this);
    }

    private void getSpecialitiesList() {
        viewModel.specialitiesList().observe(this, new Observer<SpecialitiesListClass>() {
            @Override
            public void onChanged(@Nullable final SpecialitiesListClass specialitiesListClass) {
                if (specialitiesListClass.getSuccess().equalsIgnoreCase("1")) {

                    SpecialitiesPojoClassList = specialitiesListClass.getDetails();
                    SpecialitiesdetailList = specialitiesListClass.getDetails();

                    allSpecialitiesAdapter = new AllSpecialitiesAdapter(SpecialitiesActivity.this, SpecialitiesdetailList, new AllSpecialitiesAdapter.SelectItems() {
                        @Override
                        public void Select(String position, String ids) {

                            if (list.contains(position)) {
                                CommonUtils.showSnackbarAlert(search, "Already Added To Your Specialities.");
                            } else {
                                list.add(position);
                                specialitiesIds.add(ids);
                            }
                            specialitiesListRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    AllSpecialitiesCycle.setAdapter(allSpecialitiesAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button_specialities:
                getIds();
                break;

            case R.id.searchTextView:
                searchTextView.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                AllSpecialitiesCycle.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getIds() {
        String allData = TextUtils.join(",", specialitiesIds);
        System.out.println("Data: " + allData);
        if (allData.isEmpty()) {
            CommonUtils.showSnackbarAlert(next, "please select atleast one Speciality");
        } else {
            App.getSingleton().setSpecialities(allData);
            if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
                startActivity(new Intent(SpecialitiesActivity.this, UpdateReferenceActivity.class));
            } else {
                startActivity(new Intent(SpecialitiesActivity.this, ReferencesActivity.class));
            }
        }
    }
}
