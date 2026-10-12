
class InsufficientStockException extends Exception {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}

class OrderProcessor {

    private int stock = 10;

    public void processOrder(int quantity)
            throws InsufficientStockException {

        try {
            if (quantity <= 0) {
                throw new InvalidQuantityException(
                        "Quantity must be greater than zero");
            }

            if (quantity > stock) {
                throw new InsufficientStockException(
                        "Requested quantity exceeds available stock");
            }

            stock = stock - quantity;

            System.out.println("Order placed successfully");
            System.out.println("Ordered quantity: " + quantity);
            System.out.println("Remaining stock: " + stock);

        } catch (InvalidQuantityException e) {
            throw e;

        } catch (InsufficientStockException e) {
            throw new InsufficientStockException(
                    "Order processing failed", e);

        } finally {
            System.out.println("Audit: Order processing attempt completed");
            System.out.println();
        }
    }

    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

      int[] quantities = {-2};

        for (int quantity : quantities) {

            try {
                processor.processOrder(quantity);

            }
            catch (InsufficientStockException |
                InvalidQuantityException e) {

            e.printStackTrace();
        }
        }
    }
}
