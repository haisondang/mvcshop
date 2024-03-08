package fr.iut.mvcshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.ProduitRepository;

@SpringBootApplication
public class MvcshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcshopApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase( ProduitRepository repo) {
		return args -> {
			// repo.save(new Produit("Camembert au lait cru", 0.65, 120));
			// repo.save(new Produit("Gruyere", 14.00, 120));
			// repo.save(new Produit("Vache entiere", 450.00, 45));
			// repo.save(new Produit("Cantal jeune", 10.55, 127));
			// repo.save(new Produit("Cantal vieux", 19.90, 49));
			// repo.save(new Produit("Beaufort", 45, 0));
			// repo.save(new Produit("Cabecou", 1.55, 450));

			// repo.findAll().forEach(System.out::println );

			System.out.println("START!!!!");
		};
	}
}
