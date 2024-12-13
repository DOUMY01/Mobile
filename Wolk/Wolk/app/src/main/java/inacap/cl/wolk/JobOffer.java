package inacap.cl.wolk;

public class JobOffer {
    private String jobId;
    private String title;
    private String description;
    private String requirements;

    // Constructor vacío requerido para Firebase
    public JobOffer() {
    }

    // Constructor con parámetros
    public JobOffer(String jobId, String title, String description, String requirements) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
    }

    // Getters y Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    // Métodos adicionales para id, si es necesario
    public String getId() {
        return jobId; // Devuelve el identificador único
    }

    public void setId(String id) {
        this.jobId = id; // Asigna el identificador único
    }
}