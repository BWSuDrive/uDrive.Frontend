package de.bws.udrive.ui.nachrichten;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NachrichtenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NachrichtenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier kommen die Mitteilungen an");
    }

    public LiveData<String> getText() {
        return mText;
    }
}