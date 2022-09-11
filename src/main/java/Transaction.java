import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

//TODO Convert to DTO
public class Transaction {
    private String description;
    private enum direction {
        CREDIT,
        DEBIT
    }
    private direction dir;
    private BigDecimal amount;
    private Currency cur;
    public String getDescription(){
        return this.description;
    }

    public void setDescription(String givenDescription){
        this.description = givenDescription;
    }

    public direction getDirection() {
        return this.dir;
    }

    public void setDirection(String givenDirection){
        if (givenDirection.equals("Credit")) {
            this.dir = direction.CREDIT;
        } else if (givenDirection.equals("Debit")) {
            this.dir = direction.DEBIT;
        }
    }

    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(BigDecimal givenAmount){
        this.amount = givenAmount;
    }

    public String getCurrency(){
        return this.cur.getCurrencyCode();
    }

    public void setCurrency(String givenCurrency){
        this.cur = Currency.getInstance(givenCurrency);
    }

    @Override
    public String toString() {
        String result = "";
        result = "Description: " + this.getDescription() + "\n   " +
                "Direction: " + this.getDirection() + "\n   " +
                "Amount: " + this.getAmount() + "\n   " +
                "Currency: " + this.getCurrency();
        return result;
    }

}
