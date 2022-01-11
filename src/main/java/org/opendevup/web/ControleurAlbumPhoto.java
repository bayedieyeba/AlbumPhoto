package org.opendevup.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.opendevup.dao.AlbumDao;
import org.opendevup.dao.ImageDao;
import org.opendevup.dao.UtilisateurDao;
import org.opendevup.entites.Album;
import org.opendevup.entites.Categorie;
import org.opendevup.entites.EnumAlbum;
import org.opendevup.entites.EnumUtilisateur;
import org.opendevup.entites.Image;
import org.opendevup.entites.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/album")
public class ControleurAlbumPhoto 
{
	@Autowired
	private UtilisateurDao utilisateurDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private AlbumDao albumDao;
	
	// Conttroller of the user
	
	@RequestMapping(value = "/accueil")
	public String accueil(Model model)
	{
		List<Album> albums ;
		albums = albumDao.liteAlbumPublique();
		model.addAttribute("albums", albums);
		return "accueil";
	}
	@RequestMapping(value = "/formulaireInscription")
	public String inscription(Model model)
	{
		Utilisateur utilisateur = new Utilisateur();
		model.addAttribute("utilisateur", utilisateur);
		return "inscription";
	}
	
	@GetMapping(value = "/inscription")
	public String indexRedirec(Model model, 
			@RequestParam(required = true, name="nom") String nom, @RequestParam(required = true, name= "prenom") String prenom,
			@RequestParam(required = true, name = "login") String login,@RequestParam(required=true, name = "password")String password
			)
	{	
		Utilisateur utilisateur = new Utilisateur() ;
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setLogin(login);
		utilisateur.setPassword(password);
		utilisateur.setEnumUtilisateur(EnumUtilisateur.simple);
		utilisateurDao.ajouterUtilisateur(utilisateur);
		
		model.addAttribute("utilisateur", utilisateur);
		
		return "welcome";
	}
	
	@RequestMapping(value = "/formulaireConnexion")
	public String connecter(Model model)
	{
		Utilisateur utilisateur = new Utilisateur();
		model.addAttribute("utilisateur", utilisateur);
		return "connexion";
	}
	
	@GetMapping(value = "/connexion")
	public String afficherAjouImage(Model model,
			@RequestParam("login") String login,@RequestParam("password") String password,
			HttpServletRequest request)
	{
		Utilisateur utilisateur = null;
		utilisateur = utilisateurDao.connecter(login);
		if(utilisateur != null && !utilisateur.getPassword().equals(password))
		{
			utilisateur = null;
		}
		if(utilisateur != null) 
		{
			List<Album> albums ;
			albums = albumDao.listerMesAlbum(utilisateur.getId());
			HttpSession session = request.getSession();
			session.setAttribute("utilisateur", utilisateur);
			model.addAttribute("utilisateur", utilisateur);
			model.addAttribute("albums", albums);
			return "mesAlbums";
			
		}
		else
		{
			
			return "connexion";
		}
		
	}
	@RequestMapping(value = "/deconnexion")
	public String deconnecter()
	{
		return "connexion";
	}
	@GetMapping(value = "/list/users")
	public String listerUser(Model model , Utilisateur utilisateur)
	{
		List<Utilisateur> users ;
		users = utilisateurDao.listUtilisateur();
		model.addAttribute("users", users);
		return "utilisateurs";
	}
	
	
	@RequestMapping(value = "/modifierUtilisateur")
	public String modifierUser(@RequestParam("id") long id, Model model)
	{
		Utilisateur utilisateur;
		utilisateur = utilisateurDao.modifiertypeUser(id);
		EnumUtilisateur enumUtilisateur = utilisateur.getEnumUtilisateur();
		if(enumUtilisateur==EnumUtilisateur.valueOf("simple"))
		{
			utilisateur.setEnumUtilisateur(EnumUtilisateur.valueOf("administrateur"));
			utilisateurDao.modifierUser(id);
		}
		else
		{
			utilisateur.setEnumUtilisateur(EnumUtilisateur.valueOf("simple"));
			utilisateurDao.modifierUser(id);
		}
		List<Utilisateur> users ;
		users = utilisateurDao.listUtilisateur();
		model.addAttribute("users", users);
		return "utilisateurs";
	}
	
	// controller for the albums
	
	@RequestMapping(value = "/formulaireAjoutAlbum")
	public String ajoutAlbum(HttpServletRequest request)
	{
		
		return "ajoutAlbum";
	}
	@RequestMapping(value = "/ajoutAlbum")
	public String redirecAjoutAlbum(Model model, HttpServletRequest request,
			@RequestParam("nom")String nom,@RequestParam("categorie")String categorie,@RequestParam("enumAlbum")String enumAlbum)
	{
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		EnumAlbum enAlbum = EnumAlbum.valueOf(enumAlbum);
		Categorie cat = Categorie.valueOf(categorie);
		Album album = new Album();
		album.setNom(nom);
		album.setCategorie(cat);
		album.setEnumAlbum(enAlbum);
		album.setId_utilisateur(utilisateur.getId());
		albumDao.ajouterAlbum(album);
		List<Album> albums ;
		albums = albumDao.listerMesAlbum(utilisateur.getId());
		model.addAttribute("albums",albums );
		return "mesAlbums";
	}
	@RequestMapping(value = "/modifierAlbum")
	public String modifierAlbum(@RequestParam("id")long id, Model model)
	{
		Album album = albumDao.getAlbumById(id);
		albumDao.modifierAlbum(id);
		List<Album> albums;
		albums= albumDao.listerMesAlbum(id);
		model.addAttribute("albums", albums);
		return "mesAlbums";
	}
	@RequestMapping(value = "/listerPhoto")
	public String listerMesPhotos(Model model,@RequestParam("id") long id, HttpServletRequest request) throws SQLException, UnsupportedEncodingException
	{
		int blobLength;
        byte[] blobAsBytes;
        byte[] encodeBase64;
        String base64Encoded;
		List<Image> imgs ;
		
		imgs = imageDao.mesPhoto(id);
		
		for(Image image:imgs)
		{
			
			blobLength = (int) image.getImage().length();
            blobAsBytes = image.getImage().getBytes(1, blobLength);

            encodeBase64 = Base64.encodeBase64(blobAsBytes);
            base64Encoded = new String(encodeBase64, "UTF-8");
            image.setImageBase64(base64Encoded);	

		}
		
		HttpSession session = request.getSession();
		session.setAttribute("images", imgs);
		model.addAttribute("images", imgs);
		
		return "mesPhotos";
	}
	
	// controller for  images
	@RequestMapping(value = "/formulaireAjoutImage")
	public String ajoutImage(Model model,@RequestParam("id") long id, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		Image image = new Image();
		model.addAttribute("image", image);
		return "ajoutImage";
	}
	
	@RequestMapping( value = "/ajoutImg")
	public String redirecAjoutImage(Model model, HttpServletRequest request,
			@RequestParam("titre") String titre,@RequestParam("description") String description,@RequestParam("motCle") String motCle,
			@RequestParam("image") MultipartFile image) throws IOException, SerialException, SQLException
	{
		Image im = new Image();
		HttpSession session = request.getSession();
		long id = (long) session.getAttribute("id");
		byte [] img = image.getBytes();
		Blob blob = new SerialBlob(img);
		
		im.setTitre(titre);
		im.setDescription(description);
		im.setMotCle(motCle);
		im.setImage(blob);
		im.setId_album(id);
		imageDao.ajouterImage(im);
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		List<Album> albums = albumDao.listerMesAlbum(utilisateur.getId());
		model.addAttribute("albums", albums);
		return "mesAlbums";
	}
	
	@RequestMapping(value = "/list/images")
	public String listerImage(@RequestParam("id")long id, Model model, HttpServletRequest request) throws SQLException, UnsupportedEncodingException
	{	
		int blobLength;
        byte[] blobAsBytes;
        byte[] encodeBase64;
        String base64Encoded;
		List<Image> images ;
		System.out.println(id);
		images = imageDao.listerAllImage(id);
		
		for(Image image:images)
		{
			
			blobLength = (int) image.getImage().length();
            blobAsBytes = image.getImage().getBytes(1, blobLength);

            encodeBase64 = Base64.encodeBase64(blobAsBytes);
            base64Encoded = new String(encodeBase64, "UTF-8");
            image.setImageBase64(base64Encoded);	

		}
		
		HttpSession session = request.getSession();
		session.setAttribute("images", images);
		model.addAttribute("images", images);
		return "images";
	}
	
	@RequestMapping(value = "/detailPhoto")
	public String detaillerPhoto(@RequestParam("id") long id, Model model) throws SQLException, UnsupportedEncodingException
	{
		Image image;
		image = imageDao.detaillerImage(id);
		int blobLength;
        byte[] blobAsBytes;
        byte[] encodeBase64;
        String base64Encoded;
        
        blobLength = (int) image.getImage().length();
        blobAsBytes = image.getImage().getBytes(1, blobLength);

        encodeBase64 = Base64.encodeBase64(blobAsBytes);
        base64Encoded = new String(encodeBase64, "UTF-8");
        image.setImageBase64(base64Encoded);
		
		model.addAttribute("image", image);
		return "detail";
	}
	
	@RequestMapping(value = "/supprimerPhoto")
	public String supprimerPhoto(@RequestParam("id")long id)
	{
		imageDao.supprimerPhoto(id);
		return "photoPublic";
	}
	
}
