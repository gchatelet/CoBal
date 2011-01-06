package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Community extends Model {
    @Required
    public String name;
    public String description;
    @ManyToMany(mappedBy = "communities", cascade = CascadeType.ALL)
    public List<User> users;
    // the mappedby side cannot be "community" or it will conflict
    // with the donations mappedBy ...
    @OneToMany(mappedBy = "withinCommunity", cascade = CascadeType.ALL)
    public List<Reimbursement> transfers;
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    public List<Donation> donations;

    public Community(String name, String description) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Community name cannot be null nor empty");
        this.name = name;
        this.description = description;
        this.users = new ArrayList<User>();
        this.transfers = new ArrayList<Reimbursement>();
        this.donations = new ArrayList<Donation>();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " : " + name;
    }
}
