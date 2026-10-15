
package CanteenBooking.com.menu.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daily_menu_id", nullable = false)
    private DailyMenu dailyMenu;

    @Column(nullable = false, length = 100)
    private String name;


    @ElementCollection
    @CollectionTable(
            name = "menu_item_side_dishes",
            joinColumns = @JoinColumn(name = "menu_item_id")
    )
    @Column(name = "side_dish", nullable = false, length = 100)
    private List<String> sideDishes = new ArrayList<>();


    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", nullable = false, length = 20)
    private MenuType menuType;

    @Column(nullable = false)
    private boolean available = true;



    public MenuItem() {
    }

    public Long getId() {
        return id;
    }

    public DailyMenu getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(DailyMenu dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getSideDishes() {
        return sideDishes;
    }

    public void setSideDishes(List<String> sideDishes) {
        this.sideDishes = sideDishes == null
                ? new ArrayList<>()
                : new ArrayList<>(sideDishes);
    }


}
