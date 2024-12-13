package inacap.cl.wolk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CompanyHomeActivity extends AppCompatActivity {

    private Button addJobOfferButton, scheduleButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        // Inicializar los botones
        addJobOfferButton = findViewById(R.id.addJobOfferButton);
        scheduleButton = findViewById(R.id.scheduleButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Botón para agregar ofertas de trabajo
        addJobOfferButton.setOnClickListener(v -> {
            Intent intent = new Intent(CompanyHomeActivity.this, AddJobOfferActivity.class);
            startActivity(intent);
        });

        // Botón para configurar horario de entrevistas
        scheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(CompanyHomeActivity.this, ScheduleAvailabilityActivity.class);
            startActivity(intent);
        });

        // Botón para cerrar sesión
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Cierra la sesión en Firebase
            Intent intent = new Intent(CompanyHomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia el stack de actividades
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });
    }
}
