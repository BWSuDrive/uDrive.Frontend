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

import de.bws.udrive.utilities.handler.SignUpHandler;
import de.bws.udrive.utilities.model.SignUp;


public class SignupTabFragment extends Fragment {

    private final String TAG = "uDrive." + getClass().getSimpleName();

    private EditText edtVorname;
    private EditText edtNachname;
    private EditText edtMail;
    private EditText edtPhone;
    private EditText edtPasswort;
    private EditText edtPasswortConfirm;
    private Button btnSignUp;

    private SignUpHandler signUpHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);

        initElements(view);
        handleListeners();

        return view;
    }

    private void initElements(View view)
    {
        this.edtVorname = view.findViewById(R.id.signup_vorname);
        this.edtNachname = view.findViewById(R.id.signup_nachname);
        this.edtMail = view.findViewById(R.id.signup_email);
        this.edtPhone = view.findViewById(R.id.signup_phone);
        this.edtPasswort = view.findViewById(R.id.signup_password);
        this.edtPasswortConfirm = view.findViewById(R.id.signup_confirm);

        this.btnSignUp = view.findViewById(R.id.signup_button);
    }

    private void handleListeners()
    {
        this.btnSignUp.setOnClickListener(signUpListener);
    }

    /**
     * Listener, der ausgeführt wird, sobald auf den Button "SignUp" geklickt wird
     *
     * @author Lucas
     */
    private final View.OnClickListener signUpListener = view ->
    {
        String vorname = edtVorname.getText().toString();
        String nachname = edtNachname.getText().toString();
        String mail = edtMail.getText().toString();
        String phone = edtPhone.getText().toString();
        String passwort = edtPasswort.getText().toString();
        String passwortConfirm = edtPasswortConfirm.getText().toString();

        SignUp signUpObject = new SignUp(vorname, nachname, mail, passwort, phone);

        boolean inputValid = (
                vorname.length() > 3 &&
                nachname.length() > 3 &&
                mail.length() > 6 &&
                phone.length() > 6 &&
                passwort.length() > 5 &&
                passwortConfirm.length() > 5
        );

        if(inputValid)
        {
            if (signUpObject.equalPasswords(passwortConfirm))
            {
                signUpHandler = new SignUpHandler();
                Log.i(TAG, "API Call...");
                signUpHandler.handle(signUpObject);
                signUpHandler.getFinishedState().observe(this, this.observeStateChange);

                Toast.makeText(getContext(), "Registrierung wird verarbeitet...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                showErrorDialog("Die eingegebenen Passwörter stimmen nicht überein!");
            }
        }
        else
        {
            String errorText = "Bitte überprüfe deine Eingaben!\n";
            errorText += "Deine Eingaben sind zu kurz!";
            showErrorDialog(errorText);
        }
    };

    /**
     Methode, um die HomeActivity zu öffnen <br>
     Wird nur bei erfolgreichem Login aufgerufen <br>
     @author Fabian, Lucas
     */
    private void openHomeActivity() { SignupTabFragment.this.startActivity(new Intent(getContext(), HomeActivity.class)); }


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

    /**
     * Observer, der ausgeführt wird, sobald sich Variable in <br>
     * {@link SignUpHandler} verändert <br>
     * @author Lucas
     */
    private final Observer<Boolean> observeStateChange = isFinished ->
    {
        Log.i(TAG, "Observer reacted!");
        if(signUpHandler.isSignUpSuccessful())
        {
            Log.i(TAG, "Registrierung erfolgreich!");
            Log.i(TAG, "User muss freigegeben werden!");
            Toast.makeText(getContext(), "Registrierung erfolgreich.\nFreischaltung erforderlich!", Toast.LENGTH_LONG).show();
            clearInputFields();
        }
        else
        {
            Log.i(TAG, "SignUp failure");
            Log.i(TAG, signUpHandler.getInformationString());
            showErrorDialog(signUpHandler.getInformationString());
        }
    };

    private void clearInputFields()
    {
        this.edtVorname.getText().clear();
        this.edtNachname.getText().clear();
        this.edtMail.getText().clear();
        this.edtPhone.getText().clear();
        this.edtPasswort.getText().clear();
        this.edtPasswortConfirm.getText().clear();
    }
}