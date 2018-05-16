/**
 * 
 */
package openhab;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Sven Rehfuﬂ
 *
 */
public class RestItem {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("label")
	@Expose
	private String label;
	@SerializedName("category")
	@Expose
	private String category;
	@SerializedName("tags")
	@Expose
	private List<String> tags = new ArrayList<String>();
	@SerializedName("groupNames")
	@Expose
	private List<String> groupNames = new ArrayList<String>();
	@SerializedName("link")
	@Expose
	private String link;
	@SerializedName("state")
	@Expose
	private String state;
	@SerializedName("transformedState")
	@Expose
	private String transformedState;
	@SerializedName("stateDescription")
	@Expose
	private StateDescription stateDescription;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTransformedState() {
		return transformedState;
	}

	public void setTransformedState(String transformedState) {
		this.transformedState = transformedState;
	}

	public StateDescription getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(StateDescription stateDescription) {
		this.stateDescription = stateDescription;
	}

}
