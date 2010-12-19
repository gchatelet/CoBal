package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Item extends Model {
	@Required
	public String name;

	@ManyToOne
	@Required
	public Community community;

	public Item(Community community, String name) {
		this.name = name;
		this.community = community;
	}
}
