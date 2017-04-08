package com.snaykmob.noteify.ui;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.presenter.CategoryPresenter;
import com.snaykmob.noteify.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends NoteifyActivity implements IView<CategoryDTO> {
    @BindView(R.id.categories_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    private GeneralAdapter<CategoryDTO> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CategoryDTO> items;
    private CategoryPresenter presenter;
    private CategoryDAO categoryDAO;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();

        presenter = new CategoryPresenter(this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        presenter.attemptSelect(categoryDAO);
        mAdapter = new GeneralAdapter<CategoryDTO>(this, items);
        mRecyclerView.setAdapter(mAdapter);

    }
    @OnClick(R.id.fab)
    public void addCategory() {
        final EditText categoryName = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("Category name:").setTitle("New category").setIcon(android.R.drawable.ic_menu_agenda)
        .setView(categoryName).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoryText = categoryName.getText().toString();
                if(!categoryText.equals("")) {
                    presenter.attemptCreate(categoryText,categoryDAO);
                    dialog.dismiss();
                }
                else
                    categoryName.setError("Field cannot be empty!");
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onSuccessSelect(List<CategoryDTO> items) {
        this.items = items;
    }

    @Override
    public void onSuccessDelete() {
    }

    @Override
    public void onSuccessCreate() {
        showSnackbar("Category created!", ContextCompat.getColor(this, R.color.colorPrimary));
        presenter.attemptSelect(categoryDAO);
        mAdapter.refresh(items);
    }

    @Override
    protected void onPause() {
        categoryDAO.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        categoryDAO.open();
        super.onResume();
    }
}
