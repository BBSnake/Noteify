package com.snaykmob.noteify.presenter;

import java.util.List;

public interface IPresenter<T,U> {
    void attemptCreate(String name, U dao);
    void attemptSelect(U dao);
    void attemptDelete(T dto, U dao);
    void attemptUpdate(T dto, U dao);

    void onSuccessCreate();
    void onSuccessSelect(List<T> items);
    void onSuccessDelete();
    void onSuccessUpdate(T dto);
}
