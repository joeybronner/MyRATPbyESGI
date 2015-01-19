package fr.esgi.ratp.objects;

public class Station {

	int idStation;
	String nameStation;
	String localisation;
	String typeLine;
	String latitude;
	String longitude;
	
	public Station() { }
	
	public Station (int idStation, String latitude, String longitude, 
			String nameStation, String localisation, String typeLine) {
		this.nameStation = nameStation;
		this.localisation = localisation;
		this.typeLine = typeLine;
		this.latitude = latitude;
		this.longitude = longitude;
		this.idStation = idStation;
	}
	
	public int getIDStation() {
		return this.idStation;
	}
	
	public String getNameStation() {
		return this.nameStation;
	}
	
	public String getLocalisation() {
		return this.localisation;
	}
	
	public String getTypeLine() {
		return this.typeLine;
	}
	
	public String getLatitude() {
		return this.latitude;
	}
	
	public String getLongitude() {
		return this.longitude;
	}
	
	public void setIDStation(int idStation) {
		this.idStation = idStation;
	}
	
	public void setNameStation(String nameStation) {
		this.nameStation = nameStation;
	}
	
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	
	public void setTypeLine(String typeLine) {
		this.typeLine = typeLine;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
