package inacap.cl.wolk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private RadioGroup roleRadioGroup;
    private EditText emailEditText, passwordEditText, firstNameEditText, lastNameEditText;
    private DatePicker birthDatePicker, graduationDatePicker;
    private Button registerButton;

    private FirebaseAuth auth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        birthDatePicker = findViewById(R.id.birthDatePicker);
        graduationDatePicker = findViewById(R.id.graduationDatePicker);
        registerButton = findViewById(R.id.registerButton);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Configurar botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        int birthYear = birthDatePicker.getYear();
        int gradYear = graduationDatePicker.getYear();

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
        String role = selectedRoleId == R.id.roleEmpresa ? "empresa" : "postulante";

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear usuario en Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Guardar información en Firestore
                        String userId = auth.getCurrentUser().getUid();
                        HashMap<String, Object> userData = new HashMap<>();
                        userData.put("firstName", firstName);
                        userData.put("lastName", lastName);
                        userData.put("birthYear", birthYear);
                        userData.put("gradYear", gradYear);
                        userData.put("role", role);

                        database.collection("Users").document(userId).set(userData)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                        redirectToHome(role);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Error al guardar datos", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToHome(String role) {
        Intent intent;
        if ("empresa".equals(role)) {
            intent = new Intent(RegisterActivity.this, CompanyHomeActivity.class);
        } else {
            intent = new Intent(RegisterActivity.this, PostulantHomeActivity.class);
        }
        startActivity(intent);
        finish();
    }
}