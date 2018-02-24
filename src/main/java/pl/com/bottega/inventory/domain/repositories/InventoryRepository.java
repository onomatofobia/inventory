package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Inventory;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface InventoryRepository {

    Inventory get(String skuCode);
    void save(Inventory inventory);
    Long find(String skuCode);
    void update(Inventory inventory);
    public Map<String, Inventory> getAll(Set<String> skus);
    public void updateInventory(Inventory inventory, Integer amount);
}
