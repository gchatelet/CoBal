import models.Donation;
import models.Item;
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

    protected static Item getCoca() {
        return Item.find("byName", "coca").first();
    }

    protected static User getBob() {
        return User.find("byName", "bob").first();
    }

    protected static Donation getDonation() {
        return Donation.<Donation> findAll().get(0);
    }
}
