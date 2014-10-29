package pl.adamborowski.kask.jsf;

import lombok.extern.java.Log;
import pl.adamborowski.kask.jsf.entities2.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Created by adam on 15.10.14.
 */
@Stateless
@LocalBean
@Log
public class TowerService {

    @PersistenceContext
    EntityManager em;

    private List<Tower> asList(Tower... towers) {
        return findAllTowers();
    }

    public List<Tower> findAllTowers() {
        return em.createNamedQuery("Tower.findAll").getResultList();
    }

    public List<Sorcerer> findAllWizzards() {
        return em.createNamedQuery("Sorcerer.findAll").getResultList();
    }

    public Tower findTower(int id) {
        return em.find(Tower.class, id);
    }

    public Sorcerer findWizzard(int id) {
        return em.find(Sorcerer.class, id);
    }

    public void removeTower(Tower tower) {
        em.remove(em.merge(tower));
    }

    public void removeWizzard(Sorcerer wizzard) {
        em.remove(em.merge(wizzard));
    }

    public void saveTower(Tower tower) {
        if (tower.getId() == null) {
            em.persist(tower);
        } else {
            em.merge(tower);
        }
    }

    public void saveWizzard(Sorcerer wizzard) {
        if (wizzard.getId() == null) {
            em.persist(wizzard);
        } else {
            em.merge(wizzard);
        }
    }

    public void marshalLibrary(OutputStream out) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            Marshaller m = jaxbContext.createMarshaller();
            Root library = new Root();
            library.getTowers().addAll(findAllTowers());
            m.marshal(library, out);
        } catch (JAXBException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
