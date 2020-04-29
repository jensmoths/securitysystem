package globalServer;

import java.util.Random;

public class User {
    private String firstName, surName, street, zipCode, city, country, userName, password;

    public User(String firstName, String surName, String street, String zipCode, String city, String country) {

        this.firstName = firstName;
        this.surName = surName;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        password = "";
        userName = "";
    }

    public User() {
        firstName = "";
        surName = "";
        street = "";
        zipCode = "";
        city = "";
        country = "";
        password = "";
        userName = "";
    }

    public User(String firstName, String surName, String street) {
        this.firstName = firstName;
        this.surName = surName;
        this.street = street;
        zipCode = "";
        city = "";
        country = "";
        password = "";
        userName = "";
    }

    public void generateLogInDetails() {
        Random rand = new Random();

        String characters = "123456789abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 8; i++) {
            password += characters.charAt(rand.nextInt(characters.length()));
        }

        String firstName = getFirstName().toLowerCase().replaceAll("\\s", "");
        String surName = getSurName().toLowerCase().replaceAll("\\s", "");
        String street = getStreet().toLowerCase().replaceAll("\\s", "");

        userName = street.substring(0, 3) + surName.substring(0, 2) + firstName.substring(0, 2);

        System.out.println(getUserName() + ", "+ getPassword());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        User malek = new User();
        malek.setFirstName("Ammar");
        malek.setSurName("Darwesh");
        malek.setStreet("Fläderbärsgatan 2");
        malek.generateLogInDetails();
        System.out.println(malek.getUserName() + ", "+ malek.getPassword());
    }
}
