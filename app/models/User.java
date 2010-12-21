package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {
    @Email
    @Required
    public String email;
    @Required
    public String password;
    public String name;
    public boolean isAdmin;
    public String lang;
    @OneToMany(mappedBy = "donator", cascade = CascadeType.ALL)
    public List<Donation> donations;
    @ManyToMany
    public List<Community> communities;

    public User(String email, String password, String name, String lang) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lang = lang;
        this.donations = new ArrayList<Donation>();
        this.communities = new ArrayList<Community>();
        create();
    }

    public void join(Community community) {
        if (communities.contains(community))
            return;
        if (community == null)
            throw new IllegalArgumentException("Cannot join a null community");
        communities.add(community);
        save();
        community.refresh();
    }

    public Community createAndJoin(String communityName, String description) {
        if (findCommunity(communityName) != null)
            throw new IllegalStateException("You are already in a community called " + communityName);
        final Community community = new Community(communityName, description);
        join(community);
        return community;
    }

    public Community findCommunity(String named) {
        for (Community community : communities)
            if (community.name.equals(named))
                return community;
        return null;
    }

    public Donation donate(String name, Community community, Currency currency, BigDecimal cost, int units) {
        final Donation donation = new Donation(name, community, this, currency, cost, units);
        donations.add(donation);
        save();
        return donation;
    }

    public static User connect(String username, String password) {
        return User.find("byEmailAndPassword", username, password).first();
    }
}
