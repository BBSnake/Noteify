package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.presenter.IPresenter;

import java.util.List;

public class CategoryInteractor implements IInteractor<CategoryDTO, CategoryDAO> {

    @Override
    public void select(IPresenter<CategoryDTO, CategoryDAO> callback, CategoryDAO categoryDAO) {
        List<CategoryDTO> items = categoryDAO.getAllCategories();
        callback.onSuccessSelect(items);
    }

    @Override
    public void create(IPresenter<CategoryDTO, CategoryDAO> callback, String name, CategoryDAO categoryDAO) {
        categoryDAO.createCategory(name, 0);
        callback.onSuccessCreate();
    }

    @Override
    public void delete(IPresenter<CategoryDTO, CategoryDAO> callback, CategoryDTO category, CategoryDAO categoryDAO) {
        categoryDAO.deleteCategory(category);
        callback.onSuccessDelete();
    }

    @Override
    public void update(IPresenter<CategoryDTO, CategoryDAO> callback, CategoryDTO category, CategoryDAO categoryDAO) {
        categoryDAO.updateCategory(category);
        callback.onSuccessUpdate(category);
    }
}
