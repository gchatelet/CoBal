package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Community extends Model {
	@Required
	public String name;

	public String description;

	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
	public List<Item> items;

	public Community(String name, String description) {
		this.items = new ArrayList<Item>();
		this.name = name;
		this.description = description;
	}

	public Item addItem(String name) {
		final Item item = new Item(this, name);
		item.create();
		items.add(item);
		this.save();
		return item;
	}

}
