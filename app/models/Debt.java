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
        if (borrowing == null)
            throw new IllegalArgumentException("unable to borrow a null price");
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
        this(debtor, purpose, null, units);
        if (purpose.isCountable() && units == 0)
            throw new IllegalArgumentException(purpose.name + " is countable so you can't take 0 unit of it");
        if (purpose.isCountable()) {
            final int unitLeft = purpose.getUnitLeft();
            if (unitLeft < 0)
                throw new IllegalStateException("asking for " + units + " units, but " + purpose.name + " has " + unitLeft + units + " units left");
        }
    }

    private Debt(User debtor, Donation purpose, BigDecimal cost, int units) {
        super(cost, units);
        if (debtor == null || purpose == null)
            throw new IllegalArgumentException("debtor and purpose must not be null");
        this.purpose = purpose;
        this.debtor = debtor;
        purpose.borrowings.add(this);
    }

    public User getDonator() {
        return purpose.donator;
    }
}
