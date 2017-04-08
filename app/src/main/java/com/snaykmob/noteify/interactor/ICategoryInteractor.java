package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.dao.CategoryDAO;
import com.snaykmob.noteify.dto.CategoryDTO;
import com.snaykmob.noteify.presenter.ICategoryPresenter;

public interface ICategoryInteractor {
    void select(ICategoryPresenter callback, CategoryDAO categoryDAO);
    void create(ICategoryPresenter callback, String name, CategoryDAO categoryDAO);
    void delete(ICategoryPresenter callback, CategoryDTO category, CategoryDAO categoryDAO);

}
