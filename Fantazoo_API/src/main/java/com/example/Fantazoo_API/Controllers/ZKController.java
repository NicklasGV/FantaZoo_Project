package com.example.Fantazoo_API.Controllers;

import com.example.Fantazoo_API.Models.Zookeeper;
import com.example.Fantazoo_API.Repositories.ZKRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/zkc")
public class ZKController {
    private final ZKRepository zkr;

    ZKController(ZKRepository zkr) {
        this.zkr = zkr;
    }

    @GetMapping()
    List<Zookeeper> getAllZookeepers()
    {
       return zkr.findAll();
    }

    @GetMapping("/id/{id}")
    Zookeeper getZookeeperById(@PathVariable int id)
    {
        return zkr.findById(id).get();
    }

    @PostMapping()
    void insertZookeeper(@RequestBody Zookeeper zookeeper)
    {
        zkr.save(zookeeper);
    }

    @PutMapping("/id/{id}")
    Zookeeper updateZookeeper(@RequestBody Zookeeper updatedzookeeper, @PathVariable int id) {
        return zkr.findById(id)
          .map(zookeeper -> {
            zookeeper.setName(updatedzookeeper.getName());
            zookeeper.setCage(updatedzookeeper.getCage());
            zookeeper.setWeapon(updatedzookeeper.getWeapon());
            return zkr.save(zookeeper);
          })
          .orElseGet(() -> {
            updatedzookeeper.setId(id);
            return zkr.save(updatedzookeeper);
          });
    }

    @DeleteMapping("/id/{id}")
    void deleteZookeeper(@PathVariable int id)
    {
        zkr.deleteById(id);
    }
}
