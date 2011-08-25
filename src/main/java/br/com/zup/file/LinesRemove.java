package br.com.zup.file;


public enum LinesRemove {
	
	// IMPORTS
	IMPORT_ENTITY("import javax.persistence.Entity;"),
	IMPORT_TABLE("import javax.persistence.Table;"),
	IMPORT_COLUMN("import javax.persistence.Column;"),
	IMPORT_ID("import javax.persistence.Id;"),
	IMPORT_FETCH_TYPE("import javax.persistence.FetchType;"),
	IMPORT_ONE_TO_ONE("import javax.persistence.OneToOne;"),
	IMPORT_JOIN_COLUMN("import javax.persistence.JoinTable;"),
	IMPORT_JOIN_TABLE("import javax.persistence.JoinColumn;"),
	IMPORT_MANY_TO_ONE("import javax.persistence.ManyToOne;"),
	IMPORT_TRANSIENT("import javax.persistence.Transient;"),
	IMPORT_ONE_TO_MANY("import javax.persistence.OneToMany;"),
	IMPORT_MANY_TO_MANY("import javax.persistence.ManyToMany;"),
	
	// ANNOTATIONS
	ANNOTATION_ENTITY("@Entity(\\s*\\(.+?\\))|@Entity"),
	ANNOTATION_TABLE("@Table(\\s*\\(.+?\\))|@Table"),
	ANNOTATION_ID("@Id(\\s*\\(.+?\\))|@Id"),
	ANNOTATION_COLUMN("@Column(\\s*\\(.+?\\))|@Column"),
	ANNOTATION_ONE_TO_ONE("@OneToOne(\\s*\\(.+?\\))|@OneToOne"),
	ANNOTATION_JOIN_COLUMN("@JoinColumn(\\s*\\(.+?\\))|@JoinColumn"),
	ANNOTATION_JOIN_TABLE("@JoinTable(\\s*\\(.+?\\))|@JoinTable"),
	ANNOTATION_MANY_TO_ONE("@ManyToOne(\\s*\\(.+?\\))|@ManyToOne"),
	ANNOTATION_TRANSIENT("@Transient(\\s*\\(.+?\\))|@Transient"),
	ANNOTATION_ONE_TO_MANY("@OneToMany(\\s*\\(.+?\\))|@OneToMany"),
	ANNOTATION_MANY_TO_MANY("@ManyToMany(\\s*\\(.+?\\))|@ManyToMany");

	private final String lineRegex;

	private LinesRemove(String lineRegex) {
		this.lineRegex = lineRegex;
	}

	public String lineRegex() {
		return lineRegex;
	}
	
	
}
