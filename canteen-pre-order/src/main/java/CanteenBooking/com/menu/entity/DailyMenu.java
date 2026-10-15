
package CanteenBooking.com.menu.entity;

import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.session.entity.CanteenSession;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_menus")
public class DailyMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "canteen_id", nullable = false)
    private Canteen canteen;

    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    // Null for FULL_DAY menus; required for SESSION_BASED menus.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private CanteenSession session;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "dailyMenu",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MenuItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public DailyMenu() {
    }

    public Long getId() {
        return id;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    public CanteenSession getSession() {
        return session;
    }

    public void setSession(CanteenSession session) {
        this.session = session;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items.clear();

        if (items != null) {
            for (MenuItem item : items) {
                addItem(item);
            }
        }
    }

    public void addItem(MenuItem item) {
        items.add(item);
        item.setDailyMenu(this);
    }
}
