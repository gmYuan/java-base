package xdml;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyParam {


	@JsonProperty("title")
	private String title;
	@JsonProperty("body")
	private String body;
	@JsonProperty("assignees")
	private List<String> assignees;
	@JsonProperty("milestone")
	private Integer milestone;
	@JsonProperty("labels")
	private List<String> labels;
}
