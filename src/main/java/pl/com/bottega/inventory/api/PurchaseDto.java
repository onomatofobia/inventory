package pl.com.bottega.inventory.api;

import java.util.HashMap;
import java.util.Map;

public class PurchaseDto {

    private Map<String, Integer> purchasedProducts = new HashMap<>();
    private Map<String, Integer> missingProducts = new HashMap<>();
    private boolean success;



    public Map<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Map<String, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public Map<String, Integer> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Map<String, Integer> missingProducts) {
        this.missingProducts = missingProducts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}