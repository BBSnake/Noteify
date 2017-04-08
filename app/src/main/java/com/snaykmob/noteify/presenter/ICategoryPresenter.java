package com.snaykmob.noteify.presenter;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;

import java.util.List;

public interface ICategoryPresenter {
    void attemptCreate(String name, CategoryDAO categoryDAO);
    void attemptSelect(CategoryDAO categoryDAO);
    void attemptDelete(CategoryDTO category, CategoryDAO categoryDAO);
    void attemptUpdate(CategoryDTO category, CategoryDAO categoryDAO);

    void onSuccessCreate();
    void onSuccessSelect(List<CategoryDTO> items);
    void onSuccessDelete();
    void onSuccessUpdate(CategoryDTO category);
}
