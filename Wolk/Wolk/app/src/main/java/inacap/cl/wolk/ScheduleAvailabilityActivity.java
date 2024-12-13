package inacap.cl.wolk;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ScheduleAvailabilityActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private DatabaseReference scheduleRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_availability);

        // Inicializar vistas y Firebase
        calendarView = findViewById(R.id.calendarView);
        scheduleRef = FirebaseDatabase.getInstance().getReference("schedules");

        // Escuchar cambios en la selección de fechas
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                saveAvailability(year, month + 1, dayOfMonth);
            }
        });
    }

    private void saveAvailability(int year, int month, int dayOfMonth) {
        String selectedDate = year + "-" + month + "-" + dayOfMonth;

        HashMap<String, String> availabilityData = new HashMap<>();
        availabilityData.put("date", selectedDate);

        scheduleRef.push().setValue(availabilityData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Disponibilidad agregada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar disponibilidad", Toast.LENGTH_SHORT).show();
            }
        });
    }
}