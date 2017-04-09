package com.snaykmob.noteify.presenter;

import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.interactor.ItemInteractor;
import com.snaykmob.noteify.view.IView;

import java.util.List;

public class ItemPresenter implements IPresenter<ItemDTO, ItemDAO> {
    private IView<ItemDTO> view;
    private ItemInteractor interactor;

    public ItemPresenter(IView<ItemDTO> view) {
        this.view = view;
        this.interactor = new ItemInteractor();
    }

    public void attemptCreate(String name, long categoryId, ItemDAO dao) {
        interactor.create(this, name, categoryId, dao);
    }

    public void attemptSelect(long categoryId, ItemDAO dao) {
        interactor.select(this, categoryId, dao);
    }

    @Override
    public void attemptDelete(ItemDTO dto, ItemDAO dao) {
        interactor.delete(this, dto, dao);
    }

    @Override
    public void attemptUpdate(ItemDTO dto, ItemDAO dao) {
        interactor.update(this, dto, dao);
    }

    public void attemptDeleteAll(long categoryId, ItemDAO dao) {
        interactor.deleteAll(this, categoryId, dao);
    }

    public void attemptDeleteAllMarked(long categoryId, ItemDAO dao) {
        interactor.deleteAllMarked(this,categoryId, dao);
    }

    @Override
    public void onSuccessCreate() {
        view.onSuccessCreate();
    }

    @Override
    public void onSuccessSelect(List<ItemDTO> items) {
        view.onSuccessSelect(items);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    @Override
    public void onSuccessUpdate(ItemDTO dto) {
        view.onSuccessUpdate(dto.getCompleted());
    }

    @Override
    public void onSuccessDeleteAll() {
        view.onSuccessDeleteAll();
    }

    @Override
    public void onSuccessDeleteAllMark() {
        view.onSuccessDeleteAllMark();
    }
}
