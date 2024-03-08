package fr.iut.mvcshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.mvcshop.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
@Query("SELECT c FROM Categorie c WHERE c.nom LIKE :mots")
    Page<Categorie> rechercher(@Param("mots") String mc, Pageable pageable);
}