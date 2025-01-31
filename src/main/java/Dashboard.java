import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class Dashboard {

    @Inject
    private GhostNetController ghostNetController;

    @Inject
    private Login login;

    private Long selectedNetId;  // Store selected ID for claiming
    private String selectedStatusFilter = "";  // Stores selected filter

    public List<GhostNet> getAllNets() {
        List<GhostNet> allNets = ghostNetController.getAllNets();

        if (selectedStatusFilter != null && !selectedStatusFilter.isEmpty()) {
            return allNets.stream()
                    .filter(net -> net.getStatus().equalsIgnoreCase(selectedStatusFilter))
                    .collect(Collectors.toList());
        }
        return allNets;
    }

    public void applyFilter() {
        System.out.println("🔍 Applying filter: " + selectedStatusFilter);
    }

    public void claimNet() {
        if (selectedNetId == null) {
            return;
        }

        if (!login.isLoggedIn() || login.getLoggedInUser().getPhoneNumber() == null || login.getLoggedInUser().getPhoneNumber().isEmpty()) {
            return;
        }

        GhostNet ghostNet = ghostNetController.findById(selectedNetId);
        if (ghostNet != null && ghostNet.getRetrievingPerson() == null) {
            ghostNet.setRetrievingPerson(login.getLoggedInUser());
            ghostNet.setStatus("Retrieving");

            ghostNetController.update(ghostNet);
        }
    }

    public void reportMissing() {
        if (selectedNetId == null || !login.isLoggedIn()) {
            return;
        }

        GhostNet ghostNet = ghostNetController.findById(selectedNetId);
        if (ghostNet == null || ghostNet.getRetrievingPerson() == null) {
            return;
        }

        if (!ghostNet.getRetrievingPerson().getId().equals(login.getLoggedInUser().getId())) {
            return;
        }

        ghostNet.setStatus("Missing");
        ghostNetController.update(ghostNet);
    }

    public void cancel() {
        if (selectedNetId == null || !login.isLoggedIn()) {
            return;
        }

        GhostNet ghostNet = ghostNetController.findById(selectedNetId);
        if (ghostNet == null || ghostNet.getRetrievingPerson() == null) {
            return;
        }

        if (!ghostNet.getRetrievingPerson().getId().equals(login.getLoggedInUser().getId())) {
            return;
        }

        ghostNet.setStatus("Reported");
        ghostNet.setRetrievingPerson(null);
        ghostNetController.update(ghostNet);
    }

    public void reportRetrieved() {
        if (selectedNetId == null || !login.isLoggedIn()) {
            return;
        }

        GhostNet ghostNet = ghostNetController.findById(selectedNetId);
        if (ghostNet == null || ghostNet.getRetrievingPerson() == null) {
            return;
        }

        if (!ghostNet.getRetrievingPerson().getId().equals(login.getLoggedInUser().getId())) {
            return;
        }

        ghostNet.setStatus("Retrieved");
        ghostNetController.update(ghostNet);
    }

    public Long getSelectedNetId() {
        return selectedNetId;
    }

    public void setSelectedNetId(Long selectedNetId) {
        this.selectedNetId = selectedNetId;
    }

    public String getSelectedStatusFilter() {
        return selectedStatusFilter;
    }

    public void setSelectedStatusFilter(String selectedStatusFilter) {
        this.selectedStatusFilter = selectedStatusFilter;
    }
}