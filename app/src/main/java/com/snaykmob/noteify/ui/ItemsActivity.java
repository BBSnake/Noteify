package com.snaykmob.noteify.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.Utils;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.presenter.ItemPresenter;
import com.snaykmob.noteify.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemsActivity extends NoteifyActivity implements IView<ItemDTO> {
    @BindView(R.id.items_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_items)
    FloatingActionButton fab;
    private GeneralAdapter<ItemDTO> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ItemDTO> items;
    private ItemPresenter presenter;
    private ItemDAO itemDAO;
    private String activityTitle;
    private long categoryId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Intent intent = getIntent();
        activityTitle = intent.getStringExtra(Utils.CAT_TITLE);
        categoryId = intent.getLongExtra(Utils.CAT_ID, 0);
        this.setTitle(activityTitle);
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

        itemDAO = new ItemDAO(this);
        itemDAO.open();

        presenter = new ItemPresenter(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        presenter.attemptSelect(categoryId, itemDAO);
        mAdapter = new GeneralAdapter<>(this, items);
        mRecyclerView.setAdapter(mAdapter);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            if(direction == ItemTouchHelper.LEFT) {
                ItemDTO item = items.get(position);
                if(item.getCompleted() == 0)
                    item.setCompleted(1);
                else
                    item.setCompleted(0);
                presenter.attemptUpdate(item,itemDAO);
            }
            if(direction == ItemTouchHelper.RIGHT) {
                final ItemDTO item = items.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemsActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage(getString(R.string.item_q_delete)+" \""+item.getText()+"\"?").setTitle(R.string.item_delete)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.attemptDelete(item, itemDAO);
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

    @OnClick(R.id.fab_items)
    public void addItem() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(R.string.item_name).setTitle(R.string.new_item)
                .setView(dialogLayout).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText itemName = (EditText) dialogLayout.findViewById(R.id.dialog_et);
                String itemText = itemName.getText().toString();
                if(itemText.length() > 0) {
                    presenter.attemptCreate(itemText, categoryId, itemDAO);
                    dialog.dismiss();
                }
                else
                    showSnackbar(fab, getString(R.string.item_not_empty), ContextCompat.getColor(ItemsActivity.this, R.color.colorSecondaryDark));
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onSuccessSelect(List<ItemDTO> items) {
        this.items = items;
    }

    @Override
    public void onSuccessDelete() {
        showSnackbar(fab, getString(R.string.item_deleted), ContextCompat.getColor(this,R.color.colorPrimary));
        presenter.attemptSelect(categoryId, itemDAO);
        mAdapter.refresh(items);
    }

    @Override
    public void onSuccessCreate() {
        showSnackbar(fab, getString(R.string.item_created), ContextCompat.getColor(this,R.color.colorPrimary));
    }

    @Override
    public void onSuccessUpdate(long completed) {
        if(completed == 1)
            showSnackbar(fab, getString(R.string.mark_str), ContextCompat.getColor(this,R.color.colorPrimary));
        else
            showSnackbar(fab, getString(R.string.unmark_str), ContextCompat.getColor(this,R.color.colorPrimary));
        presenter.attemptSelect(categoryId, itemDAO);
        mAdapter.refresh(items);
    }

    @Override
    protected void onPause() {
        itemDAO.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        itemDAO.open();
        super.onResume();
    }
}
