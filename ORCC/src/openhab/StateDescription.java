/**
 * 
 */
package openhab;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author reh4512
 *
 */
public class StateDescription {

	@SerializedName("minimum")
	@Expose
	private Integer minimum;
	@SerializedName("maximum")
	@Expose
	private Integer maximum;
	@SerializedName("step")
	@Expose
	private Integer step;
	@SerializedName("pattern")
	@Expose
	private String pattern;
	@SerializedName("readOnly")
	@Expose
	private Boolean readOnly;
	@SerializedName("options")
	@Expose
	private List<Option> options = new ArrayList<Option>();

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

}
