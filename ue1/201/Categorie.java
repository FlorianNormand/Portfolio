
public class Categorie {
	private int capacite;
	private Double tarif_de_base;
	private String libelle;
	
	public Categorie(int capacite, Double tarif_de_base, String libelle) {
		this.capacite = capacite;
		this.tarif_de_base = tarif_de_base;
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Categorie [capacite=" + capacite + ", tarif_de_base=" + tarif_de_base + ", libelle=" + libelle + "]";
	}
	
	public void affiche()
	{
		System.out.println(this.toString());
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public Double getTarif_de_base() {
		return tarif_de_base;
	}

	public void setTarif_de_base(Double tarif_de_base) {
		this.tarif_de_base = tarif_de_base;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}	
}