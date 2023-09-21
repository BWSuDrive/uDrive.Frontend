package de.bws.udrive;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.bws.udrive.utilities.model.uDrive;
import de.bws.udrive.utilities.model.uDriveHandler;

public class LoginTabFragment extends Fragment {

    private EditText username;
    private EditText password;
    private Button loginButton;

    /* Klassenobjekte */
    private uDriveHandler.LoginHandler loginHandler;

    /* AlertDialog für falschen Login */
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);
        initElements(view);
        handleListeners();

        return view;
    }

    /**
     Initialisiert die Elemente auf der UI per ID <br>
     @author Fabian, Lucas
     */
    private void initElements(View view)
    {
        this.username = view.findViewById(R.id.login_email);
        this.password = view.findViewById(R.id.login_password);
        this.loginButton = view.findViewById(R.id.login_button);
    }

    /**
     Setzt die Listener für einzelne Objekte (bspw. Buttons) <br>
     @author Fabian, Lucas
     */
    private void handleListeners() { loginButton.setOnClickListener(loginButtonListener); }

    /**
     Methode, um die HomeActivity zu öffnen <br>
     Wird nur bei erfolgreichem Login aufgerufen <br>
     @author Fabian, Lucas
     */
    private void openHomeActivity() { LoginTabFragment.this.startActivity(new Intent(getContext(), HomeActivity.class)); }

    /**
     Error-Box die angezeigt wird <br>
     Wird nur ausgeführt, wenn Parameter gültig (nicht null und length > 0) ist <br>
     @param errorText Fehlertext, der angezeigt wird <br>
     @author Fabian, Lucas
     */
    private void showErrorDialog(String errorText) {
        if(errorText != null && errorText.length() > 0)
        {
            ConstraintLayout errorLayout = getView().findViewById(R.id.errorConstraintLayout);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.error_dialog, errorLayout);
            Button errorClose = view.findViewById(R.id.errorClose);

            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();

            errorClose.setOnClickListener(clickedView -> alertDialog.dismiss());

            if (alertDialog.getWindow() != null)
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            alertDialog.show();

            ((TextView) view.findViewById(R.id.errorDesc)).setText(errorText);
        }
    }

    /* =================================================================================== */
    /*                          Listener & Observer für Objekte                            */
    /* =================================================================================== */

    /**
     OnClickListener für "Login"-Button <br>
     Wird ausgeführt, sobald auf Login-Button geklickt wird <br>
     Wird in {@link #handleListeners()} gesetzt
     @author Fabian, Lucas
     */
    private View.OnClickListener loginButtonListener = view -> {

        /* Werte aus UI auslesen */
        String user = this.username.getText().toString();
        String password = this.password.getText().toString();

        /* Login-Objekt erstellen, welches für API Call benötigt wird */
        uDrive.Login loginObject = new uDrive.Login("Secretary@udrive.de", "SecretarySTrongPassword!2345", "Secretary@udrive.de");

        /* Überprüfung, ob Name & Passwort Felder eine bestimmte Länge haben */
        boolean inputValid = (user.length() > 3 && password.length() > 5);

        /* API Call */
        if(inputValid)
        {
            loginHandler = new uDriveHandler.LoginHandler();
            Log.i("uDrive.MainActivity.loginButtonListener", "Valid input. Sending API request..");
            loginHandler.handle(loginObject);
            Log.d("uDrive.MainActivity", "Setting an observer for loginHandler");
            loginHandler.getFinishedState().observe(this, this.observeStateChange);

            Toast.makeText(getContext(), "Login wird verarbeitet...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String invalidInput = "Ungültige Eingaben.\n";
            invalidInput += "Benutzername und/oder Passwort zu kurz!\n";
            showErrorDialog(invalidInput);
        }
    };

    /**
     Observer für API Login Call <br>
     Wird ausgeführt, sobald sich der Zustand von {@link de.bws.udrive.utilities.model.uDriveHandler.LoginHandler#isFinished} ändert
     @author Lucas
     */
    private final Observer<Boolean> observeStateChange = isFinished -> {
        if(loginHandler.isLoginSuccessful())
        {
            Log.d("uDrive.MainActivity.observeStateChange", "Login was successful!");
            Log.d("uDrive.MainActivity.observeStateChange", "Value of isFinished is: " + isFinished);
            openHomeActivity();
        }
        else
        {
            showErrorDialog(loginHandler.getError());
        }
    };

    /* =================================================================================== */
}