package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;

@Entity
public class Reimbursement extends Transaction {
    @ManyToOne
    @Required
    public Debt onBorrowing;
    @ManyToOne
    @Required
    public User creditor;

    public Reimbursement(User creditor, Debt onBorrowing, BigDecimal price, int count) {
        super(price, count);
        if (creditor == null || onBorrowing == null)
            throw new IllegalArgumentException("creditor and borrowing must not be null");
        this.onBorrowing = onBorrowing;
        this.creditor = creditor;
    }
}
