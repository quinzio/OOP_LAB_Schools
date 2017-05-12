package schools;

public class Branch {
    private int code;
    private Municipality municipality;
    private String address;
    private int cap;
    private School school;
    private Region region;

    public Branch(int code, Municipality municipality, String address, int cap, School school) {
	this.code = code;
	this.municipality = municipality;
	this.address = address;
	this.cap = cap;
	this.school = school;
    }

    public int getCode() {
	return code;
    }

    public String getAddress() {
	return address;
    }

    public int getCAP() {
	return cap;
    }

    public Municipality getMunicipality() {
	return municipality;
    }

    public School getSchool() {
	return school;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
