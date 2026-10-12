
abstract class Payment {

    protected double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    abstract void pay();

    void pay(double amount) {
        System.out.println("Payment amount: " + amount);
    }

    void pay(double amount, String method) {
        System.out.println(method + " payment: " + amount);
    }
}

interface Refundable {
    void refund(double amount);
}

class CardPayment extends Payment implements Refundable {

    CardPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Card payment completed successfully: " + amount);
    }

    @Override
    public void refund(double amount) {
        System.out.println("Card refund processed: " + amount);
    }
}

class UPIPayment extends Payment implements Refundable {

    UPIPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Paid " + amount + " using UPI");
    }

    @Override
    public void refund(double amount) {
        System.out.println("UPI refund processed: " + amount);
    }
}

class CashPayment extends Payment {

    CashPayment(double amount) {
        super(amount);
    }

    @Override
    void pay() {
        System.out.println("Paid " + amount + " using Cash");
    }
}

public class PaymentDemo {

    public static void main(String[] args) {

        Payment card = new CardPayment(1500);
        Payment upi = new UPIPayment(800);
        Payment cash = new CashPayment(300);

        card.pay();
        upi.pay();
        cash.pay();

        System.out.println();

        card.pay(2000);
        upi.pay(500, "UPI");

        System.out.println();

        Refundable refund = new CardPayment(1500);
        refund.refund(500);
    }
}
