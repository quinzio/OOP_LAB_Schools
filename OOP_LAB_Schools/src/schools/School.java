package schools;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class School {
    private String name;
    private String code;
    private int grade;
    private String description;
    private Map<Integer, Branch> branches = new TreeMap<>();
    private Region region;

    public School(String name, String code, int grade, String description) {
	this.name = name;
	this.code = code;
	this.grade = grade;
	this.description = description;
    }

    public String getName() {
	return name;
    }

    public String getCode() {
	return code;
    }

    public int getGrade() {
	return grade;
    }

    public String getDescription() {
	return description;
    }

    public Collection<Branch> getBranches() {
	return branches.values();
    }

    public void setRegion(Region region) {
	this.region = region;
    }

    public void addBranch(Branch branch) {
	branches.put(branch.getCode(), branch);
    }

}
