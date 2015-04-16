package ch.unige.idsi.sportit.beans;

public class BeanInfrastructure {
	
	String nomInfra;
	String latitude;
	String longitude;
	
	public void setnomInfra( String nomInfra ) {
		this.nomInfra = nomInfra;
	}
	
	public String getnomInfra() {
		return nomInfra;
	}
	
	public void setlatitude( String latitude ) {
		this.latitude = latitude;
	}
	
	public String getlatitude() {
		return latitude;
	}
	
	public void setlongitude( String longitude ) {
		this.longitude = longitude;
	}
	
	public String getlongitude() {
		return longitude;
	}

}
