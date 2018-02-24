package pl.com.bottega.inventory.domain.commands;

import java.util.HashMap;
import java.util.Map;

public class PurchaseCommand implements Validatable{

    private Map<String, Integer> purchasedProducts = new HashMap<>();

    @Override
    public void validate(ValidationErrors errors) {

        if (purchasedProducts.isEmpty()){
            errors.add("skus", "are required");
        }

        if (purchasedProducts.size() > 0){
            for(Map.Entry<String, Integer> entry : purchasedProducts.entrySet()){
                Integer amount = entry.getValue();
                String skuCode = entry.getKey();
                if (amount == null) {
                    errors.add(skuCode, "can't be blank");
                }
                if( amount != null && (amount < 1 || amount > 999)){
                    errors.add(skuCode, "must be between 1 and 999");
                }
            }
        }
    }

    public Map<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Map<String, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}