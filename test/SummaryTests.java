import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import models.Community;
import models.Donation;
import models.Reimbursement;
import models.Summary;
import models.User;

import org.junit.Test;

public class SummaryTests extends AbstractUnitTest {
    @Test
    public void summaryJohnReimbursedDebt() {
        final Community rnD = getRnD();
        // 5 coca cola for 10
        final Donation donation = getCocaDonation();
        // one coke is two
        assertEquals(BigDecimal.valueOf(2), donation.getPartCost());
        // john taked two units
        assertEquals(2, donation.borrowings.get(0).units);
        // he gives bob 10 ( way too much right )
        new Reimbursement(rnD, getBob(), getJohn(), donation, BigDecimal.TEN);
        // there's one tranfert
        assertEquals(1, rnD.transfers.size());
        final Reimbursement reimbursement = rnD.transfers.get(0);
        assertEquals(BigDecimal.TEN, reimbursement.cost);
        assertEquals(getBob(), reimbursement.creditor);
        assertEquals(getJohn(), reimbursement.debtor);
        // let's see the summary
        final Summary summary = new Summary(rnD);
        final Map<User, BigDecimal> balanceMap = summary.getBalanceMap();
        // 2 users involved
        assertEquals(2, balanceMap.size());
        // bob is OK
        assertEquals(BigDecimal.valueOf(0), summary.getBalanceFor(getBob()));
        assertTrue(summary.isBalanced(getBob()));
        assertTrue(summary.isBalanced(getBob(), BigDecimal.ZERO));
        assertFalse(summary.isBalanced(getJohn()));
        assertFalse(summary.isBalanced(getJohn(), BigDecimal.ONE));
        assertTrue(summary.isBalanced(getJohn(), BigDecimal.TEN));
        // john now owes the rest of the units
        assertEquals(BigDecimal.valueOf(-8), summary.getBalanceFor(getJohn()));
        final List<Donation> whatsLeft = summary.whatsLeft();
        assertEquals(1, whatsLeft.size());
        assertEquals(3, whatsLeft.get(0).getUnitLeft());
        // is it balanced if everyone's consider leaving ten is ok
        assertTrue(summary.isBalanced(BigDecimal.TEN));
        // is it balancer considering leaving one is ok
        assertFalse(summary.isBalanced(BigDecimal.ONE));
    }
}
