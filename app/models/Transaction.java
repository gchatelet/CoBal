package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Transaction extends Model {
    @Required
    public Date date;
    @Min(value = 0)
    public BigDecimal cost;
    @Required
    @Min(value = 0)
    public int units;

    public Transaction(BigDecimal cost, int units) {
        this.date = new Date();
        this.cost = checkCost(cost);
        this.units = checkUnits(units);
    }

    private int checkUnits(int unitCount) {
        if (unitCount < 0)
            throw new IllegalArgumentException("units must be >= 0");
        return unitCount;
    }

    private BigDecimal checkCost(BigDecimal value) {
        if (value != null && BigDecimal.ZERO.compareTo(value) > 0)
            throw new IllegalArgumentException("cost must be >= 0");
        return value;
    }
}
