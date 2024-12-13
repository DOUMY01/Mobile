package inacap.cl.wolk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddJobOfferActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, requirementsEditText;
    private Button postJobButton;
    private DatabaseReference jobOffersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_offer);

        // Inicializar vistas
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        requirementsEditText = findViewById(R.id.requirementsEditText);
        postJobButton = findViewById(R.id.postJobButton);

        // Referencia a Firebase
        jobOffersRef = FirebaseDatabase.getInstance().getReference("jobOffers");

        // Configurar el botón de publicación
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJobOffer();
            }
        });
    }

    private void postJobOffer() {
        // Obtener datos ingresados
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String requirements = requirementsEditText.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(requirements)) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar ID único para la oferta
        String jobId = jobOffersRef.push().getKey();

        // Crear un mapa con los datos de la oferta
        HashMap<String, String> jobData = new HashMap<>();
        jobData.put("title", title);
        jobData.put("description", description);
        jobData.put("requirements", requirements);

        // Guardar en Firebase
        if (jobId != null) {
            jobOffersRef.child(jobId).setValue(jobData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Oferta publicada con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad
                } else {
                    Toast.makeText(this, "Error al publicar la oferta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}