package inacap.cl.wolk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    private RadioGroup roleRadioGroup;
    private RadioButton roleEmpresa, rolePostulante;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        // Inicializar vistas
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        roleEmpresa = findViewById(R.id.roleEmpresa);
        rolePostulante = findViewById(R.id.rolePostulante);
        nextButton = findViewById(R.id.nextButton);

        // Configurar el botón de continuar
        nextButton.setOnClickListener(v -> {
            int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
            if (selectedRoleId == -1) {
                // Ningún rol seleccionado
                Toast.makeText(this, "Por favor seleccione un rol", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar qué rol se seleccionó
                if (selectedRoleId == R.id.roleEmpresa) {
                    // Redirigir a registro como empresa
                    Intent intent = new Intent(RoleSelectionActivity.this, RegisterActivity.class);
                    intent.putExtra("role", "empresa"); // Pasa el rol de la empresa
                    startActivity(intent);
                } else if (selectedRoleId == R.id.rolePostulante) {
                    // Redirigir a registro como postulante
                    Intent intent = new Intent(RoleSelectionActivity.this, RegisterActivity.class);
                    intent.putExtra("role", "postulante"); // Pasa el rol de postulante
                    startActivity(intent);
                }
            }
        });
    }
}