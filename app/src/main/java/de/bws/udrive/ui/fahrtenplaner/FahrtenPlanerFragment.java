package de.bws.udrive.ui.fahrtenplaner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.bws.udrive.R;
import de.bws.udrive.databinding.FragmentFahrtenplanerBinding;
import de.bws.udrive.utilities.handler.TourPlanHandler;
import de.bws.udrive.utilities.model.TourPlan;
import de.bws.udrive.utilities.uDriveUtilities;

public class FahrtenPlanerFragment extends Fragment {

    private final String TAG = "uDrive." + getClass().getSimpleName();

    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int hour = 0;
    private int minute = 0;
    private FragmentFahrtenplanerBinding binding;
    private TextView tvDateTime;
    private Button btnAbfahrt;
    private Button btnSaveTour;

    private EditText etTimeBeforeEnd;
    private TimePicker tpETA;
    private EditText etStart;
    private EditText etDestination;
    private EditText etComment;

    private TourPlanHandler tourPlanHandler;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FahrtenPlanerViewModel fahrtenPlanerViewModel = new ViewModelProvider(this).get(FahrtenPlanerViewModel.class);
        binding = FragmentFahrtenplanerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btnAbfahrt = binding.btnAbfahrt;
        btnAbfahrt.setOnClickListener(pickDate);
        tvDateTime = binding.tvDateTime;
        btnSaveTour = binding.btnSaveTour;
        btnSaveTour.setOnClickListener(saveTourListener);

        tpETA = binding.tpETA;
        tpETA.setIs24HourView(true);
        etTimeBeforeEnd = binding.etTimeBeforeEnd;
        etStart = binding.etStart;
        etDestination = binding.etDestination;
        etComment = binding.etComment;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getDateTimeCalendar() {
        Calendar c = Calendar.getInstance();

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
    }

    private final View.OnClickListener pickDate = view -> {
        getDateTimeCalendar();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                FahrtenPlanerFragment.this.dateSetListener,
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setBackground(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (86400000 * 7));
        datePickerDialog.show();
    };


    private final DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) ->
    {
        this.year = year;
        this.month = month + 1;
        this.day = day;

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                FahrtenPlanerFragment.this.timeSetListener,
                hour, minute, true);
        timePickerDialog.show();
    };
    private final TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) ->
    {
        this.hour = hour;
        this.minute = minute;

        tvDateTime.setText(uDriveUtilities.convertToGermanDate(year, month, day, hour, minute));

    };
    private final View.OnClickListener saveTourListener = view ->
    {
        String convertedDate = uDriveUtilities.convertToISO8601Date(year, month, day, hour, minute);

        int timeBeforeEnd = (!etTimeBeforeEnd.getText().toString().equals("")) ?
                Integer.valueOf(etTimeBeforeEnd.getText().toString()) :
                0;

        String estTimeArrival = uDriveUtilities.convertTimeToString(tpETA.getHour(), tpETA.getMinute());
        String start = etStart.getText().toString();
        String destination = etDestination.getText().toString();
        String comment = etComment.getText().toString();

        TourPlan tourPlan = new TourPlan(convertedDate, timeBeforeEnd, estTimeArrival, start, destination, comment);

        boolean inputValid =
                (
                    convertedDate.length() != 19 &&
                    timeBeforeEnd >= 0 &&
                    /* estTimeArrival (time) > convertedDate (Time) */
                    start.length() > 0 &&
                    destination.length() > 0
                );

        /* Validierung der Eingaben */
        if(inputValid)
        {
            this.tourPlanHandler = new TourPlanHandler();

            Log.i(TAG, "Started API Call...");
            tourPlanHandler.handle(tourPlan);
            tourPlanHandler.getFinishedState().observe(this, this.observeStateChange);
        }
        else
        {
            Toast.makeText(getContext(), "Bitte überprüfe deine Eingaben!", Toast.LENGTH_LONG).show();
        }
    };

    private final Observer<Boolean> observeStateChange = isFinished ->
    {
        if(isFinished)
        {
            Log.i(TAG, "isFinished: " + isFinished);
            Log.i(TAG, "Infotext: " + tourPlanHandler.getInformationString());
            if(tourPlanHandler.isTourPlanSuccessful())
            {
                Log.i(TAG, "Fahrt wurde erfolgreich registriert!");
                Toast.makeText(getContext(), tourPlanHandler.getInformationString(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.e(TAG, "Fahrt wurde nicht erfolgreich registriert!");
                Toast.makeText(getContext(), tourPlanHandler.getInformationString(), Toast.LENGTH_LONG).show();
            }
        }
    };
}