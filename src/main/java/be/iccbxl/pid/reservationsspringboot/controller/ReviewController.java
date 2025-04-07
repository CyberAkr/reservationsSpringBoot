package be.iccbxl.pid.reservationsspringboot.controller;

import be.iccbxl.pid.reservationsspringboot.model.Review;
import be.iccbxl.pid.reservationsspringboot.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService service;
    
    @GetMapping("")
    public String index(Model model) {
        List<Review> reviews = service.getAll();
        
        model.addAttribute("reviews", reviews);
        model.addAttribute("title", "Liste des avis");
        
        return "review/index";
    }
    
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        Optional<Review> optReview = service.get(id);
        
        if(optReview.isEmpty()) {
            return "error/404";
        }
        
        model.addAttribute("review", optReview.get());
        model.addAttribute("title", "Fiche d'un avis");
        
        return "review/show";
    }
    
    // Supprimons temporairement la méthode problématique
    /*
    @GetMapping("/show/{showId}")
    public String showReviews(Model model, @PathVariable("showId") long showId) {
        // ...
    }
    */
}