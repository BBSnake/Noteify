package com.snaykmob.noteify.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.dto.ITableDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralAdapter<T extends ITableDTO> extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv)
        public TextView itemTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private List<T> mItems;
    private Context mContext;

    public GeneralAdapter(Context context, List<T> items) {
        mItems = items;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public GeneralAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_layout, parent, false);

        return new GeneralAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GeneralAdapter.ViewHolder viewHolder, int position) {
        T item = mItems.get(position);

        TextView textView = viewHolder.itemTextView;
        textView.setText(item.getText());
        if (item.getCompleted() == 1)
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void refresh(List<T> list) {
        if (mItems != null) {
            mItems.clear();
            mItems.addAll(list);
        } else
            mItems = list;
        notifyDataSetChanged();
    }
}
