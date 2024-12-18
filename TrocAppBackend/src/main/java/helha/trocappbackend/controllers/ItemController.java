package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryRepository categoryRepository;

    /*
        we find the given category and we store it in the item,
        so the returned item will have a category that already exists
     */
    @PostMapping
    public Item addItem(@RequestBody Item item) {
        Item item1 = new Item();
        item1.setName(item.getName());
        item1.setDescription(item.getDescription());
        item1.setAvailable(item.isAvailable());
        item1.setOwner(item.getOwner());
        //item1.setOwnerId(item.getOwnerId());
        item1.setPhoto(item.getPhoto());
        Integer idCategory = item.getCategory().getId_category();
        if (idCategory != null) {
            Category category = categoryRepository.findById(idCategory)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            item.setCategory(category);
            Item newItem = itemService.addItem(item);
            return ResponseEntity.ok(newItem);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);  //badRequest  entree invalide
        }
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //err cote serveur exc java
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Item>> getAllAvailableItems() {
        try {
            List<Item> items = itemService.getAllAvailableItems();
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //err cote serveur exc java
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Item>> getAllItemsByUserID(@PathVariable int id) {
        try {
            List<Item> itemsOfUserID = itemService.getAllAvailableItemsByUserId(id);
            return ResponseEntity.ok(itemsOfUserID);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();                  //no content au lieu de ok(renvoye)
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //  build  au lieu de body null ?
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        try {
            item.setId(id);
            Item itemUpdated = itemService.updateItem(item);
            return ResponseEntity.ok(itemUpdated);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);       ///// badRequest
        }
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable("id") int id) {
        return itemService.getItemById(id);
    }
}




