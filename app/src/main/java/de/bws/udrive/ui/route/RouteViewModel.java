package de.bws.udrive.ui.route;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RouteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RouteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hier werden die Routen angezeigt");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
