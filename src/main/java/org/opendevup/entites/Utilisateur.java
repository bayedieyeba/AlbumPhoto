package org.opendevup.entites;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity(name = "utilisateur")
public class Utilisateur implements Serializable
{
	@Id
	@GeneratedValue
	private long id;
	private String nom;
	private String prenom;
	private String login;
	private String password;
	@Enumerated(EnumType.STRING)
	private EnumUtilisateur enumUtilisateur;
	
	public Utilisateur() 
	{
		super();
		
	}

	public Utilisateur(String nom, String prenom, String login, String password) 
	{
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.password = password;
		this.enumUtilisateur = EnumUtilisateur.simple ;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public String getPrenom() 
	{
		return prenom;
	}

	public void setPrenom(String prenom) 
	{
		this.prenom = prenom;
	}

	public String getLogin() 
	{
		return login;
	}

	public void setLogin(String login) 
	{
		this.login = login;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public EnumUtilisateur getEnumUtilisateur() 
	{
		return enumUtilisateur;
	}

	public void setEnumUtilisateur(EnumUtilisateur enumUtilisateur) 
	{
		this.enumUtilisateur = enumUtilisateur;
	}
	
	
}
