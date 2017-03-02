package menjacnica.ui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import menjacnica.model.KursnaLista;
import menjacnica.model.VrednostValute;
import menjacnica.utils.PomocnaKlasa;

public class TestUI {

	public static void ispisiOsnovneOpcije() {
		System.out.println("Menjacnica - Osnovne opcije:");
		System.out.println("\tOpcija broj 1 - Prikaz Svih Valuta");
		System.out.println("\tOpcija broj 2 - Prikaz Kursne Liste");
		System.out.println("\tOpcija broj 3 - Kreiraj Novu Kursnu Listu");
		System.out.println("\tOpcija broj 4 - Statistika vrednosti valute");
		System.out.println("\t\t ...");
		System.out.println("\tOpcija broj 0 - IZLAZ IZ PROGRAMA");
	}

	private static void update() throws IOException, ParseException {
		int odluka = -1;
		while (odluka != 0) {
			TestUI.ispisiOsnovneOpcije();
			System.out.print("opcija: ");
			odluka = PomocnaKlasa.ocitajCeoBroj();
			switch (odluka) {
			case 0:
				System.out.println("Izlaz iz programa");
				break;
			case 1:
                ValutaUI.prikaziSveValute();
				break;
			case 2:
				KursnaListaUI.prikaziMeniKL();
				break;
			case 3:
				KursnaListaUI.kreirajNovuListuMENU();
				break;
			case 4:
				VrednostValute.ispisiStatistiku();
				break;	
			default:
				System.out.println("Nepostojeca komanda");
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {

		String sP = System.getProperty("file.separator");
		
		KursnaLista kl = new KursnaLista();
		KursnaListaUI.sveKL.add(kl);
		
		File listaValuta = new File("."+sP+"data"+sP+"lista_valuta.csv");
		ValutaUI.ucitajValuteSaFajla(listaValuta);
		
		File listaVrednostiValuta = new File("." + sP + "data" + sP + "vrednosti_valuta.csv");
		VrednostValute.ucitajVrednostiSaFajla(listaVrednostiValuta);

		File listaKursnihListi = new File("."+sP+"data"+sP+"kursne_liste.csv");
		KursnaListaUI.ucitajVrednostiUFajl(listaKursnihListi);
		
		TestUI.update();
	}

}


































