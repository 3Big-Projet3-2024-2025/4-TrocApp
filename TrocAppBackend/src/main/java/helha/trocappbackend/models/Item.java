package helha.trocappbackend.models;

import jakarta.persistence.*;

public class Item {
    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private User user;
}
