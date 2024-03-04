package com.legocats.twinklebun.ui.badges;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BadgesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BadgesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is badges fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}