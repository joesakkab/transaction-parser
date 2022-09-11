import java.math.BigDecimal;

public class Transaction {
    String description;
    String direction;
    BigDecimal amount;
    String currency;

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String givenDescription){
        this.description = givenDescription;
    }

    public String getDirection(){
        return this.direction;
    }

    public void setDirection(String givendirection){
        this.direction = givendirection;
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(BigDecimal givenAmount){
        this.amount = givenAmount;
    }

    public String getCurrency(){
        return this.currency;
    }

    public void setCurrency(String givenCurrency){
        this.currency = givenCurrency;
    }

}
