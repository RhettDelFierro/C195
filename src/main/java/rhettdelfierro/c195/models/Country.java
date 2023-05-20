package rhettdelfierro.c195.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {
    private ObservableList<Division> divisions = FXCollections.observableArrayList();
    private int countryId;
    private String country;
    private String createDate;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Divisions getter
     *
     * @return the divisions
     */
    public ObservableList<Division> getDivisions() {
        return divisions;
    }

    /**
     * Adds division to list.
     *
     * @param division the division to add
     */
    public void addDivision(Division division) {
        divisions.add(division);
    }

    /**
     * Country ID getter
     *
     * @return the countryId
     */
    public int getId() {
        return countryId;
    }

    /**
     * Set divisions.
     */
    public void setDivisions(ObservableList<Division> divisions) {
        this.divisions = divisions;
    }
}
