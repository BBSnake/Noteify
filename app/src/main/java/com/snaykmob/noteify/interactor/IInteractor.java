package com.snaykmob.noteify.interactor;

import com.snaykmob.noteify.presenter.IPresenter;

interface IInteractor<T,U> {
    void delete(IPresenter<T,U> callback, T dto, U dao);
    void update(IPresenter<T,U> callback, T dto, U dao);
}
