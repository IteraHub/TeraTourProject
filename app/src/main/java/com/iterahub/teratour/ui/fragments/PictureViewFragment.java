package com.iterahub.teratour.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.iterahub.teratour.R;
import com.iterahub.teratour.utils.ShowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PictureViewFragment extends Fragment {

    private static String IMAGE_URL = "IMAGE_URL";

    private String imageUrl;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.image_view)
    ImageView imageView;

    public PictureViewFragment() {
        // Required empty public constructor
    }

    public static PictureViewFragment newInstance(String imageUrl) {
        PictureViewFragment fragment = new PictureViewFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL,imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            imageUrl = getArguments().getString(IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_picture_view, container, false);
        ButterKnife.bind(this,v);
        ShowUtils.setImage(getActivity(),imageView,imageUrl,progressBar);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
