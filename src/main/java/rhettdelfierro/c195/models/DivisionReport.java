package rhettdelfierro.c195.models;

public class DivisionReport {
    private String division;
    private int count;

    public DivisionReport(String division, int count) {
        this.division = division;
        this.count = count;
    }

    /**
     * Returns the division.
     * @return a String representing the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division.
     * @param division a String representing the division.
     */
    public void setDivision(String division) {
        this.division = division;
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
