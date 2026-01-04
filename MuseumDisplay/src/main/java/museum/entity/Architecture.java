package museum.entity;

import java.time.LocalDateTime;

public class Architecture {
    private int architectureID;
    private String name;
    private String dynasty;
    private String location;
    private String type;
    private String description;
    private String imagePath;
    private int yearBuilt;
    private int createdBy;
    private LocalDateTime createdAt;

    public Architecture() {}

    public Architecture(int architectureID, String name, String dynasty, String location, String type, String description, String imagePath, int yearBuilt, int createdBy, LocalDateTime createdAt) {
        this.architectureID = architectureID;
        this.name = name;
        this.dynasty = dynasty;
        this.location = location;
        this.type = type;
        this.description = description;
        this.imagePath = imagePath;
        this.yearBuilt = yearBuilt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getArchitectureID() {return architectureID;}
    public void setArchitectureID(int architectureID) {this.architectureID = architectureID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDynasty() {return dynasty;}
    public void setDynasty(String dynasty) {this.dynasty = dynasty;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getImagePath() {return imagePath;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    public int getYearBuilt() {return yearBuilt;}
    public void setYearBuilt(int yearBuilt) {this.yearBuilt = yearBuilt;}

    public int getCreatedBy() {return createdBy;}
    public void setCreatedBy(int createdBy) {this.createdBy = createdBy;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
