package com.example.sprintproject.model;

import java.util.Date;

public class Accommodation implements HasID {
    private String id;
    private String hotelName;
    private String location;
    private Date checkInDate;
    private Date checkOutDate;
    private String numsRooms;
    private String roomType;

    public Accommodation() {
    }

    public Accommodation(String hotelName, Date checkInDate,
                         Date checkOutDate, String location, String numsRooms, String roomType) {
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.location = location;
        this.numsRooms = numsRooms;
        this.roomType = roomType;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getNumsRooms() {
        return numsRooms;
    }

    public void setNumsRooms(String numsRooms) {
        this.numsRooms = numsRooms;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
