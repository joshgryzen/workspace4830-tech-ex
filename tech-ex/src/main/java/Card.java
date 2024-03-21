public class Card {
    private String color;
    private String type;
    private int cmc;
    private String name;
    private int quantity;

    // Constructor
    public Card(String color, String type, int cmc, String name, int quantity) {
        this.color = color;
        this.type = type;
        this.cmc = cmc;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
