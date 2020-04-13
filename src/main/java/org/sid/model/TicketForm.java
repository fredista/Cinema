package org.sid.model;

import java.util.ArrayList;
import java.util.List;

import org.sid.web.CinemaRescontroller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TicketForm {
	private String nomClient;
	private int codePayement;
	private List<Long> ticket = new ArrayList<>();

}
