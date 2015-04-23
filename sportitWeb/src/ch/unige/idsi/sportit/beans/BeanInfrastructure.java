package ch.unige.idsi.sportit.beans;

public class BeanInfrastructure {
	
	String nomInfra;
	double latitude;
	double longitude;
	
	public BeanInfrastructure(){
		
	}
	
	public BeanInfrastructure(String nomInfra, double latitude, double longitude){
		this.nomInfra = nomInfra;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setnomInfra( String nomInfra ) {
		this.nomInfra = nomInfra;
	}
	
	public String getnomInfra() {
		return nomInfra;
	}
	
	public void setlatitude( double latitude ) {
		this.latitude = latitude;
	}
	
	public double getlatitude() {
		return latitude;
	}
	
	public void setlongitude( double longitude ) {
		this.longitude = longitude;
	}
	
	public double getlongitude() {
		return longitude;
	}

}
