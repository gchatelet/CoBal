import java.math.BigDecimal;

import models.Donation;
import models.User;

import org.junit.Test;

public class DonationTest extends AbstractUnitTest {
    @Test
    public void donateGoodItem() {
        final Donation donation = new Donation("coca cola", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 1);
        assertNotNull(donation.name);
        assertNotNull(donation.borrowings);
        assertNotNull(donation.community);
        assertNotNull(donation.donator);
        assertNotNull(donation.currency);
        assertNotNull(donation.cost);
        assertNotNull(donation.units);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateEmptyItem() {
        new Donation("", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateNulltem() {
        new Donation(null, getRnD(), getBob(), getEuro(), BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateBadCommunity() {
        new Donation("coca cola", null, getBob(), getEuro(), BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateBadUser() {
        new Donation("coca cola", getRnD(), null, getEuro(), BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateBadCurrency() {
        new Donation("coca cola", getRnD(), getBob(), null, BigDecimal.ONE, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateNegativePrice() {
        new Donation("coca cola", getRnD(), getBob(), getEuro(), new BigDecimal(-5), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void donateNegativeCount() {
        new Donation("coca cola", getRnD(), getBob(), getEuro(), BigDecimal.ONE, -1);
    }

    @Test
    public void donateForFreeIsOk() {
        new Donation("coca cola", getRnD(), getBob(), getEuro(), BigDecimal.ZERO, 1);
    }

    @Test
    public void donateUncountableIsOk() {
        new Donation("coca cola", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 0);
    }

    @Test
    public void donateViaUser() {
        final User bob = getBob();
        final Donation donation = bob.donate("coca cola", getRnD(), getEuro(), BigDecimal.ONE, 0);
        assertNotNull(donation);
        assertEquals("coca cola", donation.name);
        assertEquals(bob, donation.donator);
        assertEquals(getRnD(), donation.community);
        assertEquals(getEuro(), donation.currency);
        assertEquals(BigDecimal.ONE, donation.cost);
        assertEquals(0, donation.units);
    }
}
