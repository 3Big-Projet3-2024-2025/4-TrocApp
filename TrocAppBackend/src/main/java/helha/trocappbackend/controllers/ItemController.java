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
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        Item item1 = new Item();
        item1.setName(item.getName());
        item1.setDescription(item.getDescription());
        item1.setAvailable(item.isAvailable());
        item1.setOwner(item.getOwner());
        //item1.setOwnerId(item.getOwnerId());
        item1.setPhoto(item.getPhoto());
        Integer idCategory = item.getCategory().getId();
        if (idCategory != null) {
            Category category = categoryRepository.findById(idCategory)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            item1.setCategory(category);
        }
        //System.out.println("voici l'id category " + idCategory+ "item:"+item+"item1"+item1);
        return itemService.addItem(item1);
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




