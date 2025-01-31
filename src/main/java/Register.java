import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Register {
    @Inject
    private PersonController personController; // Inject the controller for person logic

    private String name;
    private String surname;
    private String phoneNumber;

    public String register() {
        try {
            String effectivePhoneNumber = (phoneNumber == null || phoneNumber.trim().isEmpty()) ? null : phoneNumber;
            // Check if the person already exists
            if (personController.personExists(name, surname, effectivePhoneNumber)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Failed",
                                "Person already exists in the system."));
                return null; // Stay on the registration page
            }

            // Register the person through the controller
            personController.registerPerson(name, surname, effectivePhoneNumber);

            // Add a success message
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful", "Welcome, " + name + "!"));

            // Redirect to the index page
            return "index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            // Add an error message
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "Please try again."));
            return null; // Stay on the registration page
        }
    }

    // Getters and setters for form fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}