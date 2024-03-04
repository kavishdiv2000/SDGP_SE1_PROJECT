package com.legocats.twinklebun.ui.leadership;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeadershipViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LeadershipViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is leadership fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}