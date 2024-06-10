
public class Prestation {
	private String type;
	private Double prixHT;
	private String libelle;
	
	public Prestation(String type, Double prixHT, String libelle) {
		this.type = type;
		this.prixHT = prixHT;
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Prestation [type=" + type + ", prixHT=" + prixHT + ", libelle=" + libelle + "]";
	}
	
	public void affiche()
	{
		System.out.println(this.toString());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrixHT() {
		return prixHT;
	}

	public void setPrixHT(Double prixHT) {
		this.prixHT = prixHT;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}