package com.sci.Trip.Model;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue()
    @Column(name = "picture_id")
    private int pictureId;

    @Column(name = "trips_id")
    private int tripId;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Lob
    @Column(name = "picture_base64")
    private String pictureBase64;



    public Picture() {
    }

    public Picture(int tripId, byte[] picture) {
        this.tripId = tripId;
        this.picture = picture;
    }

    public int getPictureId() {
        return pictureId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String pictureBase64) {
        this.pictureBase64 = pictureBase64;
    }
}
