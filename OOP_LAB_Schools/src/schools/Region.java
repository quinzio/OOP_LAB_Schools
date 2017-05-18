package schools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

// Hint: to write compact stream expressions
// you can include the following
//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class Region {
	private String name;
	Map<String, Community> communities = new TreeMap<>();
	Map<String, Municipality> municipalities = new TreeMap<>();
	Map<String, School> schools = new TreeMap<>();
	Map<Integer, Branch> branches = new TreeMap<>();

	public Region(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<School> getSchools() {
		return schools.values();
	}

	public Collection<Community> getCommunities() {
		return communities.values();
	}

	public Collection<Municipality> getMunicipalies() {
		return municipalities.values();
	}

	// factory methods

	public Community newCommunity(String name, Community.Type type) {
		Community c = new Community(name, type);
		communities.put(name, c);
		c.setRegion(this);
		return c;
	}

	public Municipality newMunicipality(String nome, String provincia) {
		return newMunicipality(nome, provincia, null);
	}

	public Municipality newMunicipality(String nome, String provincia,
			Community comunita) {
		Optional<Community> com = Optional.ofNullable(comunita);
		Municipality m = new Municipality(nome, provincia, com);
		m.setRegion(this);
		municipalities.put(nome, m);
		return m;
	}

	public School newSchool(String name, String code, int grade,
			String description) {
		School s = new School(name, code, grade, description);
		s.setRegion(this);
		schools.put(name, s);
		return s;
	}

	public Branch newBranch(int regionalCode, Municipality municipality,
			String address, int zipCode, School school) {
		Branch b = new Branch(regionalCode, municipality, address, zipCode,
				school);
		b.setRegion(this);
		branches.put(regionalCode, b);
		school.addBranch(b);
		municipality.addBranch(b);
		return b;
	}

	public void readData(String url) throws IOException {
		// Hint: use LineUtils.loadLinesUrl(url) to load the CSV lines from a
		// URL
		// String url =
		// "http://oop.polito.it/svn/OOPLabs/master/Data/schools.csv";
		String strempty = "";
		Community c = null;
		Municipality m = null;
		School sch = null;
		Branch b = null;
		List<String> lines = it.polito.utility.LineUtils.loadLinesUrl(url);
		lines.remove(0);
		int nrows = lines.size();
		int ncols = lines.get(0).split(",").length;
		System.out
				.println("Loaded " + nrows + " lines of " + ncols + " columns");

		for (String s : lines) {
			String[] words = s.split(",", 11);
			if (!words[9].equals(strempty)) {
				c = new Community(words[9], Community.Type.COLLINARE);
				if (!this.communities.containsKey(words[9]))
					this.communities.put(words[9], c);
				else
					c = this.communities.get(words[9]);
			}
			if (!words[10].equals(strempty)) {
				c = new Community(words[10], Community.Type.MONTANA);
				if (this.communities.containsKey(words[10]))
					this.communities.put(words[10], c);
				else
					c = this.communities.get(words[10]);
			}
			
			if (this.municipalities.containsKey(words[1]))
				m = this.municipalities.get(words[1]);
			else
				m = newMunicipality(words[1], words[0], c);

			String grstr = words[2].split(" - ")[0];
			int grade = Integer.valueOf(grstr);
			
			if (this.schools.containsKey(words[6]))
				sch = this.schools.get(words[6]);
			else
				sch = newSchool(words[6], words[5], grade, words[3]);

			b = newBranch(Integer.valueOf(words[4]), m, words[7],
					Integer.valueOf(words[8]), sch);

		}

	}

	public Map<String, Long> countSchoolsPerDescription() {
		return schools.values().stream().collect(Collectors
				.groupingBy(School::getDescription, Collectors.counting()));
	}

	public Map<String, Long> countBranchesPerMunicipality() {
		return branches.values().stream().collect(Collectors.groupingBy(
				b -> b.getMunicipality().getName(), Collectors.counting()));
	}

	public Map<String, Double> averageBranchesPerMunicipality() {
		return municipalities.values().stream()
				.collect(Collectors.groupingBy(Municipality::getProvince,
						Collectors.mapping(m -> m.getBranches().size(),
								Collectors.averagingInt(v -> v))));
	}

	public Collection<String> countSchoolsPerMunicipality() {
		return branches.values().stream()
				.collect(Collectors.groupingBy(Branch::getMunicipality,
						Collectors.counting()))
				.entrySet().stream()
				.map(e -> e.getValue().toString() + " - "
						+ e.getKey().toString() + "\n")
				.collect(Collectors.toList());
	}

	public List<String> countSchoolsPerCommunity() {
		class CommSch {
			public School school;
			public Community community;
			public CommSch(School school, Community community) {
				this.school = school;
				this.community = community;
			}
		};
		return branches.values().stream().map(b -> {
			return new CommSch(b.getSchool(),
					b.getMunicipality().getCommunity().get());
		}).distinct()
				.collect(Collectors.groupingBy(cs -> cs.community,
						Collectors.counting()))
				.entrySet().stream()
				.map(e -> e.getValue().toString() + " - "
						+ e.getKey().toString() + "\n")
				.collect(Collectors.toList());

	}

}
