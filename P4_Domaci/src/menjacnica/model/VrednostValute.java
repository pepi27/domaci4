package menjacnica.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import menjacnica.ui.KursnaListaUI;
import menjacnica.ui.ValutaUI;
import menjacnica.utils.PomocnaKlasa;


public class VrednostValute {

	public static ArrayList<VrednostValute> sveVrednosti = new ArrayList<>();
	
	private KursnaLista kl; 
	private Valuta valuta; 
	
	private int id; 
	private double kupovnaVrednost; 
	private double prodajnaVrednost; 
	private double trenutnaVrednostValute;
	
	public VrednostValute() {}
	
	public VrednostValute(String text) {
		String[] tokeni = text.split(",");
		if(tokeni.length != 4) {
			System.out.println("Greska pri unosenju vrednosti");
			System.exit(0);
		}
		
		String sifraValute = tokeni[0];
		kupovnaVrednost = Double.parseDouble(tokeni[1]);
		prodajnaVrednost = Double.parseDouble(tokeni[2]);
		long idKursneListe = Long.parseLong(tokeni[3]);
		
		valuta = ValutaUI.nadjiValutuPoSifri(sifraValute);
		if(valuta == null) {
			System.out.println("Ne postoje podaci o valuti");
		}
		
		kl = KursnaListaUI.pronadjiKursnuListuId(idKursneListe);
		if(kl==null){
			System.out.println("Podaci o kursnoj listi ne postoje " + text);
		}
		
		kl.getSpisakValuta().add(this);
		
		
	}

	public double getKupovnaVrednost() {
		return kupovnaVrednost;
	}
	public void setKupovnaVrednost(double kupovnaVrednost) {
		this.kupovnaVrednost = kupovnaVrednost;
	}
	public double getProdajnaVrednost() {
		return prodajnaVrednost;
	}
	public void setProdajnaVrednost(double prodajnaVrednost) {
		this.prodajnaVrednost = prodajnaVrednost;
	}
	public double getTrenutnaVrednostValute() {
        trenutnaVrednostValute = (kupovnaVrednost + prodajnaVrednost) / 2; 
		return trenutnaVrednostValute;
	}
	public void setTrenutnaVrednostValute(double trenutnaVrednostValute) {
		
		this.trenutnaVrednostValute = trenutnaVrednostValute;
	}
	
	

	public Valuta getValuta() {
		return valuta;
	}

	public void setValuta(Valuta valuta) {
		this.valuta = valuta;
	}

	public static void ucitajVrednostiSaFajla(File document) throws IOException {
		
		if(document.exists()){

			BufferedReader in = new BufferedReader(new FileReader(document));
			
			//workaround for UTF-8 files and BOM marker
			//BOM (byte order mark) marker may appear on the beginning of the file
			//BOM can signal which of several Unicode encodings (8-bit, 16-bit, 32-bit) that text is encoded as
			
			in.mark(1); //zapamti trenutnu poziciju u fajlu da mozes kasnije da se vratis na nju
			if(in.read()!='\ufeff'){
				in.reset();
			}
			
			String s2;
			while((s2 = in.readLine()) != null) {
				sveVrednosti.add(new VrednostValute(s2));
			}
			in.close();
		} else {
			System.out.println("Ne postoji fajl!");
		}
	}

	
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.###");
		return valuta.getOznakaValute() + "," + df.format(getTrenutnaVrednostValute());
	}

	public static VrednostValute pronadjiVrednostOznaka(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	// nedovrsen metod
	public static VrednostValute pronadjiVrednostPoOznaci(String oznaka) {
		VrednostValute retVal = null;
		for (int i = 0; i < sveVrednosti.size(); i++) {
			
		}
		return retVal;
	}

	public static VrednostValute nadjiSpisakValutaPoID(int id) {
		VrednostValute retVal = null; 
		for(int i = 0; i < sveVrednosti.size(); i++) {
			if(sveVrednosti.get(i).getId() == id) {
				retVal = sveVrednosti.get(i);
				break; 
			}
		}
		return retVal; 
	}
	
	public int getId() {
		return id; 
	}

	public static void ispisiStatistiku() throws ParseException {
		System.out.println("Unesite pocetni datum  u formi  dan/mesec/godina (zadnje dve cifre) : ");
		String startDate = PomocnaKlasa.ocitajTekst(); 
		System.out.println("Unesite zarvsni datum u formi  dan/mesec/godina (zadnje dve cifre) : ");
		String endDate = PomocnaKlasa.ocitajTekst();
		
		ArrayList<KursnaLista> kl = KursnaListaUI.pronadjiKLPoDatumu(startDate, endDate);
		
		for(KursnaLista klist : kl) {
			System.out.println();
			for(VrednostValute v1 : klist.getSpisakValuta()) {
				System.out.println(v1.getValuta().getOznakaValute() + " srednja vrednost : " + v1.getTrenutnaVrednostValute());
				
			}
			Collections.sort(klist.getSpisakValuta(), new Comparator<VrednostValute>(){

				@Override
				public int compare(VrednostValute o1, VrednostValute o2) {
					return Double.compare(o1.trenutnaVrednostValute, o2.trenutnaVrednostValute);
				}
			});
		}
		System.out.println("\n--- Najnize Srednje Vrednosti Po Valuti ---");
		System.out.println(sveVrednosti);
		System.out.println();
	}
}	
