package com.example.Fantazoo_API.Controllers;

import com.example.Fantazoo_API.Models.Animal;
import com.example.Fantazoo_API.Models.Zookeeper;
import com.example.Fantazoo_API.Repositories.AnimRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ac")
public class AnimController {
    private final AnimRepository ar;

    AnimController(AnimRepository animRepository) {
        this.ar = animRepository;
    }

    @GetMapping()
    List<Animal> getAllAnimals()
    {
        return ar.findAll();
    }

    @GetMapping("/id/{id}")
    Animal getAnimalById(@PathVariable int id)
    {
        return ar.findById(id).get();
    }

    @PostMapping()
    void insertAnimal(@RequestBody Animal animal)
    {
        ar.save(animal);
    }

    @PutMapping("/id/{id}")
    Animal updateAnimal(@RequestBody Animal updatedanimal, @PathVariable int id) {
        return ar.findById(id)
          .map(animal -> {
            animal.setName(updatedanimal.getName());
            animal.setAge(updatedanimal.getAge());
            animal.setGender(updatedanimal.getGender());
            animal.setCage(updatedanimal.getCage());
            return ar.save(animal);
          })
          .orElseGet(() -> {
            updatedanimal.setId(id);
            return ar.save(updatedanimal);
          });
    }

    @DeleteMapping("/id/{id}")
    void deleteAnimal(@PathVariable int id)
    {
        ar.deleteById(id);
    }
}
