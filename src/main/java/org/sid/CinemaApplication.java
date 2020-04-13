package org.sid;

import org.sid.model.Film;
import org.sid.model.Salle;
import org.sid.model.Ticket;
import org.sid.service.IcinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
	@Autowired
	private IcinemaInitService icinemaInitService; 
	@Autowired
	private RepositoryRestConfiguration repositoryconf;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		repositoryconf.exposeIdsFor(Film.class,Salle.class,Ticket.class);
		icinemaInitService.initVille();
		icinemaInitService.initCinema();
		icinemaInitService.initSalle();
		icinemaInitService.initPlace();
		icinemaInitService.initSeance();
		icinemaInitService.initCategorie();
		icinemaInitService.initFilm();
		icinemaInitService.initProjection();
		icinemaInitService.initTicket();
		
	}

}
