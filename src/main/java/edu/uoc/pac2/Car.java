package edu.uoc.pac2;

import java.time.LocalDate;

public class Car {


    private static final double VAT_SPAIN=21;
    private static final double VAT_FRANCE=21.5;
    private int id;
    private static int nextId =0;
    private String make;
    private String model;
    private int licenseYear;
    private char fuel='P';
    private String licensePlate;
    private double price;

    public Car() {
       this.price=10000;
       this.licensePlate="0000CDV";
       this.fuel='P';
       this.make="Lorem";
       this.model="IPSUM";
       this.licenseYear=2000;
       nextId++;


    }

    public Car(String make,String model,int licenseYear,char fuel, String licensePlate, double price) {
        this.price=price;
        this.licensePlate=licensePlate;
        this.fuel=fuel;
        this.make=make;
        this.model=model;
        this.licenseYear=licenseYear;
        setNexId(nextId);

    }

    public int getId() {
        id=nextId;
        return id;

    }

    private void setId(int id) {
        this.id = id;

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
        if(make.length()<15){
            this.make = make.substring(0,1).toUpperCase()+make.substring(1);
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
        switch (fuel){
            case 'P':
            case 'H':
            case 'D':
            case 'E':
                this.fuel=fuel;
            break;
            default:System.out.print("[ERROR] Car's fuel is incorrect");
            break;

        }

    }
//falla:
    public boolean getWarranty(){
        int year=LocalDate.now().getYear();
        if (year-licenseYear >5){
            return  false;
        }else return true;

    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getPrice() {
        price+=price*VAT_SPAIN/100;
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
