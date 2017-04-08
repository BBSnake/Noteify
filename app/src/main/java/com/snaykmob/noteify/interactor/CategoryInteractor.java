package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.presenter.ICategoryPresenter;

import java.util.List;

public class CategoryInteractor implements ICategoryInteractor {

    @Override
    public void select(ICategoryPresenter callback, CategoryDAO categoryDAO) {
        List<CategoryDTO> items = categoryDAO.getAllCategories();
        callback.onSuccessSelect(items);
    }

    @Override
    public void create(ICategoryPresenter callback, String name, CategoryDAO categoryDAO) {
        categoryDAO.createCategory(name);
        callback.onSuccessCreate();
    }

    @Override
    public void delete(ICategoryPresenter callback, CategoryDTO category, CategoryDAO categoryDAO) {
        categoryDAO.deleteCategory(category);
        callback.onSuccessDelete();
    }
}
