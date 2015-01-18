package fr.esgi.ratp.objects;

public class Station {

	String nameLine;
	String localisation;
	String typeLine;
	String latitude;
	String longitude;
	int idStation;
	
	
	public Station() { }
	
	public Station (int idStation, String latitude, String longitude, 
			String nameLine, String localisation, String typeLine) {
		this.nameLine = nameLine;
		this.localisation = localisation;
		this.typeLine = typeLine;
		this.latitude = latitude;
		this.longitude = longitude;
		this.idStation = idStation;
	}
	
	protected int getidStation() {
		return this.idStation;
	}
	
	public String getNameLine() {
		return this.nameLine;
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
	
	protected void setIDLine(int idStation) {
		this.idStation = idStation;
	}
	
	public void setNameLine(String nameLine) {
		this.nameLine = nameLine;
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
