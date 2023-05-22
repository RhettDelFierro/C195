package rhettdelfierro.c195.models;

public class MonthlyTypeReport {
    private String type;
    private String month;
    private int count;

    public MonthlyTypeReport(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    /**
     * Returns the type.
     * @return a String representing the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type a String representing the type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the month.
     * @return a String representing the month.
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month.
     * @param month a String representing the month.
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Returns the count.
     * @return an integer representing the count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count.
     * @param count an integer representing the count.
     */
    public void setCount(int count) {
        this.count = count;
    }
}
