package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.presenter.IPresenter;

import java.util.List;

public class ItemInteractor implements IInteractor<ItemDTO, ItemDAO> {

    public void select(IPresenter<ItemDTO, ItemDAO> callback, long categoryId, ItemDAO dao) {
        List<ItemDTO> items = dao.getItems(categoryId);
        callback.onSuccessSelect(items);
    }

    public void create(IPresenter<ItemDTO, ItemDAO> callback, String name, long categoryId, ItemDAO dao) {
        dao.createItem(name, categoryId, 0);
        callback.onSuccessCreate();
    }

    @Override
    public void delete(IPresenter<ItemDTO, ItemDAO> callback, ItemDTO dto, ItemDAO dao) {
        dao.deleteItem(dto);
        callback.onSuccessDelete();
    }

    @Override
    public void update(IPresenter<ItemDTO, ItemDAO> callback, ItemDTO dto, ItemDAO dao) {
        dao.updateItem(dto);
        callback.onSuccessUpdate(dto);
    }
}
