package be.iccbxl.pid.reservationsspringboot.api.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.reservationsspringboot.api.dto.LoginDTO;
import be.iccbxl.pid.reservationsspringboot.api.dto.UserDTO;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.service.UserService;

@RestController
@RequestMapping("/api/public")
public class AuthApiController {

    private final UserService userService;

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
            user.setPassword(userDTO.getPassword()); // À remplacer par une version hashée
            user.setLangue(userDTO.getLangue());
            user.setCreatedAt(LocalDateTime.now()); // Important: définir la date de création
            
            // Utiliser la méthode addUser qui existe déjà dans votre service
            userService.addUser(user);
            
            // Réponse de succès
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inscription réussie");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Pour voir l'erreur dans les logs
            
            // Réponse d'erreur
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Échec de l'inscription: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Échec de la connexion: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}