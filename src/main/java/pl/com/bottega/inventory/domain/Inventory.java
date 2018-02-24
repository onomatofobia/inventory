package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.InflateCommand;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

    @Id
    @Column
    private String skuCode;

    @Column
    private Integer amount;

    public Inventory(InflateCommand command) {
        skuCode = command.getSkuCode();
        amount = command.getAmount();
    }

    public Inventory(){}


    public Integer getAmount() {
        return amount;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void updateAmount(Integer amount) {
        this.amount += amount;
    }

    public boolean canPurchase(Integer purchasedAmount){
        return this.amount >= purchasedAmount;
    }

    public void substractFromAmount(Integer amount) {
        this.amount -= amount;
    }
}
