package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.dao.ItemDAO;
import com.snaykmob.noteify.dto.ItemDTO;
import com.snaykmob.noteify.presenter.IPresenter;

public class ItemInteractor implements IInteractor<ItemDTO, ItemDAO> {
    @Override
    public void select(IPresenter<ItemDTO, ItemDAO> callback, ItemDAO dao) {

    }

    @Override
    public void create(IPresenter<ItemDTO, ItemDAO> callback, String name, ItemDAO dao) {

    }

    @Override
    public void delete(IPresenter<ItemDTO, ItemDAO> callback, ItemDTO dto, ItemDAO dao) {

    }

    @Override
    public void update(IPresenter<ItemDTO, ItemDAO> callback, ItemDTO dto, ItemDAO dao) {

    }
}
