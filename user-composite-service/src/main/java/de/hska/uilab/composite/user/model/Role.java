package de.hska.uilab.composite.user.model;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = 2099448359591589435L;

	public Long id;
	public int level;
	public String type;
	
	protected Role() {
		super();
	}

	public Role(int level, String type) {
		super();
		this.level = level;
		this.type = type;
	}

	public Role(Long id, int level, String type) {
		super();
		this.id = id;
		this.level = level;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", level=" + level + ", type=" + type + "]";
	}

}
