package org.opendevup.web;

import java.util.List;

import org.opendevup.dao.EtudiantRepository;
import org.opendevup.entites.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/Etudiants")
public class EtudiantControleur {
	
	private EtudiantRepository etudiantRepository;
	
	@RequestMapping(value = "/Index")
	public String index(Model model, 
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="motCle",defaultValue="")String mc) {
		Page<Etudiant> etds= etudiantRepository.chercherEdtudiants("%"+mc+"%",
				 PageRequest.of(page,5));
		int pagesCount = etds.getTotalPages();
		int[] pages = new int[pagesCount];
		for(int i = 0;i<pagesCount;i++) pages[i]=i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageEtudiants", etds);
		model.addAttribute("pageCourante", page);
		model.addAttribute("motCle", mc);
		return "etudiants";
	}

}
