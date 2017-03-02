package menjacnica.ui;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import menjacnica.model.KursnaLista;
import menjacnica.model.Valuta;
import menjacnica.model.VrednostValute;
import menjacnica.utils.PomocnaKlasa;

public class KursnaListaUI {
	
	public static ArrayList<KursnaLista> sveKL = new ArrayList<>();

	public static void prikaziMeniKL() {
		int odluka = -1;
		while(odluka!= 0){
			ispisiTekstListaOpcije();
			System.out.print("opcija:");
			odluka = PomocnaKlasa.ocitajCeoBroj();
			switch (odluka) {
				case 0:	
					System.out.println("Izlaz");	
					break;
				case 1:	
					islistajSveListe();	
					break;
				default:
					System.out.println("Nepostojeca komanda");
					break;	
			}
		}
	}
	
	public static void kreirajNovuListuMENU() throws IOException {
		int odluka = -1;
		while(odluka!= 0){
			ispisiNovuListuOpcije();
			System.out.print("opcija:");
			odluka = PomocnaKlasa.ocitajCeoBroj();
			switch (odluka) {
				case 0:	
					System.out.println("Izlaz");	
					break;
				case 1:	
					kreirajNovuListu();
					break;
				default:
					System.out.println("Nepostojeca komanda");
					break;	
			}
		}
	}
	
	public static void kreirajNovuListu() throws IOException {
		
		URL url = new URL("http://www.kursna-lista.com/kursna-lista-nbs");
		Document doc = Jsoup.parse(url, 3000);

		Element table = doc.select("#curFullTable").first();
		
		Elements rows = table.select("tr");
		
		KursnaLista poslednjaLista = new KursnaLista();
		
		for (int i = 1; i < rows.size(); i++) { 
	        Element row = rows.get(i);
	        
	        String oznaka = row.select("td").get(1).text();
	        VrednostValute v = new VrednostValute();
	        Valuta valuta = ValutaUI.nadjiValutuPoSifri(oznaka);
	        if(valuta != null) {
	        	v.setValuta(valuta);
	        	v.setKupovnaVrednost(Double.parseDouble(row.select("td").get(4).text()));
	        	v.setProdajnaVrednost(Double.parseDouble(row.select("td").get(6).text()));
	        	poslednjaLista.getSpisakValuta().add(v);
	        }
	        
		}
		
		sveKL.add(poslednjaLista);
		
		String sP = System.getProperty("file.separator");
		File listKursneListe = new File("."+sP+"data"+sP+"kursne_liste.csv");
		KursnaListaUI.ucitajVrednostiUFajl(listKursneListe);
	}
	
	public static void ispisiTekstListaOpcije(){	
		System.out.println("Rad sa Kursnim Listama - opcije:");
		System.out.println("\tOpcija broj 1 - Izlistaj sve liste");
		System.out.println("\t\t ...");
		System.out.println("\tOpcija broj 0 - IZLAZ");	
	}
	
	public static void ispisiNovuListuOpcije(){	
		System.out.println("Rad sa Novim Listama - opcije:");
		System.out.println("\tOpcija broj 1 - Kreiraj Novu Kursnu Listu");
		System.out.println("\t\t ...");
		System.out.println("\tOpcija broj 0 - IZLAZ");	
	}
	
	public static KursnaLista pronadjiKursnuListuId(long id){
		KursnaLista retVal = null;
		for (int i = 0; i < sveKL.size(); i++) {
			KursnaLista kl = sveKL.get(i);
			if (kl.getId()==id) {
				retVal = kl;
				break;
			}
		}
		return retVal;
	}
	
	public static void islistajSveListe() {
		for(KursnaLista kl : sveKL) {
			System.out.println(kl);
		}
	}

	public static void ucitajValuteSaFajla(File document) throws IOException, ParseException{
		if(document.exists()) {
			
			BufferedReader in = new BufferedReader(new FileReader(document));
			
			in.mark(1);
			if(in.read() != '\ufeff'){
				in.reset();
			}
			
			String s2; 
			while((s2 = in.readLine()) != null) {
				sveKL.add(new KursnaLista(s2));
			}
			in.close();
			
		} else {
			System.out.println("Ne postoji fajl.");
		}
	}

	public static void ucitajVrednostiUFajl(File listKursneListe) throws IOException{
		
			PrintWriter out2 = new PrintWriter(new FileWriter(listKursneListe));
			for (KursnaLista kl : sveKL) {
				out2.println(kl.toFileRepresentation());
			}
			
			out2.flush();
			out2.close();
	}
	
	public static ArrayList<KursnaLista> pronadjiKLPoDatumu(String datumOd, String datumDo) throws ParseException {
		ArrayList<KursnaLista> retKL = new ArrayList<>();
		DateFormat dtf = new SimpleDateFormat("dd/MM/yy");
		for(KursnaLista kl : sveKL) {
			if(kl.getFormiranaNaDatum().compareTo(dtf.parse(datumOd)) > 0 && kl.getFormiranaNaDatum().compareTo(dtf.parse(datumDo)) < 0) {
				retKL.add(kl);
			}
		}
		return retKL; 
	}
}
