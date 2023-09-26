package de.bws.udrive.ui.fahrtenplaner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Objects;

import de.bws.udrive.R;
import de.bws.udrive.databinding.FragmentFahrtenplanerBinding;
import de.bws.udrive.utilities.model.TourPlan;

public class FahrtenPlanerFragment extends Fragment {

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
    private String DateTime;
    private int timeBeforeEnd = 0;
    private String eTA;
    private String start;
    private String destination;
    private String comment;


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
        datePickerDialog.getDatePicker().setBackground(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (86400000 * 7));
        datePickerDialog.show();
    };


    private final DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) ->
    {
        this.year = year;
        this.month = month + 1;
        this.day = day;
        Log.i("uDrive.Date", this.year + " " + this.month + " " + this.day);

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
        Log.i("uDrive.Date", this.hour + " " + this.minute);
        tvDateTime.setText(this.day + "-" + this.month + "-" + this.year + " " + this.hour + ":" + this.minute);

    };
    private final View.OnClickListener saveTourListener = view -> {
        DateTime = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":00Z";
        timeBeforeEnd = Integer.valueOf(etTimeBeforeEnd.getText().toString());
        eTA = tpETA.getHour() + ":" + tpETA.getMinute()+":00";
        start = etStart.getText().toString();
        destination = etDestination.getText().toString();
        comment = etComment.getText().toString();

        TourPlan tourPlan = new TourPlan(DateTime,timeBeforeEnd,eTA,start,destination,comment);
    };
}