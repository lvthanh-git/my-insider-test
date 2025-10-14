package thanhle.insider.dataobject;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import io.restassured.response.ResponseBody;
import tools.jackson.databind.ObjectMapper;

public class Pet {
	private int id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private List<Tag> tags;
	private String status;
	
	
	public Pet() {}
	
	public static Pet fromResponse(ResponseBody<?> body) {
        try {
            String json = body.asString();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Pet.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Pet from response", e);
        }
    } 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void initData() {
        Faker faker = new Faker();
        Random random = new Random();

        this.id = Math.abs(random.nextInt(100000)); 
        this.name = faker.dog().name();
        this.status = faker.options().option("available", "pending", "sold");

        // Create a random category
        Category cat = new Category();
        cat.setId(random.nextInt(10));
        cat.setName(faker.animal().name());
        this.category = cat;

        // Add a fake photo URL
        this.photoUrls = Collections.singletonList(faker.internet().avatar());

        // Create a random tag
        Tag tag = new Tag();
        tag.setId(random.nextInt(100));
        tag.setName(faker.color().name());
        this.tags = Collections.singletonList(tag);
    }
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

}
