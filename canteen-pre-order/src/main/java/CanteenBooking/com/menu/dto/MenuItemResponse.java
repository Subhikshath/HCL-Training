
package CanteenBooking.com.menu.dto;

import CanteenBooking.com.menu.entity.MenuType;

import java.math.BigDecimal;
import java.util.List;

public class MenuItemResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private MenuType menuType;
    private boolean available;
    private List<String> sideDishes;
    private Integer availableQuantity;

    public MenuItemResponse(
            Long id,
            String name,
            String description,
            BigDecimal price,
            MenuType menuType,
            boolean available,
            List<String> sideDishes,
            Integer availableQuantity) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.menuType = menuType;
        this.available = available;
        this.sideDishes = sideDishes;
        this.availableQuantity = availableQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<String> getSideDishes() {
        return sideDishes;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
}
