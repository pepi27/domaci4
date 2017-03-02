package menjacnica.model;

public class Valuta {

	private String oznakaValute;
	private String naziv;
	
	 
	
	public Valuta(String text) {
		
		String[] tokeni = text.split(",");
		if(tokeni.length != 2) {
			System.out.println("Greska pri ucitavanju valute");
			System.exit(0);
		}
		oznakaValute = tokeni[0];
		naziv = tokeni[1];
	}
	
	public String getOznakaValute() {
		return oznakaValute;
	}
	public void setOznakaValute(String oznakaValute) {
		this.oznakaValute = oznakaValute;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return "Valuta [oznakaValute=" + oznakaValute + ", naziv=" + naziv + "]";
	} 
	
	
	
}
