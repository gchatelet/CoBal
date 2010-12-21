package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;

@Entity
public class Debt extends Transaction {
    @ManyToOne
    @Required
    public Donation purpose;
    @ManyToOne
    @Required
    public User debtor;

    public Debt(User debtor, Donation purpose, BigDecimal borrowing) {
        this(debtor, purpose, borrowing, 0);
        final String currency = purpose.currency.toString();
        if (purpose.isCountable())
            throw new IllegalArgumentException("unable to borrow " + borrowing + ' ' + currency + " on " + purpose.name
                    + " : it is a countable donation");
        final BigDecimal moneyLeft = purpose.getMoneyLeft();
        if (moneyLeft.compareTo(borrowing) < 0)
            throw new IllegalStateException("unable to borrow " + borrowing + ' ' + currency + " on " + purpose.name + " only " + moneyLeft + ' '
                    + currency + " left");
    }

    public Debt(User debtor, Donation purpose, int units) {
        this(debtor, purpose, BigDecimal.ZERO, units);
        if (purpose.isCountable() && units == 0)
            throw new IllegalArgumentException(purpose.name + " is countable so you can't take 0 unit of it");
        final int unitLeft = purpose.getUnitLeft();
        if (units > unitLeft)
            throw new IllegalStateException("asking for " + units + " units, but " + purpose.name + " has " + unitLeft + " units left");
    }

    private Debt(User debtor, Donation purpose, BigDecimal cost, int units) {
        super(cost, units);
        if (debtor == null || purpose == null)
            throw new IllegalArgumentException("debtor and purpose must not be null");
        this.purpose = purpose;
        this.debtor = debtor;
        create();
    }

    public User getDonator() {
        return purpose.donator;
    }
}
