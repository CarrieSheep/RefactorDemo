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
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = this.rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while(rentals.hasMoreElements()){

            Rental each = (Rental) rentals.nextElement();

            double thisAmount = getEachCharge(each);

            //add frequent renter points
            frequentRenterPoints ++;
            //add bonus for a two day new release rental
            if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDayRented() > 1){
                frequentRenterPoints ++;
            }

            //show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        //add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    public String htmlStatement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = this.rentals.elements();
        String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";
        while (rentals.hasMoreElements()) {

            Rental each = (Rental) rentals.nextElement();

            double thisAmount = getEachCharge(each);

            //add frequent renter points
            frequentRenterPoints ++;
            //add bonus for a two day new release rental
            if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDayRented() > 1){
                frequentRenterPoints ++;
            }

            //show figures for this rental
            result += each.getMovie().getTitle() + ":\t" + String.valueOf(thisAmount) + "<BR>\n";
            totalAmount += thisAmount;
        }
        //add footer lines
        result += "<P>You owe<EM>" + totalAmount + "</EM><P>\n";
        result += "On this rental you earned <EM>" + frequentRenterPoints +
                "</EM> frequent renter points<P>";
        return result;
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
