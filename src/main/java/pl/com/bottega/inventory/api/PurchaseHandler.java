package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import java.util.Map;

@Component
public class PurchaseHandler implements Handler<PurchaseCommand, PurchaseDto>{

    private InventoryRepository inventoryRepository;
    private PurchaseDto purchaseDto = new PurchaseDto();
    private Validatable.ValidationErrors validationErrors;

    public PurchaseHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public PurchaseDto handle(PurchaseCommand command) {

        Map<String, Inventory> inventories = inventoryRepository.getAll(command.getPurchasedProducts().keySet());
        validateInvetories(inventories, command);

        PurchaseDto purchaseDto = new PurchaseDto();
        for (Map.Entry<String, Integer> entry : command.getPurchasedProducts().entrySet()) {
            String skuCode = entry.getKey();
            Integer amount = entry.getValue();
            managePurchases(purchaseDto, skuCode, amount, inventories);
        }
        if (purchaseDto.getMissingProducts().size() > 0) {
            purchaseDto.getPurchasedProducts().clear();
            return purchaseDto;
        }

        for (Map.Entry<String, Integer> entry : purchaseDto.getPurchasedProducts().entrySet()) {
            String skuCode = entry.getKey();
            Integer amount = entry.getValue();
            Inventory inventory = inventories.get(skuCode);
            updateInventoryAmount(inventory, amount);
        }

            purchaseDto.setSuccess(true);
            return purchaseDto;
    }


    private void validateInvetories(Map<String, Inventory> inventories, PurchaseCommand command) {
        Validatable.ValidationErrors validationErrors = new Validatable.ValidationErrors();
        for(Map.Entry<String, Integer> entry : command.getPurchasedProducts().entrySet()){
            String skuCode = entry.getKey();
            if (!inventories.containsKey(skuCode)){
                validationErrors.add(skuCode, "no such sku");
                }
            }
        if (!validationErrors.isValid()) {
            throw new InvalidCommandException(validationErrors);
        }
    }
    private void managePurchases(PurchaseDto purchaseDto, String skuCode, Integer purchasedAmount,
                                         Map<String, Inventory> inventories) {
        Inventory inventory = inventories.get(skuCode);
        if (inventory.canPurchase(purchasedAmount)) {
            purchaseDto.getPurchasedProducts().put(skuCode, purchasedAmount);
        } else {
            purchaseDto.getMissingProducts().put(skuCode, purchasedAmount);
        }
    }


    public void updateInventoryAmount(Inventory inventory, Integer amount) {
        inventory.substractFromAmount(amount);
        inventoryRepository.save(inventory);
    }



    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return PurchaseCommand.class;
    }
}