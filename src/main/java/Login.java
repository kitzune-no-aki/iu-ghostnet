import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    private PersonDAO personDAO;

    private String name;
    private String surname;
    private String phoneNumber;

    private Person loggedInUser;

    public String login() {
        try {
            // Check if the person exists
            Person existingPerson;
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                // Use the method without phone if no phone number is provided
                existingPerson = personDAO.findByNameAndSurname(name, surname);
            } else {
                // Use the method with phone number if provided
                existingPerson = personDAO.findByNameAndPhone(name, surname, phoneNumber);
            }

            if (existingPerson != null) {
                loggedInUser = existingPerson;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Successful", "Welcome, " + loggedInUser.getName() + "!"));
                return "index.xhtml?faces-redirect=true"; // Redirect to the dashboard
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid credentials."));
                return null; // Stay on the login page
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", "An unexpected error occurred. Please try again."));
            return null; // Stay on the login page
        }
    }

    public String logout() {
        loggedInUser = null; // Clear the session
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout Successful", "You have been logged out."));
        return "index.xhtml?faces-redirect=true"; // Redirect to the home page
    }

    // Getters and setters
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

    public Person getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}