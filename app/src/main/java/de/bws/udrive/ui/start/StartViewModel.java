package de.bws.udrive.ui.start;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier kann man das Mitfahren auswählen");
    }

    public LiveData<String> getText() {
        return mText;
    }
}