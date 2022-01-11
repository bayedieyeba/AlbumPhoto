package org.opendevup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.opendevup.entites.EnumUtilisateur;
import org.opendevup.entites.Utilisateur;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;


@Repository
public class UtilisateurDao 
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void ajouterUtilisateur(Utilisateur utilisateur )
	{
		entityManager.persist(utilisateur);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Utilisateur> listUtilisateur()
	{
		List<Utilisateur> liste;
		Query q = entityManager.createQuery("SELECT u from utilisateur u");
		liste  = q.getResultList();
		return liste;
	}
	@Transactional
	public void modifierUser(Utilisateur utilisateur)
	{
		entityManager.merge(utilisateur);
	}
	@Transactional
	public Utilisateur connecter(String login)
	{
		Utilisateur utilisateur = null;
		Query q = entityManager.createQuery("SELECT  u FROM utilisateur u WHERE u.login like :login");
		q.setParameter("login", login);
		utilisateur = (Utilisateur) q.getSingleResult();	
		return utilisateur;	
		 
	}
	
	@Transactional
	public Utilisateur modifiertypeUser(long id)
	{
		Utilisateur utilisateur = null;
		Query q = entityManager.createQuery("SELECT  u FROM utilisateur u WHERE u.id like :id");
		q.setParameter("id", id);
		utilisateur = (Utilisateur) q.getSingleResult();
		
		return utilisateur;
	}
	@Transactional
	public void modifierUser(long id)
	{
		Utilisateur utilisateur = null;
		Query q = entityManager.createQuery("SELECT  u FROM utilisateur u WHERE u.id like :id");
		q.setParameter("id", id);
		utilisateur = (Utilisateur) q.getSingleResult();
		
		entityManager.merge(utilisateur);
	}
}
