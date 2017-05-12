package schools;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Municipality {
    	private String name;
    	private String province;
    	private Optional<Community> community;
    	private Map<Integer, Branch> branches = new TreeMap<>();
    	private Region region;

	public Municipality(String name, String province, Optional<Community> community) {
	    this.name = name;
	    this.province = province;
	    this.community = community;
	}
	public String getName() {
		return name;
}
	public String getProvince() {
		return province;
	}

	public Collection<Branch> getBranches() {
		
		return branches.values();
	}

	public Optional<Community> getCommunity() {
		return community;
	}
	public void setRegion(Region region) {
	    this.region = region;
	}	
	public void addBranch(Branch branch) {
	    branches.put(branch.getCode(), branch);
	    
	    
	}
	
	
}
