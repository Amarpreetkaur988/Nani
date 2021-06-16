package com.omninos.nani.fragments.naniFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.omninos.nani.R;
import com.omninos.nani.activity.FAQActivity;
import com.omninos.nani.activity.TermAndConditionActivity;
import com.omninos.nani.activity.naniActivity.NaniEditProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaniSettingFragment extends Fragment implements View.OnClickListener {

    private CardView profileCard, term,faq;


    public NaniSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nani_setting, container, false);
        initView(view);
        SetUp(view);

        return view;
    }

    private void initView(View view) {
        profileCard = view.findViewById(R.id.profileCard);
        term = view.findViewById(R.id.term);
        faq=view.findViewById(R.id.faq);

    }

    private void SetUp(View view) {
        profileCard.setOnClickListener(this);
        term.setOnClickListener(this);
        faq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profileCard:
                startActivity(new Intent(getActivity(), NaniEditProfile.class));
                break;
            case R.id.term:
                startActivity(new Intent(getActivity(), TermAndConditionActivity.class).putExtra("Type", "1"));
                break;
            case R.id.faq:
                startActivity(new Intent(getActivity(), FAQActivity.class).putExtra("Type","1"));
                break;

        }
    }
}
