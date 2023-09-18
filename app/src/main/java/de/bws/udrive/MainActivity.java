package de.bws.udrive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.Tag;
import de.bws.udrive.utilities.model.uDrive;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login-Activity (App wird hier gestartet)
 * Öffnet bei Erfolgreichem Login {@link HomeActivity}
 */
public class MainActivity extends AppCompatActivity {

    /* Lokale Variabeln */
    private EditText username;
    private EditText password;
    private Button loginButton;
    private uDrive.LoginHandler loginHandler;

    /* AlertDialog für falschen Login */
    private AlertDialog alertDialog;

    /* "Konstruktor"-Methode */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Die Elemente per ID initialisieren
        this.initElements();

        // Button Klicks registrieren
        this.handleListeners();
    }

    /*
        Initialisiert die Elemente auf der UI per ID
        Entwickelt von: Fabian & Lucas
     */
    private void initElements()
    {
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.loginbutton);
    }

    /*
        Setzt die Listener für einzelne Objekte
        Entwickelt von: Fabian & Lucas
     */
    private void handleListeners()
    {
        loginButton.setOnClickListener(loginButtonListener);
    }

    /* =================================================================================== */


    /*
        Methode um die HomeActivity zu öffnen
        Entwickelt von: Fabian, Lucas
        Angepasst durch: Lucas
    */
    private void openHomeActivity()
    {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        homeIntent.putExtra("signedInUser", username.getText().toString());
        MainActivity.this.startActivity(homeIntent);
    }

    /*
        Error-Dialog Box die bei ungültigem Login angezeigt wird
        Entwickelt von: Fabian
        Angepasst durch: Lucas
    */
    private void showErrorDialog(String errorText) {
        ConstraintLayout errorLayout = findViewById(R.id.errorConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.error_dialog, errorLayout);
        Button errorClose = view.findViewById(R.id.errorClose);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        this.alertDialog = builder.create();

        errorClose.setOnClickListener(errorDialogListener);
        if (this.alertDialog.getWindow() != null)
        {
            this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        this.alertDialog.show();

        /* text == null ? nothing : show */

        if(errorText != null)
            ((TextView) view.findViewById(R.id.errorDesc)).setText(errorText);
    }

    /* =================================================================================== */

    /*
        OnClickListener für "Login"-Button
        Entwickelt von: Fabian, Lucas
    */
    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        private StringBuffer errorTextBuffer;
        @Override
        public void onClick(View view) {
            errorTextBuffer = new StringBuffer();

            /* User-Input in Variablen speichern */
            String inputUsername = MainActivity.this.username.getText().toString();
            String inputPassword = MainActivity.this.password.getText().toString();

            /* Login-Objekt erstellen --> Für API Call */
            uDrive.Login loginObject = new uDrive.Login(inputUsername, "SecretarySTrongPassword!2345", "Secretary@udrive.de");

            /* API Call vorbereiten und Login-Objekt übergeben */
            Call<uDrive.LoginResponse> loginRequest = APIClient.getAPI().create(APIInterface.class).sendLoginRequest(loginObject);

            /* Überprüfung, ob Name & Password Felder eine bestimmte Länge haben */
            boolean inputValid = (inputUsername.length() > 3 && inputPassword.length() > 5);
            MainActivity.this.loginHandler = new uDrive.LoginHandler();

            /* API Call */
            if(inputValid)
            {
                Log.i("uDrive.MainActivity.loginButtonListener", "Valid input. Sending API request..");
                loginHandler.handle(loginObject);
                loginHandler.getIsFinished().observe(MainActivity.this, MainActivity.this.observeStateChange);
            }
            else
            {
                errorTextBuffer.append("Die Eingaben sind ungültig.").append("\n");
                errorTextBuffer.append("Der Name muss mindestens 3, das Passwort mindestens 5 Zeichen lang sein!").append("\n");
            }
        }
    };

    private View.OnClickListener errorDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.this.alertDialog.dismiss();
            Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
        }
    };

    private Observer<Boolean> observeStateChange = isFinished -> {
        boolean loginSuccessful = loginHandler.isLoginSuccessful();

        if(loginSuccessful && isFinished.booleanValue())
        {
            openHomeActivity();
        }
        else
        {
            showErrorDialog(loginHandler.getError());
        }
    };

    /* =================================================================================== */

}