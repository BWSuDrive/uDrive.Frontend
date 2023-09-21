package de.bws.udrive.ui.fahrtenplaner;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import de.bws.udrive.R;
import de.bws.udrive.databinding.FragmentFahrtenplanerBinding;

public class FahrtenPlanerFragment extends Fragment {

    private FragmentFahrtenplanerBinding binding;
    private TextView tvDatum;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FahrtenPlanerViewModel fahrtenPlanerViewModel = new ViewModelProvider(this).get(FahrtenPlanerViewModel.class);

        binding = FragmentFahrtenplanerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //tvDatum = binding.tvDatum;
        //tvDatum.setOnClickListener(clickedOnDate);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final View.OnClickListener clickedOnDate = view -> {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.Theme_UDriveFrontend,
                FahrtenPlanerFragment.this.dateSetListener,
                year, month, day);

        datePickerDialog.show();

        DatePicker datePicker = datePickerDialog.getDatePicker();

        /* Maximales Datum = T + 7 */
        datePicker.setMaxDate(System.currentTimeMillis() + (86400000 * 7));
        datePicker.setScaleX(.75f);
        datePicker.setScaleY(.75f);
    };

    private final DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) ->
    {
        month += 1;
        String datum = day + "." + month + "." + year;

        tvDatum.setText(datum);
    };
}