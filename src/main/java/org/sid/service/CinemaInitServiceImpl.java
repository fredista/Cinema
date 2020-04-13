package org.sid.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.dao.CategorieRepository;
import org.sid.dao.CinemaRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.PlaceRepository;
import org.sid.dao.ProjectionRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SeanceRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.VilleRepository;
import org.sid.model.Categorie;
import org.sid.model.Cinema;
import org.sid.model.Film;
import org.sid.model.Place;
import org.sid.model.Projection;
import org.sid.model.Salle;
import org.sid.model.Seance;
import org.sid.model.Ticket;
import org.sid.model.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements IcinemaInitService {
	
	@Autowired
	private VilleRepository VilleRepository;
	@Autowired
	private CinemaRepository CinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private ProjectionRepository ProjectionRepository; 
	@Autowired
	private FilmRepository filmRepository;

	@Override
	public void initVille() {
		
		Stream.of("Ndjamena","sarh","Moundou","Abeche","Lai","Doba").forEach(v->{
			Ville ville = new Ville();
			ville.setName(v);
			VilleRepository.save(ville);
		});
		
	}

	@Override
	public void initCinema() {
		VilleRepository.findAll().forEach(ville -> {
			Stream.of("Rio","detente","preseverance","chagoua").forEach(cine->{
				Cinema cinema = new Cinema();
				cinema.setName(cine);
				cinema.setNbresalle(3+(int)(Math.random()*7));
				cinema.setVille(ville);
				CinemaRepository.save(cinema);
			});
		});
		
		
	}

	@Override
	public void initSalle() {
		CinemaRepository.findAll().forEach(cinema -> {
			for(int i = 0; i<cinema.getNbresalle(); i++) {
				Salle salle = new Salle();
				salle.setNom("salle"+(i+1));
				salle.setNbreplaces(10+(int)(Math.random()*10));
				salle.setCinema(cinema);
				salleRepository.save(salle);
				
			}
		});
		
	}

	@Override
	public void initPlace() {
		salleRepository.findAll().forEach(salle ->{
			for(int i=0; i< salle.getNbreplaces() ; i++ ) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeance() {
		DateFormat date = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","14:00","16:00","21:00").forEach(heure -> {
			Seance seance = new Seance();
			
			try {
				   seance.setHeuredebut(date.parse(heure));
				   seanceRepository.save(seance);
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		
	}

	@Override
	public void initCategorie() {
		Stream.of("Histoire","Geagraphie","action","politique","theatre").forEach(cate ->{
			Categorie categorie = new Categorie();
			categorie.setName(cate);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilm() {
		
		double dure []= {1,2,3,4};
		List<Categorie> categorie = categorieRepository.findAll();
		//Categorie categori = categorie.get([new Random().nextInt(categorie.size())]);
		Stream.of("Junoposterp","murder","bluevalentine","underground","fractured").forEach(des ->{
			Film film = new Film();
			film.setDescription("tres interessant");
			film.setTitre(des);
			film.setPhoto(des);
			film.setDuree(dure[new Random().nextInt(dure.length)]);
			film.setCategorie(categorie.get(new Random().nextInt(categorie.size())));
			filmRepository.save(film);
		});
		
	}

	@Override
	public void initProjection() {
		
		double price [] = new double[] {10,20,30,40,50};
		List<Film> films = filmRepository.findAll();
		VilleRepository.findAll().forEach(ville ->{
			ville.getCinema().forEach(cinema ->{
				cinema.getSalle().forEach(salle ->{
				int index = new Random().nextInt(films.size());
				Film film = films.get(index);
						seanceRepository.findAll().forEach(seance ->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(price[new Random().nextInt(price.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							ProjectionRepository.save(projection);
							
						});
				
				});
			});
		});
		
	}

	@Override
	public void initTicket() {
		// TODO Auto-generated method stub
		ProjectionRepository.findAll().forEach(projection ->{
			projection.getSalle().getPlace().forEach(place ->{
				Ticket ticket = new Ticket();
				ticket.setPlaces(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				
				ticketRepository.save(ticket);
			});
		});
		
	}

}
