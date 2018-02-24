package pl.com.bottega.inventory.domain.commands;

public class InflateCommand implements Validatable {

    private String skuCode;
    private  Integer amount;

    @Override
    public void validate(ValidationErrors errors) {

        validatePresenceOf(skuCode, "skuCode", errors);

        validatePresenceOf(amount, "amount", errors);

        if(amount != null && (amount < 1 || amount > 999)){
            errors.add("amount", "must be between 1 and 999");
        }
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
