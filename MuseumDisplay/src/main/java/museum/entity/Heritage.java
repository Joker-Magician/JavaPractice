package museum.entity;

import java.time.LocalDateTime;

public class Heritage {
    private int heritageId;
    private String name;
    private String category;
    private String region;
    private String description;
    private String imagePath;
    private int yearRecognized;
    private int createBy;
    private LocalDateTime createdAt;

    public Heritage() {}

    public Heritage(String name, String category, String region, String description, String imagePath, int yearRecognized) {
        this.name = name;
        this.category = category;
        this.region = region;
        this.description = description;
        this.imagePath = imagePath;
        this.yearRecognized = yearRecognized;
    }

    public int getHeritageId() {return heritageId;}
    public void setHeritageId(int heritageId) {this.heritageId = heritageId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public String getRegion() {return region;}
    public void setRegion(String region) {this.region = region;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getImagePath() {return imagePath;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public int getYearRecognized() {return yearRecognized;}
    public void setYearRecognized(int yearRecognized) {this.yearRecognized = yearRecognized;}

    public int getCreatedBy() {return createBy;}
    public void setCreatedBy(int createBy) {this.createBy = createBy;}

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
