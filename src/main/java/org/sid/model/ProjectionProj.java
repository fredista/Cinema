package org.sid.model;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="pp",types = {org.sid.model.Projection.class})
public interface ProjectionProj {
	
	public Long getId();
	public Date getDateProjection();
	public double getPrix();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTicket();

}
