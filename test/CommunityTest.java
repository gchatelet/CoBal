import models.Community;
import models.User;

import org.junit.Test;

public class CommunityTest extends AbstractUnitTest {
    @Test(expected = IllegalArgumentException.class)
    public void joinNullCommunity() {
        getBob().join(null);
    }

    @Test
    public void joinCommunity() {
        getBob().join(getRnD());
        assertEquals(1, getBob().communities.size());
        assertEquals(1, getRnD().users.size());
    }

    @Test
    public void joinCommunityTwice() {
        getBob().join(getRnD());
        getBob().join(getRnD());
        assertEquals(1, getBob().communities.size());
        assertEquals(1, getRnD().users.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndJoinNullCommunity() {
        getBob().createAndJoin(null, null);
    }

    @Test
    public void createAndJoinCommunity() {
        final User bob = getBob();
        final Community community = bob.createAndJoin("toto", "one description");
        assertNotNull(community);
        assertNotNull(community.name);
        assertNotNull(community.description);
        assertTrue(community.users.contains(bob));
        assertTrue(bob.communities.contains(community));
    }

    @Test(expected = IllegalStateException.class)
    public void createAndJoinSameCommunityTwice() {
        final User bob = getBob();
        bob.createAndJoin("toto", "one description");
        bob.createAndJoin("toto", null);
    }
}
