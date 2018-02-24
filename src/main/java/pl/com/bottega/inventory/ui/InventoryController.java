package pl.com.bottega.inventory.ui;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.PurchaseDto;
import pl.com.bottega.inventory.domain.commands.InflateCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;

import java.util.Map;

@RestController
public class InventoryController {

    private CommandGateway commandGateway;

    public InventoryController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/inventory")
    public void inflateInventory(@RequestBody InflateCommand inflateCommand){

        commandGateway.execute(inflateCommand);
     }

    @PostMapping("/purchase")
    public PurchaseDto purchaseFromInventory(@RequestBody Map<String, Integer> purchaseProducts){

        PurchaseCommand purchaseCommand = new PurchaseCommand();
        purchaseCommand.setPurchasedProducts(purchaseProducts);
        return commandGateway.execute(purchaseCommand);
    }
}