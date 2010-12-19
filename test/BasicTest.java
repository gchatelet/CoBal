import org.junit.*;

import java.math.BigDecimal;
import java.util.*;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Test
	public void addItemToCommunity() {
		Fixtures.deleteAll();
		final Community newCommunity = new Community("R&D", null);
		assertTrue(newCommunity.create());
		newCommunity.addItem("item");
		// checking the community is reachable
		Community community = Community.find("byName", "R&D").first();
		assertNotNull(community);
		assertEquals(1, community.items.size());
		// checking the item is reachable
		final Item item = Item.find("byName", "item").first();
		assertNotNull(item);
		assertNotNull(item.community);
		assertEquals(community, item.community);
	}

}
