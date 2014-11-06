package pl.gda.pg.eti.kask.javaee.enterprise;

import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * Created by adam on 15.10.14.
 */
@Stateless
@LocalBean
@Log
public class TowerService {

  
    @Resource
    SessionContext sctx;

    @PersistenceContext
    EntityManager em;

    @RolesAllowed({"Admin", "User"})
    public List<Tower> findAllTowers() {
        return em.createNamedQuery("Tower.findAll").getResultList();
    }

    @RolesAllowed({"Admin", "User"})
    public List<Sorcerer> findAllWizzards() {
        return em.createNamedQuery("Sorcerer.findAll").getResultList();
    }

    @RolesAllowed({"Admin", "User"})
    public Tower findTower(int id) {
        return em.find(Tower.class, id);
    }

    @RolesAllowed({"Admin", "User"})
    public Sorcerer findWizzard(int id) {
        return em.find(Sorcerer.class, id);
    }

    @RolesAllowed({"Admin", "User"})
    public void removeTower(Tower tower) {
        em.remove(em.merge(tower));
    }

    @RolesAllowed({"Admin", "User"})
    public void removeWizzard(Sorcerer wizzard) {
        em.remove(em.merge(wizzard));
    }

    @RolesAllowed({"Admin"})
    public void trainWizzards(int amount) {
        em.createNamedQuery("Sorcerer.training").setParameter("amount", amount).executeUpdate();
    }

    @RolesAllowed({"Admin", "User"})
    public void saveTower(Tower tower) {
        if (tower.getId() == null) {
            em.persist(tower);
        } else {
            em.merge(tower);
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void saveWizzard(Sorcerer wizzard) {
        if (wizzard.getId() == null) {
            em.persist(wizzard);
        } else {
            em.merge(wizzard);
        }
    }
    
}
