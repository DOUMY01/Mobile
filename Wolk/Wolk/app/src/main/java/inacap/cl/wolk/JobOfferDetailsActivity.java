package inacap.cl.wolk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JobOfferDetailsActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView, requirementsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer_details);

        // Inicializar vistas
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        requirementsTextView = findViewById(R.id.requirementsTextView);

        // Cargar datos
        loadJobOfferDetails();
    }

    private void loadJobOfferDetails() {
        // Simular datos cargados para la demostración
        String title = "Ejemplo de título";
        String description = "Ejemplo de descripción";
        String requirements = "Ejemplo de requisitos";

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        requirementsTextView.setText(requirements);
    }
}