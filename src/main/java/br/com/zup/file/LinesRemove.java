package br.com.zup.file;

public enum LinesRemove {
	
	// IMPORTS
	IMPORT_ENTITY("import javax.persistence.Entity;"),
	IMPORT_TABLE("import javax.persistence.Table;"),
	IMPORT_COLUMN("import javax.persistence.Column;"),
	IMPORT_ID("import javax.persistence.Id;"),
	IMPORT_FETCH_TYPE("import javax.persistence.FetchType;"),
	IMPORT_ONE_TO_ONE("import javax.persistence.OneToOne;"),
	IMPORT_JOIN_COLUMN("import javax.persistence.JoinColumn;"),
	IMPORT_MANY_TO_ONE("import javax.persistence.ManyToOne;"),
	IMPORT_TRANSIENT("import javax.persistence.Transient;"),
	IMPORT_ONE_TO_MANY("import javax.persistence.OneToMany"),
	IMPORT_MANY_TO_MANY("import javax.persistence.ManyToMany"),
	
	// ANNOTATIONS
	ANNOTATION_ENTITY("@Entity"),
	ANNOTATION_TABLE("@Table(.)"),
	ANNOTATION_ID("@Id"),
	ANNOTATION_COLUMN("@Column(.)"),
	ANNOTATION_ONE_TO_ONE("@OneToOne(.)"),
	ANNOTATION_JOIN_COLUMN("@JoinColumn(.)"),
	ANNOTATION_MANY_TO_ONE("@ManyToOne"),
	ANNOTATION_TRANSIENT("@Transient"),
	ANNOTATION_ONE_TO_MANY("@OneToMany(.)"),
	ANNOTATION_MANY_TO_MANY("@ManyToMany(.)");

	private final String lineRegex;

	private LinesRemove(String lineRegex) {
		this.lineRegex = lineRegex;
	}

	public String lineRegex() {
		return lineRegex;
	}
	
	
}
