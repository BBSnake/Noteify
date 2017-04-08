package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.presenter.IPresenter;

public interface IInteractor<T,U> {
    void select(IPresenter<T,U> callback, U dao);
    void create(IPresenter<T,U> callback, String name, U dao);
    void delete(IPresenter<T,U> callback, T dto, U dao);
    void update(IPresenter<T,U> callback, T dto, U dao);
}
