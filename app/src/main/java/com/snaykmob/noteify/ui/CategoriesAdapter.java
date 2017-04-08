package com.snaykmob.noteify.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.dto.CategoryDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends GeneralAdapter<CategoryDTO> {

    public CategoriesAdapter(Context context, List<CategoryDTO> items) {
        super(context, items);
    }


//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.item_tv) public TextView categoryTextView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    private List<CategoryDTO> mCategories;
//    private Context mContext;
//
//    public CategoriesAdapter(Context context, List<CategoryDTO> categories) {
//        mCategories = categories;
//        mContext = context;
//    }
//
//    private Context getContext() {
//        return mContext;
//    }
//
//    @Override
//    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View categoryView = inflater.inflate(R.layout.item_layout, parent,false);
//
//        return new ViewHolder(categoryView);
//    }
//
//    @Override
//    public void onBindViewHolder(CategoriesAdapter.ViewHolder viewHolder, int position) {
//        CategoryDTO category = mCategories.get(position);
//
//        TextView textView = viewHolder.categoryTextView;
//        textView.setText(category.getText());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCategories.size();
//    }
}
