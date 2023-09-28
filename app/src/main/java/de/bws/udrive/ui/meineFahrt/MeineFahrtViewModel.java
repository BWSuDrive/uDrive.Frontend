package de.bws.udrive.ui.meineFahrt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeineFahrtViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MeineFahrtViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier wird die Aktuelle Fahrt geplant");
    }

    public LiveData<String> getText() {
        return mText;
    }
}