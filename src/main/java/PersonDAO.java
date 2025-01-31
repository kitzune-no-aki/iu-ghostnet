import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Named
@ApplicationScoped
public class PersonDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostnetPU");

    public void save(Person person){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(person);
        tx.commit();
        em.close();
    }

    public Person findByNameAndPhone(String name, String surname, String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query;

        if (phoneNumber == null) {
            query = em.createQuery(
                    "SELECT p FROM Person p WHERE p.name = :name AND p.surname = :surname AND p.phoneNumber IS NULL",
                    Person.class
            );
        } else {
            query = em.createQuery(
                    "SELECT p FROM Person p WHERE p.name = :name AND p.surname = :surname AND p.phoneNumber = :phoneNumber",
                    Person.class
            );
            query.setParameter("phoneNumber", phoneNumber);
        }

        query.setParameter("name", name);
        query.setParameter("surname", surname);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Person findByNameAndSurname(String name, String surname) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery(
                "SELECT p FROM Person p WHERE p.name = :name AND p.surname = :surname", Person.class
        );
        query.setParameter("name", name);
        query.setParameter("surname", surname);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}