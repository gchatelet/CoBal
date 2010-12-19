import java.math.BigDecimal;
import java.util.List;

import models.Borrowing;
import models.Donation;
import models.Item;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import junit.framework.TestCase;

public class BorrowingTest extends AbstractUnitTest {
    @Test
    public void checkData() {
        final Donation donation = getDonation();
        assertNotNull(donation);
        assertNotNull(donation.date);
        assertNotNull(donation.fromUser);
        assertNotNull(donation.price);
        assertEquals(6, donation.unitCount);
    }

    @Test
    public void borrowOk() {
        final Donation donation = getDonation();
        final Borrowing borrowing = donation.borrow(getBob(), 1);
        assertNotNull(borrowing);
        assertNotNull(borrowing.date);
        assertEquals(1, borrowing.unitCount);
        assertEquals(BigDecimal.ZERO, borrowing.price);
    }

    @Test(expected = IllegalArgumentException.class)
    public void borrowNoneButDonationIsCountable() {
        final Donation donation = getDonation();
        donation.borrow(getBob(), 0);
    }
}
