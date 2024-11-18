package helha.trocappbackend.models;

public class Category {
    private int id_category;
    private String name;
    //private List<Item> items;

    public Category() {
    }

    public Category(int id, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id_category = id_category;
    }

    public void setName(String name) {
        this.name = name;
    }

}
