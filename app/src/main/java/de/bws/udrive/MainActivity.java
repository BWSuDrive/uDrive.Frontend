package de.bws.udrive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

            if(inputValid)
            {
                Log.i("uDrive.MainActivity.loginButtonListener", "Valid input. Sending API request..");
                loginRequest.enqueue(MainActivity.this.loginResponse);
            }
            else
            {
                errorTextBuffer.append("Die Eingaben sind ungültig.").append("\n");
                errorTextBuffer.append("Der Name muss mindestens 3, das Passwort mindestens 5 Zeichen lang sein!").append("\n");
            }

            if(uDrive.Generic.isLoginSuccessful())
            {
                Log.i("uDrive.MainActivity.loginButtonListener", "Login successful. Opening MainActivity..");
                MainActivity.this.openHomeActivity();
            }
            else
            {
                errorTextBuffer.append("Der Login war nicht erfolgreich!").append("\n");
            }

            if(errorTextBuffer.toString().length() > 0)
                MainActivity.this.showErrorDialog(errorTextBuffer.toString());
        }
    };

    private View.OnClickListener errorDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.this.alertDialog.dismiss();
            Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Wird ausgeführt, sobald
     */
    private Callback<uDrive.LoginResponse> loginResponse = new Callback<uDrive.LoginResponse>() {
        private StringBuffer errorTextBuffer;
        @Override
        public void onResponse(Call<uDrive.LoginResponse> call, Response<uDrive.LoginResponse> response) {
            errorTextBuffer = new StringBuffer();
            /* Antwort OK von API */
            if(response.code() == 200)
            {
                if(response.body() != null)
                {
                    String bearerToken = response.body().getData().get("token");

                    if(bearerToken != null)
                        uDrive.Generic.setToken(bearerToken);

                    Log.d("uDrive.MainActivity.loginResponse.onResponse", "Token is: " + uDrive.Generic.getToken());
                    Log.d("uDrive.MainActivity.loginResponse.onResponse", "Login was successful. Setting generic value");

                    uDrive.Generic.setLoginSuccessful(true);

                }
                else /* Body null */
                {
                    errorTextBuffer.append("Responsebody was null.").append("\n");
                    errorTextBuffer.append("Unknown error occured!").append("\n");
                    errorTextBuffer.append(response.errorBody().toString()).append("\n");

                    showErrorDialog(errorTextBuffer.toString());
                }
            }
            else /* Antwort nicht OK */
            {
                errorTextBuffer.append("Der Login war nicht möglich!").append("\n");
                errorTextBuffer.append("Fehler-Code: " + response.code()).append("\n");
                errorTextBuffer.append("Fehler: " + response.message()).append("\n");

                showErrorDialog(errorTextBuffer.toString());
            }
        }

        @Override
        public void onFailure(Call<uDrive.LoginResponse> call, Throwable t) {
            errorTextBuffer = new StringBuffer();

            errorTextBuffer.append("Die Kommunikation mit der API war nicht möglich!").append("\n");
            errorTextBuffer.append(t.getMessage()).append("\n");
            Log.wtf(Tag.FAILURE, errorTextBuffer.toString());

            showErrorDialog(errorTextBuffer.toString());
        }
    };

    /* =================================================================================== */

}