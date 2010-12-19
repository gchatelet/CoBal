package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;

import play.data.validation.Required;

@Entity
public class Payback extends Transaction {
    @ManyToOne
    @Required
    public Borrowing onBorrowing;
    @ManyToOne
    @Required
    public User creditor;

    public Payback(User creditor, Borrowing onBorrowing, BigDecimal price, int count) {
        super(price, count);
        if (creditor == null || onBorrowing == null)
            throw new IllegalArgumentException("creditor and borrowing must not be null");
        this.onBorrowing = onBorrowing;
        this.creditor = creditor;
    }
}
