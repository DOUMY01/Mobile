package inacap.cl.wolk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostulantHomeActivity extends AppCompatActivity {

    private RecyclerView jobOffersRecyclerView;
    private ArrayList<JobOffer> jobOffersList;
    private JobOfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulant_home);

        // Inicializar RecyclerView
        jobOffersRecyclerView = findViewById(R.id.jobOffersRecyclerView);
        jobOffersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobOffersList = new ArrayList<>();
        adapter = new JobOfferAdapter(jobOffersList, this);
        jobOffersRecyclerView.setAdapter(adapter);

        // Configurar el botón de cerrar sesión
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Cierra la sesión en Firebase
            Intent intent = new Intent(PostulantHomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia el stack de actividades
            startActivity(intent);
            finish();
        });

        // Cargar ofertas de trabajo desde Firebase
        loadJobOffers();
    }

    private void loadJobOffers() {
        DatabaseReference jobOffersRef = FirebaseDatabase.getInstance().getReference("jobOffers");

        jobOffersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobOffersList.clear();
                for (DataSnapshot offerSnapshot : snapshot.getChildren()) {
                    JobOffer jobOffer = offerSnapshot.getValue(JobOffer.class);
                    if (jobOffer != null) {
                        jobOffer.setId(offerSnapshot.getKey());
                        jobOffersList.add(jobOffer);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostulantHomeActivity.this, "Error al cargar ofertas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}