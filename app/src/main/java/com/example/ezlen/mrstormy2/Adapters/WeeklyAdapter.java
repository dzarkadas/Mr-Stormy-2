package com.example.ezlen.mrstormy2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.model.Weather.WeeklyWeather;

public class WeeklyAdapter extends BaseAdapter {

    private Context mContext;
    private WeeklyWeather[] mWeeklyWeathers;

    public WeeklyAdapter(Context context, WeeklyWeather[] days) {
        mContext = context;
        mWeeklyWeathers = days;
    }

    @Override
    public int getCount() {
        return mWeeklyWeathers.length;
    }

    @Override
    public Object getItem(int position) {
        return mWeeklyWeathers[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.weekly_list_item, null);
            holder = new ViewHolder();

            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.tempTextView = (TextView) convertView.findViewById(R.id.tempTextView);
            holder.dayTextView = (TextView) convertView.findViewById(R.id.dayTextView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        WeeklyWeather day = mWeeklyWeathers[position];

        holder.iconImageView.setImageResource(day.getIconId());
        holder.tempTextView.setText(String.format("%.1f", day.getMaxTemp()));

        if (position == 0) {
            holder.dayTextView.setText("Today");
        }
        else {
            holder.dayTextView.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }


    private static class ViewHolder {
        ImageView iconImageView;
        TextView tempTextView;
        TextView dayTextView;
    }


    @Override   // NOT USING
    public long getItemId(int position) {
        return 0;
    }
}
