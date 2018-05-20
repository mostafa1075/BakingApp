package com.mostafa1075.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mostafa1075.bakingapp.pojo.Step;


public class StepDetailFragment extends Fragment{

    public static final String STEP_ARGUMENT  = "step_arg";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private Step mStep;

    public StepDetailFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(STEP_ARGUMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mPlayerView = rootView.findViewById(R.id.step_player);

        initializePlayer(Uri.parse(mStep.getVideoURL()));

        return rootView;

    }

    public void initializePlayer(Uri uri){
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                new DefaultTrackSelector());
        mPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");

        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
