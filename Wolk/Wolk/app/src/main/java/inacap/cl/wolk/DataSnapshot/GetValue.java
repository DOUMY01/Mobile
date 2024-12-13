package inacap.cl.wolk.DataSnapshot;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inacap.cl.wolk.JobOffer;

public class GetValue {

    private static final String TAG = "GetValue";

    // Interfaz para devolver las ofertas de trabajo
    public interface JobOfferCallback {
        void onCallback(List<JobOffer> jobOffers);
    }

    // Método para obtener todas las ofertas de trabajo desde Firebase
    public static void getJobOffers(JobOfferCallback callback) {
        DatabaseReference jobOffersRef = FirebaseDatabase.getInstance().getReference("jobOffers");

        jobOffersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<JobOffer> jobOffers = new ArrayList<>();

                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    JobOffer jobOffer = jobSnapshot.getValue(JobOffer.class);
                    if (jobOffer != null) {
                        jobOffers.add(jobOffer);
                    }
                }

                // Devolver la lista a través de la interfaz
                callback.onCallback(jobOffers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error al leer las ofertas de trabajo", error.toException());
            }
        });
    }
}
