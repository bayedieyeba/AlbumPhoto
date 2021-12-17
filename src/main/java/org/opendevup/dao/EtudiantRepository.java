package org.opendevup.dao;

import java.util.Date;
import java.util.List;

import org.opendevup.entites.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
	public List<Etudiant> findByNom(String nom);
	public Page<Etudiant> findByNom(String nom , Pageable pageable);
	
	@Query( value = "select nom from Etudiant e where e.nom like :x")
	public Page<Etudiant> chercherEdtudiants(@Param("x") String mc, Pageable pageable);
	@Query(value = "select nom from Etudiant e where e.dateNaissance >:x and e.dateNaissance <:y")
	public List<Etudiant> chercherEdtudiants(@Param("x")Date d1, @Param("y")Date d2);

}
