package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.InflateCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import javax.persistence.NoResultException;

@Component
public class InflateHandler implements Handler<InflateCommand, Void> {

    private InventoryRepository inventoryRepository;

    public InflateHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public Void handle(InflateCommand command) {

        Inventory inventory;
        try {
            inventory = inventoryRepository.get(command.getSkuCode());
            inventory.updateAmount(command.getAmount());
        } catch (NoResultException e) {
            inventory = new Inventory(command);
        }
        inventoryRepository.save(inventory);
        return null;
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return InflateCommand.class;
    }
}