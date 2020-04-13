package org.sid.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketproj",types = Ticket.class)
public interface TicketProjection {

	
	public Long getId();
	public String  getNomClient();
	public double getPrix() ;
	public Integer   getCodePayement();
	public boolean isReserve();
	public Place getPlaces();
}
