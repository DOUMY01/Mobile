package inacap.cl.wolk.DataSnapshot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import inacap.cl.wolk.R;

public class CompanyScheduleActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button selectDateButton;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_schedule); // Vincula con el layout correspondiente

        // Inicialización de vistas
        calendarView = findViewById(R.id.calendarView);
        selectDateButton = findViewById(R.id.selectDateButton);

        // Escucha para seleccionar una fecha en el CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Formatear la fecha seleccionada
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

        // Acción del botón para confirmar la fecha
        selectDateButton.setOnClickListener(v -> {
            if (selectedDate != null) {
                Toast.makeText(CompanyScheduleActivity.this, "Fecha seleccionada: " + selectedDate, Toast.LENGTH_SHORT).show();
                // Aquí puedes guardar la fecha seleccionada en Firebase o usarla para generar el enlace de Zoom
            } else {
                Toast.makeText(CompanyScheduleActivity.this, "Por favor, selecciona una fecha.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}