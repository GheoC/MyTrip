package com.sci.Trip.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "trips_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripId;

    @Column(name = "name")
    @NotBlank
    private String tripName;

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "description")
    private String description;

    @Column(name = "sharable")
    private boolean sharable;

    @Transient
    private List<Picture> pictureList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }
}
