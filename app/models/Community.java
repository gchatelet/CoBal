package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Community extends Model {
    @Required
    public String name;
    public String description;
    @ManyToMany(mappedBy = "communities", cascade = CascadeType.ALL)
    public List<User> users;

    public Community(String name, String description) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Community name cannot be null nor empty");
        this.name = name;
        this.description = description;
        this.users = new ArrayList<User>();
        create();
    }
}
