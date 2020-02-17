package testDataInsertion;
import java.util.List;

public class ZipMetadataMap {
	
	private int scenarioId;
	private String zip;
	private List<String> metadata;
	
	public ZipMetadataMap() {
		
	}
	
	public ZipMetadataMap(String zip, List<String> metadata) {
		this.zip = zip;
		this.metadata = metadata;
	}
	
	

	public int getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(int scenarioId) {
		this.scenarioId = scenarioId;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<String> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<String> metadata) {
		this.metadata = metadata;
	}
	
	
	

}
