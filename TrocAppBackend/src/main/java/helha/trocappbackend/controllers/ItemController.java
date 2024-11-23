package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

//    @GetMapping("/all")
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
    }

    @PutMapping
    public Item updateItem(@RequestBody Item item) {
        return itemService.updateItem(item);
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable("id") int id) {
        return itemService.getItem(id);
    }
}




