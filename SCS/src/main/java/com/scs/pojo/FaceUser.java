package com.scs.pojo;

public class FaceUser {
    private String id;
    private String name;
    private String classes;
    private String profession;
    public FaceUser(){}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", classes='" + classes + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClasses() {
        return classes;
    }

    public String getProfession() {
        return profession;
    }

    public FaceUser(String id, String name, String classes, String profession) {
        this.id = id;
        this.name = name;
        this.classes = classes;
        this.profession = profession;
    }
}
