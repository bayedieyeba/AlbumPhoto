package org.opendevup.entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "album")
public class Album implements Serializable
{
	@Id
	@GeneratedValue
	private long id;
	@Enumerated(EnumType.STRING)
	private Categorie categorie;
	private String nom;
	private long id_utilisateur;
	@Enumerated(EnumType.STRING)
	private EnumAlbum enumAlbum;
	
	public Album() 
	{
		super();
		
	}

	public Album(Categorie categorie, String nom, long id_utilisateur,EnumAlbum enumAlbum) 
	{
		super();
		this.categorie = categorie;
		this.nom = nom;
		this.id_utilisateur = id_utilisateur;
		this.enumAlbum = enumAlbum;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public Categorie getCategorie() 
	{
		return categorie;
	}

	public void setCategorie(Categorie categorie) 
	{
		this.categorie = categorie;
	}

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public long getId_utilisateur() 
	{
		return id_utilisateur;
	}

	public void setId_utilisateur(long id_utilisateur) 
	{
		this.id_utilisateur = id_utilisateur;
	}
	
	public EnumAlbum getEnumAlbum() 
	{
		return enumAlbum;
	}

	public void setEnumAlbum(EnumAlbum enumAlbum) 
	{
		this.enumAlbum = enumAlbum;
	}
	
}
