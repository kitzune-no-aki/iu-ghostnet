import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Column(nullable = true)
    private String phoneNumber;

    // Constructor
    public Person() {
        super();
    }

    public Person(String name, String surname, String phoneNumber) {
        super();
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    // Getter / Setter
    public Long getId() {
        return id;
    }

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