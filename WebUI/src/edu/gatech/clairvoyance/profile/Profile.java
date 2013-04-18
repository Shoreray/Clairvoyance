package edu.gatech.clairvoyance.profile;

public abstract class Profile {
	
	public abstract String getName();
	
	public boolean equals(Object o){
		if(!(o instanceof Profile)){
			return false;
		}
		Profile p=(Profile)o;
		return p.getName().equals(this.getName());
	}
	

}
