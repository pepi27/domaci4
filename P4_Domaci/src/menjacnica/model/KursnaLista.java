package menjacnica.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KursnaLista {

	static long idCounter; 
	
	private long id; 
	
	private Date formiranaNaDatum;
	
	private ArrayList<VrednostValute> spisakValuta = new ArrayList<>();
	
	public KursnaLista() {
		idCounter++;
		id = idCounter; 
		this.formiranaNaDatum = new Date();
		
	}
	
	public KursnaLista(String text) throws ParseException {
		String[] tokeni = text.split(",");
		
		
		id = Integer.parseInt(tokeni[0]) + 1;
	
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		formiranaNaDatum = sdf.parse(tokeni[1]);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFormiranaNaDatum() {
		return formiranaNaDatum;
	}

	public void setFormiranaNaDatum(Date formiranaNaDatum) {
		this.formiranaNaDatum = formiranaNaDatum;
	}

	public ArrayList<VrednostValute> getSpisakValuta() {
		return spisakValuta;
	}

	public void setSpisakValuta(ArrayList<VrednostValute> spisakValuta) {
		this.spisakValuta = spisakValuta;
	}
	
	public String toFileRepresentation(){
		StringBuilder bild = new StringBuilder(); 
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		bild.append(id +"," + sdf.format(formiranaNaDatum) + "," + spisakValuta);
		return bild.toString();
	}

	@Override
	public String toString() {
		return "KursnaLista " + id + ", formirana na datum : " + formiranaNaDatum + "\n" + spisakValuta;
	}

	

}
