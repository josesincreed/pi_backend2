package com.example.pib2.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // items del carrito
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private boolean checkedOut = false;

    public Cart() {}

    public Long getId() { return id; }

    public List<CartItem> getItems() { return items; }

    public boolean isCheckedOut() { return checkedOut; }
    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }

    // util para agregar item (si existe, sumar cantidad)
    public void addItem(Product product, int quantity) {
        for (CartItem it : items) {
            if (it.getProduct().getId().equals(product.getId())) {
                it.setQuantity(it.getQuantity() + quantity);
                return;
            }
        }
        CartItem newItem = new CartItem(this, product, quantity);
        items.add(newItem);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
