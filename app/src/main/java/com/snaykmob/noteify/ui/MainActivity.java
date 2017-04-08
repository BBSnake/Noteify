package com.snaykmob.noteify.ui;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.presenter.CategoryPresenter;
import com.snaykmob.noteify.view.IView;

import org.w3c.dom.Text;

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
                builder.setMessage("Do you want to delete category \""+item.getText()+"\"?").setTitle("Delete category")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.attemptDelete(item,categoryDAO);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        final EditText categoryName = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("Category name:").setTitle("New category")
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
        showSnackbar(fab, "Category deleted!", ContextCompat.getColor(this, R.color.colorPrimary));
        presenter.attemptSelect(categoryDAO);
        mAdapter.refresh(items);
    }

    @Override
    public void onSuccessCreate() {
        showSnackbar(fab, "Category created!", ContextCompat.getColor(this, R.color.colorPrimary));
        presenter.attemptSelect(categoryDAO);
        mAdapter.refresh(items);
    }

    @Override
    public void onSuccessUpdate(long completed) {
        if(completed == 1)
            showSnackbar(fab, "Marked as completed!", ContextCompat.getColor(this,R.color.colorPrimary));
        else
            showSnackbar(fab, "Unmarked as completed!", ContextCompat.getColor(this,R.color.colorPrimary));
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
