package fr.iut.mvcshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.iut.mvcshop.model.Categorie;
import fr.iut.mvcshop.repository.CategorieRepository;
import jakarta.validation.Valid;

@Controller
public class CategorieController {
    private CategorieRepository repoCat;

    public CategorieController( CategorieRepository repo){
        this.repoCat=repo;
    }
    @GetMapping("/categories")
    public String listerCategories( Model model, 
                                    @RequestParam(value="mc", defaultValue = "") String mots,
                                    @RequestParam(value="p", defaultValue = "0") int page, 
                                    @RequestParam(value="s", defaultValue = "5") int size,
                                    @RequestParam(value="act", defaultValue = "") String action, 
                                    @RequestParam(value="id", defaultValue = "0") Long id){
        
        

        Pageable pageable = PageRequest.of(page, size);

        Page<Categorie> categoriesPage;

        
        if (mots.length()>0){
            categoriesPage = this.repoCat.rechercher( "%"+mots+"%", pageable );
        }else{
            categoriesPage = this.repoCat.findAll( pageable );
        }
        
        // List<categorie> listecategories = this.repoCat.findAll(  );

        

        if(id > 0 && (action.equals("new") || action.equals("mod")) ){
            this.repoCat.findById(id).ifPresent(p -> model.addAttribute("categorie", p));
        }
        model.addAttribute("categories", categoriesPage.getContent());
        model.addAttribute("infoPage", categoriesPage);
        model.addAttribute("mots", mots);
        model.addAttribute("action", action);
        return "categories";
    }

    @GetMapping("/categorieDelete")
    public String supprimerCategories(String mc,
                                    int p, 
                                    int s, 
                                    Long id,
                                    RedirectAttributes redirectAttributes){

        this.repoCat.deleteById(id);
    
        redirectAttributes.addAttribute("mc", mc);
        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("act", "del");
        return "redirect:/categories";
    }

    @GetMapping("/categorieEdit")
    public String modifierCategories( Model model, 
                                    String mc,
                                    int p, 
                                    int s, 
                                    @RequestParam(defaultValue = "0") Long id){

        if(id > 0){
    
            Optional<Categorie> otpP = this.repoCat.findById(id);
            if ( otpP.isPresent() ){
                model.addAttribute("categorie", otpP.get());
            }else{
                return "redirect:/categories";
            }

        }else{
            model.addAttribute("categorie", new Categorie() );
        }
    
        model.addAttribute("mc", mc);
        model.addAttribute("p", p);
        model.addAttribute("s", s);
        
        return "categorieEdit";
    }


    @PostMapping("/categorieSave")
    public String sauveCategories( 
                                    String mc,
                                    int p, 
                                    int s, 
                                    @Valid Categorie categorie, 
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("mc", mc);
            model.addAttribute("p", p);
            model.addAttribute("s", s);
            return "categorieEdit";
        }
        if (categorie.getId()==null){
            redirectAttributes.addAttribute("act", "new");
        }else{
            redirectAttributes.addAttribute("act", "mod");
        }
        this.repoCat.save(categorie);
        redirectAttributes.addAttribute("mc", mc);
        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("id", categorie.getId());
        return "redirect:/categories";
    }
}
