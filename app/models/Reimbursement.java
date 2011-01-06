package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;

@Entity
public class Reimbursement extends Transaction {
    @ManyToOne
    public Donation purpose;
    @ManyToOne
    @Required
    public User creditor;
    @ManyToOne
    @Required
    public User debtor;
    @Required
    @ManyToOne
    public Community withinCommunity;

    public Reimbursement(Community community, User creditor, User debtor, Donation purpose, int count) {
        this(community, creditor, debtor, purpose, null, count);
    }

    public Reimbursement(Community community, User creditor, User debtor, Donation purpose, BigDecimal price) {
        this(community, creditor, debtor, purpose, price, 0);
        if (price == null)
            throw new IllegalArgumentException("cannot transfert a null amount");
    }

    private Reimbursement(Community community, User creditor, User debtor, Donation purpose, BigDecimal price, int count) {
        super(price, count);
        if (debtor == null || creditor == null || community == null)
            throw new IllegalArgumentException("creditor, debtor and community must not be null");
        if (debtor.equals(creditor))
            throw new IllegalArgumentException("cannot transfer to self");
        if (!debtor.communities.contains(community))
            throw new IllegalStateException(debtor + " must be part of " + community);
        if (!creditor.communities.contains(community))
            throw new IllegalStateException(creditor + " must be part of " + community);
        this.purpose = purpose;
        this.creditor = creditor;
        this.debtor = debtor;
        this.withinCommunity = community;
        this.withinCommunity.transfers.add(this);
    }
}
