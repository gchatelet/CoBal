package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    public List<Donation> donations;

    public User(String email, String password, String name, String lang) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lang = lang;
        this.donations = new ArrayList<Donation>();
    }

    public Donation donate(Item item, BigDecimal price, int count) {
        final Donation donation = new Donation(this, item, price, count);
        donation.create();
        donations.add(donation);
        return donation;
    }

    public static User connect(String username, String password) {
        return User.find("byEmailAndPassword", username, password).first();
    }
}
