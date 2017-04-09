package com.snaykmob.noteify.presenter;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.interactor.CategoryInteractor;
import com.snaykmob.noteify.view.IView;

import java.util.List;

public class CategoryPresenter implements IPresenter<CategoryDTO, CategoryDAO> {

    private IView<CategoryDTO> view;
    private CategoryInteractor interactor;

    public CategoryPresenter(IView<CategoryDTO> view) {
        this.view = view;
        interactor = new CategoryInteractor();
    }

    public void attemptCreate(String name, CategoryDAO categoryDAO) {
        interactor.create(this, name, categoryDAO);
    }

    public void attemptSelect(CategoryDAO categoryDAO) {
        interactor.select(this, categoryDAO);
    }

    @Override
    public void attemptDelete(CategoryDTO category, CategoryDAO categoryDAO) {
        interactor.delete(this, category, categoryDAO);
    }

    @Override
    public void attemptUpdate(CategoryDTO category, CategoryDAO categoryDAO) {
        interactor.update(this, category, categoryDAO);
    }

    public void attemptDeleteAll(CategoryDAO dao) {
        interactor.deleteAll(this, dao);
    }

    public void attemptDeleteAllMarked(CategoryDAO dao) {
        interactor.deleteAllMarked(this, dao);
    }

    @Override
    public void onSuccessCreate() {
        view.onSuccessCreate();
    }

    @Override
    public void onSuccessSelect(List<CategoryDTO> items) {
        view.onSuccessSelect(items);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    @Override
    public void onSuccessUpdate(CategoryDTO category) {
        view.onSuccessUpdate(category.getCompleted());
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
