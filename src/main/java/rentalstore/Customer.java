package rentalstore;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private String name;
    private Vector rentals = new Vector();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg){
        rentals.addElement(arg);
    }

    public String getName() {
        return name;
    }

    public String statement(){
        Enumeration rentals = this.rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while(rentals.hasMoreElements()){
            Rental each = (Rental) rentals.nextElement();
            result += "\t" + each.getMovie().getTitle() + "\t" + getEachCharge(each) + "\n";
        }
        result += "Amount owed is " + getTotalAmount() + "\n";
        result += "You earned " + getFrequentRenterPoints() + " frequent renter points";
        return result;
    }

    public String htmlStatement() {
        Enumeration rentals = this.rentals.elements();
        String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            result += each.getMovie().getTitle() + ":\t" + getEachCharge(each) + "<BR>\n";
        }
        result += "<P>You owe<EM>" + getTotalAmount() + "</EM><P>\n";
        result += "On this rental you earned <EM>" + getFrequentRenterPoints() +
                "</EM> frequent renter points<P>";
        return result;
    }


    public double getTotalAmount(){
        Enumeration rentals = this.rentals.elements();
        double totalAmount = 0;
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            totalAmount += getEachCharge(each);
        }
        return totalAmount;
    }

    public int getFrequentRenterPoints(){
        Enumeration rentals = this.rentals.elements();
        int frequentRenterPoints = 0;
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            frequentRenterPoints ++;
            if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDayRented() > 1){
                frequentRenterPoints ++;
            }
        }
        return frequentRenterPoints;
    }

    public double getEachCharge(Rental each){
        double eachCharge = 0;
        switch (each.getMovie().getPriceCode()){
            case Movie.REGULAR:
                eachCharge += 2;
                if(each.getDayRented() > 2){
                    eachCharge+=(each.getDayRented() - 2) * 1.5;
                }
                break;
            case Movie.NEW_RELEASE:
                eachCharge+=each.getDayRented()*3;
                break;
            case Movie.CHILDRENS:
                eachCharge+=1.5;
                if(each.getDayRented() > 3){
                    eachCharge += (each.getDayRented() -3)*1.5;
                }
                break;
        }
        return eachCharge;
    }
}
