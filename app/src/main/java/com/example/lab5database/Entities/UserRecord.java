package com.example.lab5database.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "userRecords")
public class UserRecord {
    @PrimaryKey
    @ColumnInfo(name="name")
    @NonNull
    public String name;

    @PrimaryKey
    @ColumnInfo(name="surname")
    @NonNull
    public String surname;

    @ColumnInfo(name="comment")
    public String comment;

    public UserRecord(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserRecord(){

    }

    public UserRecord(String name, String surname, String comment){
        this(name,surname);
        this.comment = comment;
    }

    public static UserRecord[] seedData() {
        return new UserRecord[] {
                new UserRecord("Nikolai", "Logoshin"),
                new UserRecord("Sanya", "Gavruk"),
                new UserRecord("Egor", "Vashkevich"),
                new UserRecord("Peter", "Parker"),
                new UserRecord("Tony", "Stark"),
                new UserRecord("Michael", "Jordan"),
                new UserRecord("Barack", "Obama"),
                new UserRecord("Vladimir", "Putin"),
        };
    }
}
