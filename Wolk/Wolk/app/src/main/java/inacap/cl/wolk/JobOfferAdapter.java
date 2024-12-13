package inacap.cl.wolk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobOfferAdapter extends RecyclerView.Adapter<JobOfferAdapter.JobOfferViewHolder> {

    private ArrayList<JobOffer> jobOffersList;
    private Context context;

    public JobOfferAdapter(ArrayList<JobOffer> jobOffersList, Context context) {
        this.jobOffersList = jobOffersList;
        this.context = context;
    }

    // Crear la vista para cada ítem en el RecyclerView
    @NonNull
    @Override
    public JobOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_job_offer, parent, false);
        return new JobOfferViewHolder(itemView);
    }

    // Asignar datos a cada ítem
    @Override
    public void onBindViewHolder(@NonNull JobOfferViewHolder holder, int position) {
        JobOffer jobOffer = jobOffersList.get(position);

        // Asignar valores a los elementos de la vista
        holder.jobTitleTextView.setText(jobOffer.getTitle());
        holder.jobDescriptionTextView.setText(jobOffer.getDescription());
        holder.jobRequirementsTextView.setText(jobOffer.getRequirements());

        // Puedes agregar un listener para el botón "Postularse"
        holder.applyButton.setOnClickListener(v -> {
            // Lógica para postularse a la oferta
            Intent intent = new Intent(context, ApplyJobActivity.class);
            intent.putExtra("jobId", jobOffer.getJobId());
            context.startActivity(intent);
        });
    }

    // Número total de ítems
    @Override
    public int getItemCount() {
        return jobOffersList.size();
    }

    // Clase interna para los ítems del RecyclerView
    public static class JobOfferViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitleTextView, jobDescriptionTextView, jobRequirementsTextView;
        Button applyButton;

        public JobOfferViewHolder(View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            jobDescriptionTextView = itemView.findViewById(R.id.jobDescriptionTextView);
            jobRequirementsTextView = itemView.findViewById(R.id.jobRequirementsTextView);
            applyButton = itemView.findViewById(R.id.applyButton);
        }
    }
}