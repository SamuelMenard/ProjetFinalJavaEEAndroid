package cgg.informatique.jfl.webSocket.dao;

import cgg.informatique.jfl.webSocket.entites.Combat;
import cgg.informatique.jfl.webSocket.entites.Compte;
import cgg.informatique.jfl.webSocket.entites.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamenDao extends JpaRepository<Examen, Integer> {

    @Query("SELECT E FROM Examen E where E.evalue = :user")
    public List<Examen> getMyExamensOrderByDateDESC(@Param("user") Compte user);

}
