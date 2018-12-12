package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entites.Combat;
import cgg.informatique.jfl.webSocket.entites.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CombatDao extends JpaRepository<Combat, Integer> {

    @Query("SELECT C FROM Combat C where C.arbitre = :arbitre")
    public List<Combat> getMyCombats(@Param("arbitre") Compte arbitre);

}
