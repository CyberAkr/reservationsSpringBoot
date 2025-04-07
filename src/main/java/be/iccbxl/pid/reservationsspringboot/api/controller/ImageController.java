package be.iccbxl.pid.reservationsspringboot.api.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Resource resource = new ClassPathResource("static/images/" + filename);
            
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            }
        } catch (Exception e) {
            // Log l'erreur si nécessaire
        }
        
        return ResponseEntity.notFound().build();
    }
}