package customer;

public class Building {
    private int buildingHeight, year, buildingSize;
    private String buildingPrice, buildingOutwall;

    public Building() {
    }

    public void finalize() throws Throwable {
    }

    public int getBuildingHeight() {
        return buildingHeight;
    }
    public void setBuildingHeight(int buildingHeight) {
        this.buildingHeight = buildingHeight;
    }
    public String getBuildingPrice() {
        return buildingPrice;
    }
    public void setBuildingPrice(String buildingPrice) {
        this.buildingPrice = buildingPrice;
    }
    public int getBuildingSize() {
        return buildingSize;
    }
    public void setBuildingSize(int buildingSize) {
        this.buildingSize = buildingSize;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getBuildingOutwall() {
        return buildingOutwall;
    }
    public void setBuildingOutwall(String buildingOutwall) {
        this.buildingOutwall = buildingOutwall;
    }
}