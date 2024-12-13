package inacap.cl.wolk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();

        // Inicializar vistas
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Configurar el botón de inicio de sesión
        loginButton.setOnClickListener(v -> login());

        // Configurar el botón de registro
        registerButton.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Iniciar sesión con Firebase Authentication
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            checkUserRole(userId); // Llamar al método para verificar el rol
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error de autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserRole(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtener el documento del usuario desde la colección "Users"
        db.collection("Users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                        // Leer el campo "role" del documento
                        String role = task.getResult().getString("role");

                        if (role != null) {
                            // Redirigir según el rol
                            if ("empresa".equals(role)) {
                                startActivity(new Intent(MainActivity.this, CompanyHomeActivity.class));
                            } else if ("postulante".equals(role)) {
                                startActivity(new Intent(MainActivity.this, PostulantHomeActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Rol no válido: " + role, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Campo 'role' no encontrado", Toast.LENGTH_SHORT).show();
                        }

                        // Finalizar la actividad actual
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Documento no encontrado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error al consultar Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}