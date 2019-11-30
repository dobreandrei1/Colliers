package com.ngnt.models;

public class Output {

	@Override
	public String toString() {
		return "Output [IDunic=" + IDunic + ", localitate=" + localitate + ", judet=" + judet + ", tipStrada="
				+ tipStrada + ", strada=" + strada + ", numar=" + numar + ", numarCamere=" + numarCamere
				+ ", suprafata=" + suprafata + ", etaj=" + etaj + ", impartire=" + impartire + ", anConstructie="
				+ anConstructie + ", finisaje=" + finisaje + ", lat=" + lat + ", lng=" + lng + "]";
	}

	public int getIDunic() {
		return IDunic;
	}

	public void setIDunic(int iDunic) {
		IDunic = iDunic;
	}

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getJudet() {
		return judet;
	}

	public void setJudet(String judet) {
		this.judet = judet;
	}

	public String getTipStrada() {
		return tipStrada;
	}

	public void setTipStrada(String tipStrada) {
		this.tipStrada = tipStrada;
	}

	public String getStrada() {
		return strada;
	}

	public void setStrada(String strada) {
		this.strada = strada;
	}

	public String getNumar() {
		return numar;
	}

	public void setNumar(String numar) {
		this.numar = numar;
	}

	public double getNumarCamere() {
		return numarCamere;
	}

	public void setNumarCamere(double numarCamere) {
		this.numarCamere = numarCamere;
	}

	public double getSuprafata() {
		return suprafata;
	}

	public void setSuprafata(double suprafata) {
		this.suprafata = suprafata;
	}

	public String getEtaj() {
		return etaj;
	}

	public void setEtaj(String etaj) {
		this.etaj = etaj;
	}

	public String getImpartire() {
		return impartire;
	}

	public void setImpartire(String impartire) {
		this.impartire = impartire;
	}

	public String getAnConstructie() {
		return anConstructie;
	}

	public void setAnConstructie(String anConstructie) {
		this.anConstructie = anConstructie;
	}

	public String getFinisaje() {
		return finisaje;
	}

	public void setFinisaje(String finisaje) {
		this.finisaje = finisaje;
	}

	public int IDunic;
	public String localitate;
	public String judet;
	public String tipStrada;
	public String strada;
	public String numar;
	public double numarCamere;
	public double suprafata;
	public String etaj;
	public String impartire;
	public String anConstructie;
	public String finisaje;
	public double lat;
	public double lng;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	

}
