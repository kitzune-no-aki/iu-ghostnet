import jakarta.persistence.*;

@Entity
@Table(name = "ghost_nets")
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double latitude;
    private double longitude;
    private float estimatedSize;
    private String status;

    @Column(nullable = true)
    private String reporterName;

    @ManyToOne
    @JoinColumn(name = "retrieving_person_id")
    private Person retrievingPerson;

    // Constructor
    public GhostNet() {
        super();
    }

    public GhostNet(Double latitude, Double longitude, Float estimatedSize, String status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.estimatedSize = estimatedSize;
        this.status = status;
    }

    // Getter / Setter
    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(float estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Person getRetrievingPerson() {
        return retrievingPerson;
    }

    public void setRetrievingPerson(Person retrievingPerson) {
        this.retrievingPerson = retrievingPerson;
    }
}