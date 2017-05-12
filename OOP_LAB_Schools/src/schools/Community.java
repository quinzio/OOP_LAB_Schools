package schools;

import java.util.Collection;

public class Community {
    	private String name;
    	private Type type;
    	private Region region;
	
	public Community(String name, Type type) {
	    this.name = name;
	    this.type = type;
	}

	public enum Type { MONTANA, COLLINARE };
	
	public String getName() {
		return name;
	}
	
	public Type getType(){
		return type;
	}

	public Collection<Municipality> getMunicipalities() {
		return region.getMunicipalies();
	}
	public void setRegion(Region region) {
	    this.region = region;
	}	

	
}
