import java.math.BigDecimal;

import models.Debt;
import models.Donation;

import org.junit.Test;

public class DebtTest extends AbstractUnitTest {
    @Test
    public void checkData() {
        final Donation donation = getCocaDonation();
        assertNotNull(donation);
        assertNotNull(donation.date);
        assertNotNull(donation.donator);
        assertNotNull(donation.cost);
        assertEquals(6, donation.units);
    }

    @Test
    public void goodUnitDebt() {
        new Debt(getBob(), getCocaDonation(), 1);
    }

    @Test
    public void goodBorrowingDebt() {
        final Donation donation = new Donation("toto", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 0);
        new Debt(getBob(), donation, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void debtNullPrice() {
        new Debt(getBob(), getCocaDonation(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void debtBadPrice() {
        new Debt(getBob(), getCocaDonation(), new BigDecimal(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void debtZeroUnitsOnCountable() {
        new Debt(getBob(), getCocaDonation(), 0);
    }

    @Test
    public void debtZeroOnUncountable() {
        final Donation donation = new Donation("one", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 0);
        final Debt debt = new Debt(getBob(), donation, 0);
        assertEquals(getBob(), debt.getDonator());
    }

    @Test(expected = IllegalStateException.class)
    public void overUnitDebt() {
        final Donation donation = new Donation("one", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 1);
        new Debt(getBob(), donation, 2);
    }

    @Test(expected = IllegalStateException.class)
    public void overCostDebt() {
        final Donation donation = new Donation("one", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 0);
        new Debt(getBob(), donation, BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void debtPriceOnUncountable() {
        final Donation donation = new Donation("one", getRnD(), getBob(), getEuro(), BigDecimal.ONE, 10);
        new Debt(getBob(), donation, BigDecimal.ONE);
    }
}
