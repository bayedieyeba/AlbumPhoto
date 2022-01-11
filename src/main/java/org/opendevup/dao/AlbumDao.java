package org.opendevup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.opendevup.entites.Album;
import org.opendevup.entites.EnumAlbum;
import org.opendevup.entites.Image;
import org.opendevup.entites.Utilisateur;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDao 
{
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void ajouterAlbum(Album album)
	{
		em.persist(album);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Album> listerMesAlbum(long id)
	{
		List<Album> liste;
		Query q = em.createQuery("SELECT a from album a where a.id_utilisateur=?1");
		q.setParameter(1, id);
		liste  = q.getResultList();
		return liste;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Album> liteAlbumPublique()
	{
		List<Album> liste;
		Query q = em.createQuery("SELECT a from album a where a.enumAlbum=?1");
		q.setParameter(1, EnumAlbum.publique);
		liste  = q.getResultList();
		return liste;
	}
	
	@Transactional
	public void modifierAlbum(Album album)
	{
		em.merge(album);
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Image> listerMesPhotos(long id)
	{
		List<Image> images;
		Query q = em.createQuery("SELECT i FROM image i WHERE i.id_album like : id");
		q.setParameter("id", id);
		images = q.getResultList();
		return images;
	}
	@Transactional
	public void modifierAlbum(long id)
	{
		Album album = null;
		Query q = em.createQuery("SELECT  a FROM album a WHERE a.id like :id");
		q.setParameter("id", id);
		album = (Album) q.getSingleResult();
		
		em.merge(album);
	}
	@Transactional
	public Album getAlbumById(long id)
	{
		Album album = null;
		Query q = em.createQuery("SELECT  a FROM album a WHERE a.id like :id");
		q.setParameter("id", id);
		album = (Album) q.getSingleResult();
		return album;
	}
}
