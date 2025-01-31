import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GhostNetController {

    @Inject
    private GhostNetDAO ghostNetDAO;

    @Inject
    private Login login;

    public void add(Double latitude, Double longitude, Float estimatedSize, String status) {
        GhostNet newGhostNet = new GhostNet(latitude, longitude, estimatedSize, status);
        if (login.isLoggedIn()) {
            Person loggedInUser = login.getLoggedInUser();
            newGhostNet.setReporterName(loggedInUser.getName() + " " + loggedInUser.getSurname());
        } else {
            newGhostNet.setReporterName("Anonymous");
        }
        ghostNetDAO.save(newGhostNet);
    }

    public List<GhostNet> getAllNets() {
        return ghostNetDAO.getAllNets();
    }

    public GhostNet findById(Long id) {
        return ghostNetDAO.findById(id);
    }

    public void update(GhostNet ghostNet) {
        ghostNetDAO.update(ghostNet);
    }
}