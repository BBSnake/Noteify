package com.snaykmob.noteify.view;

import java.util.List;

public interface IView<T> {
    void onSuccessSelect(List<T> items);
    void onSuccessDelete();
    void onSuccessCreate();
}