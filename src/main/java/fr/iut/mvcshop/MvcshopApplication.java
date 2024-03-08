package fr.iut.mvcshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.iut.mvcshop.model.Categorie;
import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.CategorieRepository;
import fr.iut.mvcshop.repository.ProduitRepository;

@SpringBootApplication
public class MvcshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcshopApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase( ProduitRepository repoProd, CategorieRepository repoCat) {
		return args -> {
			// repoProd.save(new Produit("Camembert au lait cru", 0.65, 120));
			// repoProd.save(new Produit("Gruyere", 14.00, 120));
			// repoProd.save(new Produit("Vache entiere", 450.00, 45));
			// repoProd.save(new Produit("Cantal jeune", 10.55, 127));
			// repoProd.save(new Produit("Cantal vieux", 19.90, 49));
			// repoProd.save(new Produit("Beaufort", 45, 0));
			// repoProd.save(new Produit("Cabecou", 1.55, 450));

			// repoProd.findAll().forEach(System.out::println );
			Categorie categorie1 = new Categorie("Fromages frais");
			Categorie categorie2 = new Categorie("Fromages à pâte molle");
			Categorie categorie3 = new Categorie("Fromages à pâte pressée");

			  repoCat.save(categorie1);
			  repoCat.save(categorie2);
			  repoCat.save(categorie3);
			//  repoCat.save(new Categorie("Fromages à pâte persillée"));
			//  repoCat.findAll().forEach(System.out::println );
			
			 repoProd.save(new Produit("La vache qui rit", 19.90, 49, categorie1));
			 repoProd.save(new Produit("Emmental", 24.05, 49, categorie2));
			 repoProd.save(new Produit("Cheddar", 1.55, 450, categorie3));
			System.out.println("START!!!!");
		};
	}
	
}
