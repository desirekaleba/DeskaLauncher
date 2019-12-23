package com.desire.android.deskalauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by desire on 12/11/2018.
 */

public class DeskaLauncherFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static final String TAG = "DeskaLauncherFragment";

    public static DeskaLauncherFragment newInstance(){
        return new DeskaLauncherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_deska_launcher, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.app_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        setupAdapter();

        return v;
    }
    private void setupAdapter(){
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
        Log.i(TAG, "found "+ activities.size() + "activities");
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
        Collections.sort(activities, new Comparator<ResolveInfo>(){
            public int compare(ResolveInfo a, ResolveInfo b){
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm).toString(), b.loadLabel(pm).toString());
            }
        });
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        private ImageView mImage;

        public ActivityHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView)itemView.findViewById(R.id.activity_name);
            mImage = (ImageView)itemView.findViewById(R.id.activity_image);
        }
        @Override
        public void onClick(View view){
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        public void bindActivity(ResolveInfo resolveInfo){
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = resolveInfo.loadLabel(pm).toString();
            Drawable appImage = resolveInfo.loadIcon(pm);
            mImage.setImageDrawable(appImage);
            mNameTextView.setText(appName);
        }

    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder>{

        private final List<ResolveInfo> mActivities;

        public ActivityAdapter(List<ResolveInfo> activities){
            mActivities = activities;
        }
        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return  new ActivityHolder(inflater, parent);
        }
        @Override
        public void onBindViewHolder(ActivityHolder holder, int position){
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);

        }
        public int getItemCount(){
            return mActivities.size();
        }
    }
}
