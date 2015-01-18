package fr.esgi.ratp.objects;

public class Line {

	String nameLine;
	String departureLine;
	String arrivalLine;
	String typeLine;
	int idLine;
	
	Line() { }
	
	Line (String nameLine, String departureLine, String arrivalLine, 
			String typeLine, int idLine) {
		this.nameLine = nameLine;
		this.departureLine = departureLine;
		this.arrivalLine = arrivalLine;
		this.typeLine = typeLine;
		this.idLine = idLine;
	}
	
	protected int getIDLine() {
		return this.idLine;
	}
	
	public String getNameLine() {
		return this.nameLine;
	}
	
	public String getDepartureLine() {
		return this.departureLine;
	}
	
	public String getArrivalLine() {
		return this.arrivalLine;
	}
	
	public String getTypeLine() {
		return this.typeLine;
	}
	
	protected void setIDLine(int idLine) {
		this.idLine = idLine;
	}
	
	public void setNameLine(String nameLine) {
		this.nameLine = nameLine;
	}
	
	public void setDepartureLine(String departureLine) {
		this.departureLine = departureLine;
	}
	
	public void setArrivalLine(String arrivalLine) {
		this.arrivalLine = arrivalLine;
	}
	
	public void setTypeLine(String typeLine) {
		this.typeLine = typeLine;
	}
	
}
