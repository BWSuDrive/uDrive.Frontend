package de.bws.udrive.ui.stundenplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StundenplanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StundenplanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier kommt ihr Stundenplan hin");
    }

    public LiveData<String> getText() {
        return mText;
    }
}