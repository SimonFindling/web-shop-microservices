package de.hska.vislab.webshop.ui.model;

public class Role {
	public Long id;
	public int level;
	public String typ;

	public Role() {
	}

	public Role(int level, String typ) {
		super();
		this.level = level;
		this.typ = typ;
	}

	public Role(Long id, int level, String typ) {
		super();
		this.id = id;
		this.level = level;
		this.typ = typ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
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
		if (typ == null) {
			if (other.typ != null)
				return false;
		} else if (!typ.equals(other.typ))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", level=" + level + ", typ=" + typ + "]";
	}
}
