package com.snaykmob.noteify.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.snaykmob.noteify.R;
import com.snaykmob.noteify.adapter.GeneralAdapter;
import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.view.IView;

import java.util.List;

import butterknife.BindView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
    }
}
