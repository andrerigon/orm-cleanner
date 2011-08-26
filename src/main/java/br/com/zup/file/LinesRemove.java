/*
 * Copyright (c) 2011, ZUP IT INNOVATION <contato@zup.com.br> All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the ZUP IT INNOVATION nor the names of its contributors 
 * may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.zup.file;

public enum LinesRemove {
	
	// IMPORTS
	IMPORT_ORM_CLEANNER("import br.com.zup.annotation.Cleanner;"),
	IMPORT_JAVAX_PERSISTENCE("import javax.persistence.(.+?);"),
	
	// ANNOTATIONS
	ANNOTATION_CLEANNER("@Cleanner(\\s*\\(.+?\\))|@Cleanner"),
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
	ANNOTATION_MANY_TO_MANY("@ManyToMany(\\s*\\(.+?\\))|@ManyToMany"),
	ANNOTATION_GENERATED_VALUE("@GeneratedValue(\\s*\\(.+?\\))|@GeneratedValue"),
	ANNOTATION_SEQUENCE_GENERATOR("@SequenceGenerator(\\s*\\(.+?\\))|@SequenceGenerator");

	private final String lineRegex;

	private LinesRemove(String lineRegex) {
		this.lineRegex = lineRegex;
	}

	public String lineRegex() {
		return this.lineRegex;
	}
	
	
}
