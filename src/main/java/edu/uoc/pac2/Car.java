package edu.uoc.pac2;

import java.time.LocalDate;
import java.util.Locale;

public class Car {
    private static final double VAT_SPAIN=21;
    private static final double VAT_FRANCE=21.5;
    private int id = getNextId();
    private static int nextId =0;
    private String make;
    private String model;
    private int licenseYear;
    private char fuel='P';
    private String licensePlate;
    private double price;

    public Car() {
        this.licensePlate="0000CDV";
        setPrice(10000);
        this.make="Lorem";
        this.model="IPSUM";
        this.licenseYear=2000;
        nextId++;
        getId();
    }

    public Car(String make,String model,int licenseYear,char fuel, String licensePlate, double price) {
        setLicensePlate(licensePlate);
        setPrice(price);
        this.licensePlate=getLicensePlate();
        this.price=getPrice();
        this.fuel=fuel;
        this.make=make;
        this.model=model;
        this.licenseYear=licenseYear;
        getId();
    }

    public int getId() {
        id=nextId;
        return id;
    }

    private void setId() {
        // this.id=id;
    }

    public static int getNextId() {
        return nextId;
    }

    private void setNexId(int nextId) {
        nextId++;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
       String lowerCase="";
        if(make.length()<15){
            for (int i=0;i<make.length()+1;i++) {
               lowerCase = make.substring(0, i).toLowerCase() + make.substring(i);
            }
            this.make=make.substring(0,1).toUpperCase()+lowerCase.substring(1);
        }else System.out.println("[ERROR] Car's make cannot be longer than 15 characters");
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if(model.length()<20) this.model = model.toUpperCase();
        else System.out.println("[ERROR] Car's model cannot be longer than 20 characters");
    }

    public int getLicenseYear() {
        return licenseYear;
    }

    public void setLicenseYear(int licenseYear) {
       if(licenseYear>2000&&licenseYear<LocalDate.now().getYear()) this.licenseYear = licenseYear;
       else System.out.println("[ERROR] Car's license year must be in range [2000, current year]");
    }

    public char getFuel() {
        return fuel;
    }

    public void setFuel(char fuel) {
        switch (fuel) {
            case 'P', 'H', 'D', 'E' -> this.fuel = fuel;
            default -> System.out.print("[ERROR] Car's fuel is incorrect");
        }
    }

    public boolean getWarranty(){
        int year=LocalDate.now().getYear();
        System.out.println(getLicenseYear());
        return year - licenseYear <= 5;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        if(licensePlate.toUpperCase().matches("^[0-9]{4}[A-Z]{3}$")){
            this.licensePlate = licensePlate;
        }
        else if (licensePlate.toUpperCase().matches("^[A-Z]{2}[-][0-9]{3}[-][A-Z]{2}$")){
            this.licensePlate = licensePlate;

        }else System.out.println("[ERROR] Car's license plate pattern is incorrect");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
       this.price = price;
        char symbol=getLicensePlate().charAt(2);
        if(price>0){
            if (symbol=='-')this.price+=price*VAT_FRANCE/100;
            else this.price+=price*VAT_SPAIN/100;
        }else System.out.println("[ERROR] Car's price must be greater than 0");
    }
}
