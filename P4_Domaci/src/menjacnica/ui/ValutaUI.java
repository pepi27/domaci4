package menjacnica.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import menjacnica.model.Valuta;

public class ValutaUI {
	
	public static ArrayList<Valuta> sveValute = new ArrayList<>();
 
	public static void prikaziSveValute() {
		for(Valuta v : sveValute) {
			System.out.println(v.toString());
		}
	}
	
	public static Valuta nadjiValutuPoSifri(String sifra) {
		Valuta obj = null; 
		for(Valuta v : sveValute) {
			
			if(v.getOznakaValute().equals(sifra)) {
				obj = v; 
				break; 
			}
		}
		return obj; 
	}
	
	public static void ucitajValuteSaFajla(File document) throws IOException{
		if(document.exists()) {
			
			BufferedReader in = new BufferedReader(new FileReader(document));
			
			in.mark(1);
			if(in.read() != '\ufeff'){
				in.reset();
			}
			
			String s2; 
			while((s2 = in.readLine()) != null) {
				sveValute.add(new Valuta(s2));
			}
			in.close();
			
		} else {
			System.out.println("Ne postoji fajl.");
		}
	}
}
