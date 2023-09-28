package de.bws.udrive.ui.fahrtenplaner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FahrtenPlanerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FahrtenPlanerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier werden die Fahrten geplant");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
