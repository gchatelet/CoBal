package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;

@Entity
public class Donation extends Transaction {
    @Required
    public String name;
    @Required
    @ManyToOne
    public Community community;
    @Required
    @ManyToOne
    public User donator;
    @Required
    public Currency currency;
    @OneToMany(mappedBy = "purpose", cascade = CascadeType.ALL)
    public List<Debt> borrowings;

    public Donation(String name, Community community, User donator, Currency currency, BigDecimal cost, int units) {
        super(cost, units);
        if (donator == null || community == null || name == null || currency == null)
            throw new IllegalArgumentException("no null references allowed");
        if (name.isEmpty())
            throw new IllegalArgumentException("donation name cannot be empty");
        this.name = name;
        this.community = community;
        this.donator = donator;
        this.currency = currency;
        this.borrowings = new ArrayList<Debt>();
    }

    public boolean isCountable() {
        return units > 0;
    }

    public boolean isFree() {
        return cost == null || BigDecimal.ZERO.equals(cost);
    }

    public int getTakenUnit() {
        int units = 0;
        for (Debt borrowing : borrowings)
            units += borrowing.units;
        return units;
    }

    public int getUnitLeft() {
        return units - getTakenUnit();
    }

    public BigDecimal getMoneyLeft() {
        final BigDecimal money = cost;
        for (Debt borrowing : borrowings)
            money.min(borrowing.cost);
        return money;
    }

    public BigDecimal getPartCost() {
        if (isFree())
            return BigDecimal.ZERO;
        if (units != 0)
            return cost.divide(BigDecimal.valueOf(units), CobalMathContext.MC);
        final int takenUnit = getTakenUnit();
        if (takenUnit == 0)
            return cost;
        return cost.divide(BigDecimal.valueOf(takenUnit), CobalMathContext.MC);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " : " + name;
    }
}
