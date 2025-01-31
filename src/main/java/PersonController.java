import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class PersonController {

    @Inject
    private PersonDAO personDAO;

    public boolean personExists(String name, String surname, String phoneNumber) {
        return personDAO.findByNameAndPhone(name, surname, phoneNumber) != null;
    }

    public void registerPerson(String name, String surname, String phoneNumber) {
        Person newPerson = new Person(name, surname, phoneNumber);
        personDAO.save(newPerson);
    }
}