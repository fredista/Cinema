package org.sid.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Patch;

import org.sid.dao.FilmRepository;
import org.sid.dao.TicketRepository;
import org.sid.model.Film;
import org.sid.model.Ticket;
import org.sid.model.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")
public class CinemaRescontroller {
	
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	/*@Data @AllArgsConstructor @NoArgsConstructor
	class TickeForm{
		private String nomClient;
		private int codePayement;
		private List<Long> ticket = new ArrayList<>();
	}
	*/
	@GetMapping("/listfilm")
	public List<Film> listefilm(){
		
		return filmRepository.findAll();
	}

	
	@GetMapping(path = "/imageFilm/{id}",produces=MediaType.IMAGE_JPEG_VALUE)
	
	public byte[] image(@PathVariable(name="id")Long id) throws Exception {
		
		Film film = filmRepository.findById(id).get();
		String filmname = film.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+filmname+".jpg");
		Path path = Paths.get(file.toURI());
		
		return  Files.readAllBytes(path);
		
	}
	
	@PostMapping("/payeticket")
	@Transactional
	public List<Ticket> payerTicket(@RequestBody TicketForm ticket){
		
		List<Ticket> listticket = new ArrayList<>();
		
		ticket.getTicket().forEach(id ->{
			Ticket ticke = ticketRepository.findById(id).get();
			ticke.setReserve(true);
			ticke.setNomClient(ticket.getNomClient());
			ticketRepository.save(ticke);
			listticket.add(ticke);
		});
		return listticket;
		
	}
}
