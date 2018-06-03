package com.example.usr.scheduler.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usr.scheduler.R;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ReminderAdapter.ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.reminder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Dummy Image, will be removed later
        public ImageView dummyImage;
        public TextView title;
        public CalendarView date;

        public ViewHolder(View itemView) {
            super(itemView);

            dummyImage = (ImageView) itemView.findViewById(R.id.dummyImage);
            date       = (CalendarView) itemView.findViewById(R.id.date);
            title      = (TextView) itemView.findViewById(R.id.title);
        }

        void bind(int position) {

        }
    }
}
