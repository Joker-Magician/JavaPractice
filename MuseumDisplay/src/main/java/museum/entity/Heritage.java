package museum.entity;

public class Heritage {
    private int heritageId;
    private String name;
    private String category;
    private String region;
    private String description;
    private String imagePath;
    private int yearRecognized;
    private int createBy;
    private String createdAt;

    public Heritage() {}

    public Heritage(int heritageId, String name, String category, String region, String description, String imagePath, int yearRecognized, int createBy, String createdAt) {
        this.heritageId = heritageId;
        this.name = name;
        this.category = category;
        this.region = region;
        this.description = description;
        this.imagePath = imagePath;
        this.yearRecognized = yearRecognized;
        this.createBy = createBy;
        this.createdAt = createdAt;
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

    public int getCreateBy() {return createBy;}
    public void setCreateBy(int createBy) {this.createBy = createBy;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
}
