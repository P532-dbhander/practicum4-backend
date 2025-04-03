package edu.iu.habahram.ducksservice.controllers;
import edu.iu.habahram.ducksservice.model.DuckData;
import edu.iu.habahram.ducksservice.model.Ducks;
import edu.iu.habahram.ducksservice.repository.DucksFileRepository;
import edu.iu.habahram.ducksservice.repository.DucksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ducks")
public class DuckController {
    private DucksFileRepository ducksFileRepository;
    public DuckController(DucksFileRepository ducksFileRepository) {
        this.ducksFileRepository = ducksFileRepository;
    }

   @PostMapping
    public int add(@RequestBody DuckData duck) {
       try {
           return ducksFileRepository.add(duck);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    @GetMapping
    public List<DuckData> findAll() {
        try {
            return ducksFileRepository.findAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuckData> find(@PathVariable int id) {
        try {
            DuckData duck = ducksFileRepository.find(id);
            if(duck != null) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(duck);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/image")
    public boolean updateImage(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return ducksFileRepository.updateImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/audio")
    public boolean updateAudio(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return ducksFileRepository.updateAudio(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = ducksFileRepository.getImage(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<?> getAudio(@PathVariable int id) {
        try {
            byte[] image = ducksFileRepository.getAudio(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.valueOf("audio/mp3"))
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}