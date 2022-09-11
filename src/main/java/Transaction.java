import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class Transaction {
    String description;
    enum direction {
        CREDIT,
        DEBIT
    }
    direction dir = direction.CREDIT;
    BigDecimal amount;
    Currency cur = Currency.getInstance("USD");

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String givenDescription){
        this.description = givenDescription;
    }

    public direction getDirection() {
        return this.dir;
    }

    public void setDirection(String givendirection){
        if (givendirection == "Credit") {
            dir = direction.CREDIT;
        } else if (givendirection == "Debit") {
            dir = direction.DEBIT;
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
    // Converts the list of transactions into a String.
    // the purpose of this method is for testing the parse method in the Main class.
    public String toString() {
        String result = "";
        result = "Description: " + this.getDescription() + "\n   " +
                "Direction: " + this.getDirection() + "\n   " +
                "Amount: " + this.getAmount() + "\n   " +
                "Currency: " + this.getCurrency();
        return result;

    }

}
