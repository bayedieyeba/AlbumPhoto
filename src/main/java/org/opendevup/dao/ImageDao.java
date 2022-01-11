package org.opendevup.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.opendevup.entites.Image;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao 
{
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void ajouterImage(Image image)
	{
		em.persist(image);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> listerAllImage(long id)
	{
		List<Image> liste = new ArrayList<Image>();
		Query q = em.createQuery("SELECT i from  image i where i.id_album =?1");
		q.setParameter(1, id);
		liste = q.getResultList();
		return liste;
	}
	@Transactional
	public Image detaillerImage(long id)
	{
		Query q = em.createQuery("SELECT i from image i where i.id=?1");
		q.setParameter(1, id);
		Image image;
		image = (Image) q.getSingleResult();
		
		return image ;
	}
	@Transactional
	public void supprimerPhoto(long id)
	{
		Query q = em.createQuery("SELECT i from image i where i.id=?1");
		q.setParameter(1, id);
		Image image;
		image = (Image) q.getSingleResult();
		em.remove(image);
		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> mesPhoto(long id)
	{
		List<Image>  liste;
		Query q = em.createQuery("SELECT i from  image i where i.id_album =?1");
		q.setParameter(1, id);
		liste = q.getResultList();
		return liste;
	}
}
