package com.example.ezlen.mrstormy2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.model.Weather.HourlyWeather;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourViewHolder> {

    private HourlyWeather[] mHours;
    private Context mContext;

    public HourlyAdapter(Context context, HourlyWeather[] hours) {
        mHours = hours;
        mContext = context;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);

        HourViewHolder viewHolder = new HourViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }


    public class HourViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mHourTimeTextView;
        public TextView mHourSumTextView;
        public TextView mHourTempTextView;
        public ImageView mHourIconImageView;


        public HourViewHolder(View itemView) {
            super(itemView);

            mHourTimeTextView = (TextView) itemView.findViewById(R.id.hourTimeTextView);
            mHourTempTextView = (TextView) itemView.findViewById(R.id.hourTempTextView);
            mHourSumTextView = (TextView) itemView.findViewById(R.id.hourSumTextView);
            mHourIconImageView = (ImageView) itemView.findViewById(R.id.hourIconImageView);

            itemView.setOnClickListener(this);
        }


        public void bindHour(HourlyWeather hour) {
            mHourTimeTextView.setText(hour.getFormattedHour());
            mHourTempTextView.setText(String.format("%.1f", hour.getTemp()));
            mHourSumTextView.setText(hour.getSummary());
            mHourIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
