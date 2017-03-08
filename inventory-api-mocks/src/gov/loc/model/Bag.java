package gov.loc.model;

import java.io.Serializable;

public class Bag implements Serializable{

	static final long serialVersionUID = 1L;

	private java.lang.String bagId;
	private java.lang.String numCopy;

	public Bag() {
	}

	public java.lang.String getBagId() {
		return this.bagId;
	}

	public void setBagId(java.lang.String bagId) {
		this.bagId = bagId;
	}

	public java.lang.String getNumCopy() {
		return this.numCopy;
	}

	public void setNumCopy(java.lang.String numCopy) {
		this.numCopy = numCopy;
	}

	public Bag(java.lang.String bagId, java.lang.String numCopy) {
		this.bagId = bagId;
		this.numCopy = numCopy;
	}

}
