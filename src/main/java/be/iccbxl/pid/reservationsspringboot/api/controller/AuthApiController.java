package be.iccbxl.pid.reservationsspringboot.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.reservationsspringboot.api.dto.UserDTO;
import be.iccbxl.pid.reservationsspringboot.api.dto.LoginDTO;
import be.iccbxl.pid.reservationsspringboot.service.UserService; // Importez le service
import be.iccbxl.pid.reservationsspringboot.model.User; // Importez le modèle User

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/public")
public class AuthApiController {

    private final UserService userService; // Service à créer

    // Injection de dépendance
    public AuthApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            // Convertir le DTO en entité User
            User user = new User();
            user.setLogin(userDTO.getLogin());
            user.setEmail(userDTO.getEmail());
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setPassword(userDTO.getPassword());
            // Ajoutez d'autres champs si nécessaire
            
            // Utiliser la méthode addUser qui existe déjà dans votre service
            userService.addUser(user);
            
            // Réponse de succès
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inscription réussie");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En cas d'erreur
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Échec de l'inscription: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ... reste du code

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Ici, vous devriez implémenter la logique pour authentifier l'utilisateur
        // Pour le moment, nous renvoyons un token simulé
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", 1);
        userData.put("login", loginDTO.getLogin());
        userData.put("firstname", "Utilisateur");
        userData.put("lastname", "Test");
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", "jwt-token-example");
        response.put("user", userData);
        
        return ResponseEntity.ok(response);
    }
}