package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class JPAInventoryRepository implements InventoryRepository {

    private EntityManager entityManager;

    public JPAInventoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Inventory get(String skuCode) {
        Inventory inventory;
            inventory = (Inventory) entityManager.createQuery("SELECT i FROM Inventory i WHERE i.skuCode = :skuCode")
                    .setParameter("skuCode", skuCode)
                    .getSingleResult();
        return inventory;
    }

    @Override
    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }

    @Override
    public Long find(String skuCode) {
        Long resultCount;
            resultCount = (Long) entityManager.createQuery("SELECT COUNT(i) FROM Inventory i WHERE i.skuCode = :skuCode")
                    .setParameter("skuCode", skuCode)
                    .getSingleResult();
            return resultCount;
    }

    @Override
    public Map<String, Inventory> getAll(Set<String> skus) {
        List<Inventory> reservationList = entityManager.createQuery("SELECT i FROM Inventory i WHERE i.skuCode IN (:skuCodeList)")
                .setParameter("skuCodeList", skus)
                .getResultList();

        Map<String, Inventory> inventoryMap = new HashMap<>();
        reservationList.stream().forEach(inventory -> inventoryMap.put(inventory.getSkuCode(), inventory));
        return inventoryMap;
    }

    @Override
    public void update(Inventory inventory) {
        entityManager.merge(inventory);
    }

    @Override
    public void updateInventory(Inventory inventory, Integer amount) {

        inventory.setAmount(amount);
        entityManager.merge(inventory);
    }
}
