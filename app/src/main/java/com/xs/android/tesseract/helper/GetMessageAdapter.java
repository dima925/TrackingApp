package com.xs.android.tesseract.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xs.android.tesseract.Config;
import com.xs.android.tesseract.MainActivity;
import com.xs.android.tesseract.R;
import com.xs.android.tesseract.fragment.DetailsMessageFragment;
import com.xs.android.tesseract.fragment.GetMsgFragment;
import com.xs.android.tesseract.fragment.PopulationFragment;

public class GetMessageAdapter extends RecyclerView.Adapter<GetMessageAdapter.ViewHolder> {
    public static MessageItemData[] itemsData;
    public static MainActivity mActivity;
    public GetMessageAdapter(Activity mActivity,MessageItemData[] itemsData) {
        this.itemsData = itemsData;
        this.mActivity = (MainActivity) mActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GetMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_massage_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData[position].getTitle());
        viewHolder.txtViewContent.setText(itemsData[position].getContent());
        viewHolder.txtViewDateTime.setText(itemsData[position].getDatetime());

//        viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());


    }



    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        android.support.v4.app.FragmentManager fm;
        public TextView txtViewTitle;
        public TextView txtViewContent;
        public TextView txtViewDateTime;

        private String mItemTitle;
        private String mItemContent;
        private String mItemDateTime;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewContent = (TextView) itemLayoutView.findViewById(R.id.item_content);
            txtViewDateTime = (TextView) itemLayoutView.findViewById(R.id.item_datetime);
        }
        public void setItemContent(String item1,String item2,String item3) {
            mItemTitle = item1;
            mItemContent = item2;
            mItemDateTime = item3;
            txtViewTitle.setText(mItemTitle);
            txtViewContent.setText(mItemContent);
            txtViewDateTime.setText(mItemDateTime);
        }

        @Override
        public void onClick(View v) {
            Log.d(Config.APP_TAG,":" + getPosition() + ":" + itemsData[getPosition()].getTitle());
            Config.selectedMessageTitle = itemsData[getPosition()].getTitle();
            Config.selectedMessageContent = itemsData[getPosition()].getContent();
            Config.selectedMessageDatetime = itemsData[getPosition()].getDatetime();

            mActivity.replaceFragment(1001);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}
