package thanhle.insider.utility;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

public class JsonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectNode toJsonObject(String jsonString) {
		try {
			JsonNode node = mapper.readTree(jsonString);
			if (node.isObject()) {
				return (ObjectNode) node;
			} else {
				throw new IllegalArgumentException("Provided JSON is not an object");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON string to ObjectNode", e);
		}
	}

	public static String toJsonString(ObjectNode node) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize ObjectNode to string", e);
		}
	}
	
	public static String toJsonString(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize Object to string", e);
		}
	}
}
