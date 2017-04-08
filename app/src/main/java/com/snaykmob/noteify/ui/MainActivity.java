package com.snaykmob.noteify.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.Utils;
import com.snaykmob.noteify.adapter.ClickListener;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.adapter.RvTouchListener;
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

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }
        });

        categoryDAO = new CategoryDAO(this);
        categoryDAO.open();

        presenter = new CategoryPresenter(this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new RvTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryDTO item = items.get(position);
                Intent intent = new Intent(getApplicationContext(), ItemsActivity.class);
                intent.putExtra(Utils.CAT_TITLE, item.getText());
                intent.putExtra(Utils.CAT_ID, item.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        presenter.attemptSelect(categoryDAO);
        mAdapter = new GeneralAdapter<CategoryDTO>(this, items);
        mRecyclerView.setAdapter(mAdapter);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            if(direction == ItemTouchHelper.LEFT) {
                CategoryDTO item = items.get(position);
                if(item.getCompleted() == 0)
                    item.setCompleted(1);
                else
                    item.setCompleted(0);
                presenter.attemptUpdate(item,categoryDAO);
            }
            if(direction == ItemTouchHelper.RIGHT) {
                final CategoryDTO item = items.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage(getString(R.string.cat_q_delete)+" \""+item.getText()+"\"?").setTitle(R.string.cat_delete)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.attemptDelete(item,categoryDAO);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAdapter.refresh(items);
                    }
                }).create().show();
            }
        }
    };

    @OnClick(R.id.fab)
    public void addCategory() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(R.string.cat_name).setTitle(R.string.new_cat)
                .setView(dialogLayout).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText categoryName = (EditText) dialogLayout.findViewById(R.id.dialog_et);
                String categoryText = categoryName.getText().toString();
                if(categoryText.length() > 0) {
                    presenter.attemptCreate(categoryText,categoryDAO);
                    dialog.dismiss();
                }
                else
                    showSnackbar(fab, getString(R.string.cat_not_empty), ContextCompat.getColor(MainActivity.this, R.color.colorSecondaryDark));
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
        showSnackbar(fab, getString(R.string.cat_deleted), ContextCompat.getColor(this, R.color.colorPrimary));
        presenter.attemptSelect(categoryDAO);
        mAdapter.refresh(items);
    }

    @Override
    public void onSuccessCreate() {
        showSnackbar(fab, getString(R.string.cat_created), ContextCompat.getColor(this, R.color.colorPrimary));
        presenter.attemptSelect(categoryDAO);
        mAdapter.refresh(items);
    }

    @Override
    public void onSuccessUpdate(long completed) {
        if(completed == 1)
            showSnackbar(fab, getString(R.string.mark_str), ContextCompat.getColor(this,R.color.colorPrimary));
        else
            showSnackbar(fab, getString(R.string.unmark_str), ContextCompat.getColor(this,R.color.colorPrimary));
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
