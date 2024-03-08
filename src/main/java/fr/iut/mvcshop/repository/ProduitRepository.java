package fr.iut.mvcshop.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.iut.mvcshop.model.Produit;

public interface ProduitRepository  extends JpaRepository<Produit,Long>{

    @Query("SELECT p FROM Produit p WHERE p.designation LIKE :mots")
    Page<Produit> rechercher(@Param("mots") String mc, Pageable pageable);

}
