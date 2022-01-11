package org.opendevup.entites;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "image")
public class Image implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	
	private long id;
	private Blob image;
	private String titre;
	private String description;
	private String motCle;
	private LocalDateTime dateCreation= LocalDateTime.now();
	private LocalDateTime dateModification =LocalDateTime.now();
	private long id_album;
	
	private String base64Encoded;
	
	public Image() 
	{
		super();
		
	}

	public Image(String titre, String description, String motCle,  long id_album,Blob image) 
	{
		super();
		this.titre = titre;
		this.description = description;
		this.motCle = motCle;
		this.dateCreation = LocalDateTime.now();
		this.dateModification= LocalDateTime.now();
		this.id_album = id_album;
		this.image = image;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getTitre() 
	{
		return titre;
	}

	public void setTitre(String titre) 
	{
		this.titre = titre;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getMotCle() 
	{
		return motCle;
	}

	public void setMotCle(String motCle) 
	{
		this.motCle = motCle;
	}

	public LocalDateTime getDateCreation() 
	{
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) 
	{
		this.dateCreation = dateCreation;
	}
	

	public long getId_album() 
	{
		return id_album;
	}

	public void setId_album(long id_album) 
	{
		this.id_album = id_album;
	}
	
	
	
	public Blob getImage() 
	{
		return image;
	}

	public void setImage(Blob image) 
	{
		this.image = image;
	}
	public String toString()
	{
		return "base64Encoded= " + getBase64Encoded();
	}
	
	public String getBase64Encoded()
	{
		return base64Encoded;
	}
	public void setImageBase64(String base64Encoded) 
	{
		
		this.base64Encoded = base64Encoded;
	}
}
