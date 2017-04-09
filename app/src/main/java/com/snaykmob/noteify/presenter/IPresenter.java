package com.snaykmob.noteify.presenter;

import java.util.List;

public interface IPresenter<T,U> {
    void attemptDelete(T dto, U dao);
    void attemptUpdate(T dto, U dao);

    void onSuccessCreate();
    void onSuccessSelect(List<T> items);
    void onSuccessDelete();
    void onSuccessUpdate(T dto);
}
