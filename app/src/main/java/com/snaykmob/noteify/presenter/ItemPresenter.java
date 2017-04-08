package com.snaykmob.noteify.presenter;

import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.view.IView;

import java.util.List;

public class ItemPresenter implements IPresenter<ItemDTO, ItemDAO> {
    private IView<ItemDTO> view;
    private ItemInteractor interactor;

    public ItemPresenter(IView<ItemDTO> view) {
        this.view = view;
        this.interactor = new ItemInteractor();
    }

    @Override
    public void attemptCreate(String name, ItemDAO dao) {

    }

    @Override
    public void attemptSelect(ItemDAO dao) {

    }

    @Override
    public void attemptDelete(ItemDTO dto, ItemDAO dao) {

    }

    @Override
    public void attemptUpdate(ItemDTO dto, ItemDAO dao) {

    }

    @Override
    public void onSuccessCreate() {

    }

    @Override
    public void onSuccessSelect(List<ItemDTO> items) {

    }

    @Override
    public void onSuccessDelete() {

    }

    @Override
    public void onSuccessUpdate(ItemDTO dto) {

    }
}
