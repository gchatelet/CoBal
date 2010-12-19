package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;

import play.data.validation.Required;

@Entity
public class Donation extends Transaction {
    @ManyToOne
    @Required
    public Item item;
    @ManyToOne
    @Required
    public User fromUser;
    @OneToMany(mappedBy = "againstDonation", cascade = CascadeType.ALL)
    public List<Borrowing> borrowings;

    public Donation(User fromUser, Item item, BigDecimal price, int unitCount) {
        super(price, unitCount);
        if (fromUser == null || item == null)
            throw new IllegalArgumentException("User and item must not be null");
        this.item = item;
        this.fromUser = fromUser;
        this.borrowings = new ArrayList<Borrowing>();
    }

    public boolean isCountable() {
        return unitCount > 0;
    }

    public boolean isFree() {
        return BigDecimal.ZERO.equals(price);
    }

    public int getAvailablePieces() {
        int borrowedPieces = 0;
        for (Borrowing borrowing : borrowings)
            borrowedPieces += borrowing.unitCount;
        return unitCount - borrowedPieces;
    }

    public Borrowing borrow(User borrower, int count) {
        final boolean countable = isCountable();
        if (countable) {
            if (count == 0)
                throw new IllegalArgumentException("Cannot borrow 0 piece on a countable donation");
            if (getAvailablePieces() < count)
                throw new IllegalStateException("Cannot borrow " + count + " pieces : not enough pieces left");
        } else {
            if (count != 0)
                throw new IllegalArgumentException("Cannot borrow " + count + " pieces : " + item + " is not countable");
        }
        final Borrowing borrowing = new Borrowing(borrower, this, BigDecimal.ZERO, count);
        borrowing.create();
        borrowings.add(borrowing);
        save();
        return borrowing;
    }
}
