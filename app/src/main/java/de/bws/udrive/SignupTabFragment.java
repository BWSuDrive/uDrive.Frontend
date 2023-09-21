package de.bws.udrive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignupTabFragment extends Fragment {

    private EditText edtVorname;
    private EditText edtNachname;
    private EditText edtMail;
    private EditText edtPasswort;
    private EditText edtPasswortConfirm;
    private Button btnSignUp;

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
    private final View.OnClickListener signUpListener = view -> {

    };
}