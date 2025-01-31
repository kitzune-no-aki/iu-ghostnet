import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.util.List;

@Named
@ApplicationScoped
public class GhostNetDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostnetPU");

    public void save(GhostNet ghostNet){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(ghostNet);
        tx.commit();
        em.close();
    }

    public List<GhostNet> getAllNets() {
        EntityManager em = emf.createEntityManager();
        List<GhostNet> nets = em.createQuery("SELECT g FROM GhostNet g", GhostNet.class).getResultList();
        em.close();
        return nets;
    }

    public GhostNet findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(GhostNet.class, id);
        } finally {
            em.close();
        }
    }

    public void update(GhostNet ghostNet) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(ghostNet);
            tx.commit();
        } finally {
            em.close();
        }
    }
}