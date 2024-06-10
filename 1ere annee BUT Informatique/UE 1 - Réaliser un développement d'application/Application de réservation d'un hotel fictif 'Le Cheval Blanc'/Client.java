import java.util.ArrayList;

public class Client {
	private String civilite;
	private String nom;
	private String prenom;
	private String adresse1;
	private String adresse2;
	private String codePostal;
	private String ville;
	private String no_tel;
	private String mail;
	private int no_client;
	static int nb_clients = 0;
	private ArrayList<Reservation> lesResa;
	
	public Client(String civilite, String nom, String prenom, String adresse1, String adresse2, String codePostal,
			String ville, String no_tel, String mail) {
		Client.nb_clients++;
		this.civilite = civilite;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse1 = adresse1;
		this.adresse2 = adresse2;
		this.codePostal = codePostal;
		this.ville = ville;
		this.no_tel = no_tel;
		this.mail = mail;
		this.no_client = nb_clients;
		this.lesResa = new ArrayList<Reservation>();
	}
	
	@Override
	public String toString() {
		return "Client [civilite=" + civilite + ", nom=" + nom + ", prenom=" + prenom + ", adresse1=" + adresse1
				+ ", adresse2=" + adresse2 + ", codePostal=" + codePostal + ", ville=" + ville + ", no_tel=" + no_tel
				+ ", mail=" + mail + ", no_client=" + no_client + "]";
	}

	
	public void affiche()
	{
		System.out.println(this.toString());
	}
	
	public void listerResa() {
        for (Reservation resa : lesResa) {
        		resa.affiche();
        }
    }

	public ArrayList<Reservation> getLesResa() {
		return lesResa;
	}
	
	public void ajoutResa(Reservation r)
	{
		if(r!=null && !lesResa.contains(r))
		{
			lesResa.add(r);
		}
	}

	public void setLesResa(ArrayList<Reservation> lesResa) {
		this.lesResa = lesResa;
	}



	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse1() {
		return adresse1;
	}

	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}

	public String getAdresse2() {
		return adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getNo_tel() {
		return no_tel;
	}

	public void setNo_tel(String no_tel) {
		this.no_tel = no_tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getNo_client() {
		return no_client;
	}

	public void setNo_client(int no_client) {
		this.no_client = no_client;
	}

	public static int getNb_clients() {
		return nb_clients;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse1 == null) ? 0 : adresse1.hashCode());
		result = prime * result + ((adresse2 == null) ? 0 : adresse2.hashCode());
		result = prime * result + ((civilite == null) ? 0 : civilite.hashCode());
		result = prime * result + ((codePostal == null) ? 0 : codePostal.hashCode());
		result = prime * result + ((lesResa == null) ? 0 : lesResa.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + no_client;
		result = prime * result + ((no_tel == null) ? 0 : no_tel.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((ville == null) ? 0 : ville.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (adresse1 == null) {
			if (other.adresse1 != null)
				return false;
		} else if (!adresse1.equals(other.adresse1))
			return false;
		if (adresse2 == null) {
			if (other.adresse2 != null)
				return false;
		} else if (!adresse2.equals(other.adresse2))
			return false;
		if (civilite == null) {
			if (other.civilite != null)
				return false;
		} else if (!civilite.equals(other.civilite))
			return false;
		if (codePostal == null) {
			if (other.codePostal != null)
				return false;
		} else if (!codePostal.equals(other.codePostal))
			return false;
		if (lesResa == null) {
			if (other.lesResa != null)
				return false;
		} else if (!lesResa.equals(other.lesResa))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (no_client != other.no_client)
			return false;
		if (no_tel == null) {
			if (other.no_tel != null)
				return false;
		} else if (!no_tel.equals(other.no_tel))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}
	
}
