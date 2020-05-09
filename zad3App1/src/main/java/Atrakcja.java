import java.io.Serializable;

public class Atrakcja implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nazwa;
	private int dlugoscwycieczki;

	public Atrakcja(String nazwa, int dlugoscwycieczki) {
		this.nazwa = nazwa;
		this.dlugoscwycieczki = dlugoscwycieczki;
	}

	public String getNazwa() {
		return nazwa;
	}
	
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public int getDlugoscwycieczki() {
		return dlugoscwycieczki;
	}
	
	public void setDlugoscwycieczki(int dlugoscwycieczki) {
		this.dlugoscwycieczki = dlugoscwycieczki;
	}
	
	@Override
	public String toString(){
		return "Atrakcja " + nazwa + " dlugosc wycieczki: " + dlugoscwycieczki;
	}
}
