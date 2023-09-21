package de.bws.udrive;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.bws.udrive.utilities.model.uDrive;
import de.bws.udrive.utilities.model.uDriveHandler;


public class SignupTabFragment extends Fragment {

    private EditText edtVorname;
    private EditText edtNachname;
    private EditText edtMail;
    private EditText edtPasswort;
    private EditText edtPasswortConfirm;
    private Button btnSignUp;

    private AlertDialog alertDialog;

    private uDriveHandler.SignUpHandler signUpHandler;

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
        String passwort = edtPasswort.getText().toString();
        String passwortConfirm = edtPasswortConfirm.getText().toString();

        uDrive.SignUp signUp = new uDrive.SignUp(vorname, nachname, mail, passwort, passwortConfirm);

        if(signUp.equalPasswords())
        {
            signUpHandler = new uDriveHandler.SignUpHandler();

            signUpHandler.handle(signUp);

            signUpHandler.getFinishedState().observe(this, this.observeStateChange);
        }
        else
        {
            showErrorDialog("Die eingegebenen Passwörter stimmen nicht überein!");
        }
    };

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

            this.alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();

            errorClose.setOnClickListener(clickedView -> SignupTabFragment.this.alertDialog.dismiss());

            if (this.alertDialog.getWindow() != null)
                this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            this.alertDialog.show();

            ((TextView) view.findViewById(R.id.errorDesc)).setText(errorText);
        }
    }

    /**
     * Observer, der ausgeführt wird, sobald sich Variable in <br>
     * {@link de.bws.udrive.utilities.model.uDriveHandler.SignUpHandler} verändert <br>
     * @author Lucas
     */
    private final Observer<Boolean> observeStateChange = isFinished ->
    {
        if(signUpHandler.isSignUpSuccessful())
        {

        }
        else
        {

        }
    };
}