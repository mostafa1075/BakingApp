package com.mostafa1075.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.pojo.Step;

import java.util.List;

/**
 * Created by mosta on 18-May-18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private final List<Step> mSteps;
    private final Context mContext;
    private final onStepClickListener mCallback;

    public StepsAdapter(Context context, List<Step> steps, onStepClickListener callback) {
        mContext = context;
        mSteps = steps;
        mCallback = callback;
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_list_item, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
        Step step = mSteps.get(position);
        String stepShortDescription = step.getShortDescription();
        holder.mStepDescription.setText(stepShortDescription);
        String thumbnailType = MimeTypeMap.getFileExtensionFromUrl(step.getThumbnailURL()); //https://stackoverflow.com/a/39288146
        if(step.getVideoURL().equals("") && !thumbnailType.equals("mp4"))
            holder.mVideoIcon.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public interface onStepClickListener {
        void onStepClicked(int position);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mStepDescription;
        private final ImageView mVideoIcon;
        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            mStepDescription = itemView.findViewById(R.id.step_description_tv);
            mVideoIcon = itemView.findViewById(R.id.video_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mCallback.onStepClicked(position);
        }
    }
}
