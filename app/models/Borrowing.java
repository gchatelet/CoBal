package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;

import play.data.validation.Required;

@Entity
public class Borrowing extends Transaction {
    @ManyToOne
    @Required
    public Donation againstDonation;
    @ManyToOne
    @Required
    public User borrower;

    public Borrowing(User borrower, Donation donation, BigDecimal price, int count) {
        super(price, count);
        if (borrower == null || donation == null)
            throw new IllegalArgumentException("borrower and donation must not be null");
        this.againstDonation = donation;
        this.borrower = borrower;
    }
}
