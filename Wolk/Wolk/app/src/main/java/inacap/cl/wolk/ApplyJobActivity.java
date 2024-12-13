package inacap.cl.wolk;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;

public class ApplyJobActivity extends AppCompatActivity {

    private EditText uploadCvEditText, interviewDateEditText;
    private Button uploadCvButton, applyButton, selectDateButton;
    private CalendarView calendarView;

    private DatabaseReference applicationsRef;
    private StorageReference storageRef;
    private String selectedJobId, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        // Obtener el ID de la oferta seleccionada (pasada por intent)
        selectedJobId = getIntent().getStringExtra("jobId");

        // Inicializar vistas
        uploadCvEditText = findViewById(R.id.uploadCvEditText);
        interviewDateEditText = findViewById(R.id.interviewDateEditText);
        uploadCvButton = findViewById(R.id.uploadCvButton);
        applyButton = findViewById(R.id.applyButton);
        calendarView = findViewById(R.id.calendarView);
        selectDateButton = findViewById(R.id.selectDateButton);

        // Inicializar referencias de Firebase
        applicationsRef = FirebaseDatabase.getInstance().getReference("applications");
        storageRef = FirebaseStorage.getInstance().getReference("CVs");

        // Configurar selección de fecha desde el CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth; // Formatear fecha
        });

        selectDateButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(selectedDate)) {
                interviewDateEditText.setText(selectedDate);
                Toast.makeText(this, "Fecha seleccionada: " + selectedDate, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona una fecha del calendario", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón para cargar CV
        uploadCvButton.setOnClickListener(v -> uploadCv());

        // Configurar el botón para enviar la postulación
        applyButton.setOnClickListener(v -> submitApplication());
    }

    private void uploadCv() {
        // Simulación de carga de CV
        uploadCvEditText.setText("CV cargado (simulado)");
        Toast.makeText(this, "Funcionalidad de carga no implementada", Toast.LENGTH_SHORT).show();
    }

    private void submitApplication() {
        String cvPath = uploadCvEditText.getText().toString().trim();
        String interviewDate = interviewDateEditText.getText().toString().trim();

        if (TextUtils.isEmpty(cvPath) || TextUtils.isEmpty(interviewDate)) {
            Toast.makeText(this, "Completa todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar la postulación en Firebase
        String applicationId = applicationsRef.push().getKey();
        HashMap<String, String> applicationData = new HashMap<>();
        applicationData.put("jobId", selectedJobId);
        applicationData.put("cvPath", cvPath);
        applicationData.put("interviewDate", interviewDate);

        if (applicationId != null) {
            applicationsRef.child(applicationId).setValue(applicationData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Postulación enviada con éxito", Toast.LENGTH_SHORT).show();
                    generateZoomLink(interviewDate); // Generar enlace de Zoom
                } else {
                    Toast.makeText(this, "Error al enviar la postulación", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void generateZoomLink(String interviewDate) {
        // Generar enlace de Zoom simulado
        String zoomLink = "https://us04web.zoom.us/j/1234567890?pwd=abcd1234"; // Enlace fijo o dinámico
        String interviewId = FirebaseDatabase.getInstance().getReference("interviews").push().getKey();

        if (interviewId != null) {
            HashMap<String, String> interviewData = new HashMap<>();
            interviewData.put("date", interviewDate);
            interviewData.put("zoomLink", zoomLink);

            FirebaseDatabase.getInstance().getReference("interviews").child(interviewId).setValue(interviewData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Entrevista agendada. Link de Zoom: " + zoomLink, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Error al generar enlace de Zoom", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}