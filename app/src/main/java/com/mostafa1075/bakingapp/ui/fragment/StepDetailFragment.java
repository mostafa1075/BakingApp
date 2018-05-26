package com.mostafa1075.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.pojo.Step;
import com.squareup.picasso.Picasso;


public class StepDetailFragment extends Fragment{

    public static final String STEP_ARGUMENT  = "step_arg";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private ImageView mThumbnailIV;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mPlayerView = rootView.findViewById(R.id.step_player);
        TextView descriptionTV = rootView.findViewById(R.id.step_description);
        descriptionTV.setText(mStep.getDescription());

        mThumbnailIV = rootView.findViewById(R.id.step_thumbnail);

        handleStepUrls();

        return rootView;
    }

    // handle showing the step's video or thumbnail
    private void handleStepUrls(){

        String videoUrl = mStep.getVideoURL();
        String thumbnailUrl = mStep.getThumbnailURL();
        String thumbnailType = MimeTypeMap.getFileExtensionFromUrl(thumbnailUrl); //https://stackoverflow.com/a/39288146
        // Check if video URL is available, if not check for thumbnail URL, if not hide both views
        if(videoUrl != null && !videoUrl.equals("")) {
            initializePlayer(Uri.parse(videoUrl));
        }
        else if(thumbnailType.equals("mp4")){
            initializePlayer(Uri.parse(thumbnailUrl));
        }
        else if(thumbnailUrl != null && !thumbnailUrl.equals("")){
            mPlayerView.setVisibility(View.GONE);
            ImageView thumbnailIv = mThumbnailIV;
            thumbnailIv.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(thumbnailUrl)
                    .fit()
                    .into(thumbnailIv);
        }
        else{
            mPlayerView.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(Uri uri){
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
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
