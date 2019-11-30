package com.ngnt.models;

public class Position {
	public double lat;
	public double lng;
	public Position(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "Position [lat=" + lat + ", lng=" + lng + "]";
	}
	
	
}
