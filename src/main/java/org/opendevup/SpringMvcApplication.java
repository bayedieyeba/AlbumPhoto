package org.opendevup;

import java.text.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringMvcApplication {
 
	public static void main(String[] args) throws ParseException {
	ApplicationContext ctx=	SpringApplication.run(SpringMvcApplication.class, args);
	//EtudiantRepository etudiantRepository = ctx.getBean(EtudiantRepository.class);
//	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	etudiantRepository.save(new Etudiant("baye", df.parse("1998-12-19"), "bayedieyeba3@gmail.com", "baye.jpg"));
//	etudiantRepository.save(new Etudiant("modou", df.parse("1999-10-29"), "modou3@gmail.com", "modou.jpg"));
//	etudiantRepository.save(new Etudiant("cheikh", df.parse("1997-02-09"), "chaikh3@gmail.com", "chaikh.jpg"));
//	
	}

}
