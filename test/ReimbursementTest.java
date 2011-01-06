import java.math.BigDecimal;

import models.Community;
import models.Reimbursement;

import org.junit.Test;

public class ReimbursementTest extends AbstractUnitTest {
    @Test
    public void goodTransfert() {
        final Community rnD = getRnD();
        final Reimbursement transfert = new Reimbursement(rnD, getBob(), getJohn(), getCocaDonation(), BigDecimal.ONE);
        assertEquals(1, rnD.transfers.size());
        assertTrue(rnD.transfers.contains(transfert));
    }

    @Test(expected = IllegalStateException.class)
    public void usersNotInCommunity() {
        new Reimbursement(getRnD(), getBob(), getAlice(), getCocaDonation(), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transfertToSelf() {
        new Reimbursement(getRnD(), getBob(), getBob(), getCocaDonation(), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCommunity() {
        new Reimbursement(null, getBob(), getJohn(), getCocaDonation(), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCreditor() {
        new Reimbursement(getRnD(), null, getJohn(), getCocaDonation(), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDebitor() {
        new Reimbursement(getRnD(), getBob(), null, getCocaDonation(), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullAmount() {
        new Reimbursement(getRnD(), getBob(), getJohn(), getCocaDonation(), null);
    }

    @Test
    public void nullPurposeIsOk() {
        new Reimbursement(getRnD(), getBob(), getJohn(), null, BigDecimal.ONE);
    }
}
