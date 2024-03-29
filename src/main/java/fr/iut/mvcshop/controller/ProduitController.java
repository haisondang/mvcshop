package fr.iut.mvcshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.CategorieRepository;
import fr.iut.mvcshop.repository.ProduitRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ProduitController {
    
    private ProduitRepository repoProd;
    private CategorieRepository repoCat;

    public ProduitController( ProduitRepository repo, CategorieRepository repo1){
        this.repoProd=repo;
        this.repoCat=repo1;
    }
    @GetMapping("/produits")
    public String listerProduits( Model model, 
                                    @RequestParam(value="mc", defaultValue = "") String mots,
                                    @RequestParam(value="p", defaultValue = "0") int page, 
                                    @RequestParam(value="s", defaultValue = "5") int size,
                                    @RequestParam(value="act", defaultValue = "") String action, 
                                    @RequestParam(value="id", defaultValue = "0") Long id){
        
        

        Pageable pageable = PageRequest.of(page, size);

        Page<Produit> produitsPage;

        if (mots.length()>0){
            produitsPage = this.repoProd.rechercher( "%"+mots+"%", pageable );
        }else{
            produitsPage = this.repoProd.findAll( pageable );
        }
        // List<Produit> listeProduits = this.repoProd.findAll(  );
        
        if(id > 0 && (action.equals("new") || action.equals("mod")) ){
            this.repoProd.findById(id).ifPresent(p -> model.addAttribute("produit", p));
        }
        model.addAttribute("produits", produitsPage.getContent());
        model.addAttribute("infoPage", produitsPage);
        model.addAttribute("mots", mots);
        model.addAttribute("action", action);
        return "produits";


    }

    @GetMapping("/produitDelete")
    public String supprimerProduits(String mc,
                                    int p, 
                                    int s, 
                                    Long id,
                                    RedirectAttributes redirectAttributes){

        this.repoProd.deleteById(id);
    
        redirectAttributes.addAttribute("mc", mc);
        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("act", "del");
        return "redirect:/produits";
    }

    @GetMapping("/produitEdit")
    public String modifierProduits( Model model, 
                                    String mc,
                                    int p, 
                                    int s, 
                                    @RequestParam(defaultValue = "0") Long id){

        model.addAttribute("categories", repoCat.findAll());
        if(id > 0){
    
            Optional<Produit> otpP = this.repoProd.findById(id);
            if ( otpP.isPresent() ){
                model.addAttribute("produit", otpP.get());

            }else{
                return "redirect:/produits";
            }

        }else{
            model.addAttribute("produit", new Produit() );
        }
        
        model.addAttribute("mc", mc);
        model.addAttribute("p", p);
        model.addAttribute("s", s);
        
        return "produitEdit";
    }


    @PostMapping("/produitSave")
    public String sauveProduits( 
                                    String mc,
                                    int p, 
                                    int s, 
                                    @Valid Produit produit, 
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("mc", mc);
            model.addAttribute("p", p);
            model.addAttribute("s", s);
            return "produitEdit";
        }
        if (produit.getId()==null){
            redirectAttributes.addAttribute("act", "new");
        }else{
            redirectAttributes.addAttribute("act", "mod");
        }
        this.repoProd.save(produit);
        redirectAttributes.addAttribute("mc", mc);
        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("id", produit.getId());
        return "redirect:/produits";
    }
}
