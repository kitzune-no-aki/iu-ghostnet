import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Report {
    @Inject
    private GhostNetController ghostNetController;

    private Double latitude;
    private Double longitude;
    private Float estimatedSize;
    private String status;

    public String submitReport() {
        String status = "Reported";

        // Pass the data to the GhostNetController
        ghostNetController.add(latitude, longitude, estimatedSize, status);
        return "index.xhtml?faces-redirect=true"; // Redirect to the homepage
    }

    // Getters and setters
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Float getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Float estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}