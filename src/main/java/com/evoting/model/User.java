package com.evoting.model;


import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique = true)
    @Size(min = 8, max = 20)
    private String username;

    @Column(length = 100)
    private String password;

    @NotNull
    private Date birthday;

    @NotNull
    private String gender;

    @NotNull
    private String ic;

    @NotNull
    private String address;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="statesId")
    @NotNull
    private States states;

    @Lob
    @NotNull
    private byte[] photo;

    public User() {
    }

    public User(String firstName, String lastName, String username, String password, Date birthday, String gender, String ic, String address, Role role, String email,String phone, States states, byte[] photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
       this.password=password;
        this.birthday = birthday;
        this.gender = gender;
        this.ic = ic;
        this.address = address;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.states = states;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
