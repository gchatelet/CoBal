import models.Community;
import models.Currency;
import models.Donation;
import models.User;

import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;

public abstract class AbstractUnitTest extends UnitTest {
    @Before
    public void setup() {
        Fixtures.deleteAll();
        Fixtures.load("data.yml");
    }

    protected static User getBob() {
        return User.find("byName", "bob").first();
    }

    protected static Community getRnD() {
        return Community.find("byName", "R&D").first();
    }

    protected static Currency getEuro() {
        return Currency.findCurrency("€");
    }

    protected static Donation getCocaDonation() {
        return Donation.find("byName", "coca cola").<Donation> first();
    }
}
