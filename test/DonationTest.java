import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import models.Donation;
import models.Item;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import junit.framework.TestCase;

public class DonationTest extends AbstractUnitTest {
    @Test
    public void donate() {
        final User bob = getBob();
        assertNotNull(bob);
        final Item coca = getCoca();
        assertNotNull(coca);
        final Donation lastDonation = bob.donate(coca, new BigDecimal(6.5), 6);
        assertEquals(bob, lastDonation.fromUser);
        assertNotNull(lastDonation.date);
        assertEquals(6, lastDonation.unitCount);
        assertEquals(BigDecimal.valueOf(6.5), lastDonation.price);
        assertFalse(lastDonation.isFree());
        assertTrue(lastDonation.isCountable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateNulltem() {
        getBob().donate(null, BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateNullPrice() {
        getBob().donate(getCoca(), null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateBadPrice() {
        getBob().donate(getCoca(), BigDecimal.valueOf(-1), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateBadCount() {
        getBob().donate(getCoca(), BigDecimal.valueOf(1), -1);
    }

    @Test
    public void donateZeroCountIsOk() {
        final Donation donation = getBob().donate(getCoca(), BigDecimal.valueOf(1), 0);
        assertFalse(donation.isCountable());
    }

    @Test
    public void donateFreeIsOk() {
        final Donation donation = getBob().donate(getCoca(), BigDecimal.ZERO, 1);
        assertTrue(donation.isFree());
    }
}
