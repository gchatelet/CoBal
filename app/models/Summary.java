package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Summary extends CobalMathContext {
    final Map<User, BigDecimal> balanceMap = new HashMap<User, BigDecimal>();
    private final Community community;

    public Summary(Community community) {
        this.community = community;
        getSummary();
    }

    private void getSummary() {
        for (Donation donation : community.donations) {
            final BigDecimal partCost = donation.getPartCost();
            for (Debt debt : donation.borrowings)
                modifyBalanceFor(debt.debtor, partCost);
            modifyBalanceFor(donation.donator, donation.cost.negate(MC));
        }
        for (Reimbursement reimbursment : community.transfers) {
            modifyBalanceFor(reimbursment.creditor, reimbursment.cost);
            modifyBalanceFor(reimbursment.debtor, reimbursment.cost.negate(MC));
        }
    }

    private BigDecimal modifyBalanceFor(final User debtor, final BigDecimal offset) {
        BigDecimal balance = balanceMap.get(debtor);
        if (balance == null)
            balanceMap.put(debtor, offset);
        else
            balanceMap.put(debtor, balance.add(offset, MC));
        return balance;
    }

    public final Map<User, BigDecimal> getBalanceMap() {
        return balanceMap;
    }

    public final BigDecimal getBalanceFor(User user) {
        return balanceMap.get(user);
    }

    public final boolean isBalanced(User user) {
        return isBalanced(user, PRECISION);
    }

    public final boolean isBalanced(User user, BigDecimal approx) {
        final BigDecimal balanceForUser = balanceMap.get(user);
        if (balanceForUser == null)
            return true;
        if (balanceForUser.abs().compareTo(approx) > 0)
            return false;
        return true;
    }

    public final boolean isBalanced() {
        return isBalanced(PRECISION);
    }

    public final boolean isBalanced(BigDecimal approx) {
        for (BigDecimal balance : balanceMap.values())
            if (balance.abs().compareTo(approx) > 0)
                return false;
        return true;
    }

    public final List<Donation> whatsLeft() {
        final ArrayList<Donation> donations = new ArrayList<Donation>();
        for (Donation donation : community.donations)
            if (donation.isCountable() && donation.getUnitLeft() > 0)
                donations.add(donation);
        return donations;
    }
}
