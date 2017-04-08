package com.snaykmob.noteify.presenter;

import android.content.Context;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.interactor.CategoryInteractor;
import com.snaykmob.noteify.view.IView;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

public class CategoryPresenter implements IPresenter<CategoryDTO, CategoryDAO> {

    private IView<CategoryDTO> view;
    private CategoryInteractor interactor;

    public CategoryPresenter(IView<CategoryDTO> view) {
        this.view = view;
        interactor = new CategoryInteractor();
    }

    @Override
    public void attemptCreate(String name, CategoryDAO categoryDAO) {
        interactor.create(this,name,categoryDAO);
    }

    @Override
    public void attemptSelect(CategoryDAO categoryDAO) {
        interactor.select(this,categoryDAO);
    }

    @Override
    public void attemptDelete(CategoryDTO category, CategoryDAO categoryDAO) {
        interactor.delete(this, category, categoryDAO);
    }

    @Override
    public void attemptUpdate(CategoryDTO category, CategoryDAO categoryDAO) {
        interactor.update(this, category, categoryDAO);
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
}
