package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import play.data.validation.Min;
import play.data.validation.Range;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Transaction extends Model {
    @Required
    public Date date;
    @Required
    @Min(value = 0)
    public BigDecimal price;
    @Required
    @Min(value = 0)
    public int unitCount;

    public Transaction(BigDecimal price, int unitCount) {
        this.date = new Date();
        if (!validPrice(price) || unitCount < 0)
            throw new IllegalArgumentException("Invalid transaction with price : " + price + ", and item count : " + unitCount);
        this.price = price;
        this.unitCount = unitCount;
    }

    public static boolean validPrice(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) <= 0;
    }
}
