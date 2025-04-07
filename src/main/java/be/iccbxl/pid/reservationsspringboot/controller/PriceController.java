package be.iccbxl.pid.reservationsspringboot.controller;

import be.iccbxl.pid.reservationsspringboot.model.Price;
import be.iccbxl.pid.reservationsspringboot.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prices")
public class PriceController {
    @Autowired
    private PriceService service;
    
    @GetMapping("")
    public String index(Model model) {
        List<Price> prices = service.getAll();
        
        model.addAttribute("prices", prices);
        model.addAttribute("title", "Liste des prix");
        
        return "price/index";
    }
    
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        Optional<Price> optPrice = service.get(id);
        
        if(optPrice.isEmpty()) {
            return "error/404";
        }
        
        model.addAttribute("price", optPrice.get());
        model.addAttribute("title", "Fiche d'un prix");
        
        return "price/show";
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("price", new Price());
        model.addAttribute("title", "Créer un nouveau prix");
        
        return "price/create";
    }
    
    @PostMapping("/create")
    public String store(@ModelAttribute("price") Price price) {
        service.add(price);
        
        return "redirect:/prices";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Optional<Price> optPrice = service.get(id);
        
        if(optPrice.isEmpty()) {
            return "error/404";
        }
        
        model.addAttribute("price", optPrice.get());
        model.addAttribute("title", "Modifier un prix");
        
        return "price/edit";
    }
    
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") long id, @ModelAttribute("price") Price price) {
        service.update(id, price);
        
        return "redirect:/prices/" + id;
    }
    
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        service.delete(id);
        
        return "redirect:/prices";
    }
}