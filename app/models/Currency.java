package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Currency extends Model {
    @Required
    public String name;

    public Currency(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException();
        this.name = name;
        save();
    }

    public static Currency findCurrency(String byName) {
        return Currency.find("byName", byName).first();
    }

    @Override
    public String toString() {
        return name;
    }
}
