package fr.efrei.maudarsene.lasertagtracker.model;

public class Match {

    private String playerName;
    private int rank;
    private int score;
    private double precision;
    private int teamScore;

    // Given
    private int chestGiven;
    private int backGiven;
    private int shouldersGiven;
    private int gunGiven;

    // Received
    private int chestReceived;
    private int backReceived;
    private int shouldersReceived;
    private int gunReceived;

    // Location
    private String address;
    private double latitude;
    private double longitude;

    public Match(String playerName, int rank, int score, double precision, int teamScore, int chestGiven, int backGiven, int shouldersGiven, int gunGiven, int chestReceived, int backReceived, int shouldersReceived, int gunReceived, String address, double latitude, double longitude) {
        this.playerName = playerName;
        this.rank = rank;
        this.score = score;
        this.precision = precision;
        this.teamScore = teamScore;
        this.chestGiven = chestGiven;
        this.backGiven = backGiven;
        this.shouldersGiven = shouldersGiven;
        this.gunGiven = gunGiven;
        this.chestReceived = chestReceived;
        this.backReceived = backReceived;
        this.shouldersReceived = shouldersReceived;
        this.gunReceived = gunReceived;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Computed Properties

    public int totalGiven(){
        return chestGiven + backGiven + shouldersGiven + gunGiven;
    }

    public int totalReceived(){
        return chestReceived + backReceived + shouldersReceived + gunReceived;
    }

    public double teamScorePercent(){
        return (score / teamScore) * 100;
    }

    // Getters & setters

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    public int getChestGiven() {
        return chestGiven;
    }

    public void setChestGiven(int chestGiven) {
        this.chestGiven = chestGiven;
    }

    public int getBackGiven() {
        return backGiven;
    }

    public void setBackGiven(int backGiven) {
        this.backGiven = backGiven;
    }

    public int getShouldersGiven() {
        return shouldersGiven;
    }

    public void setShouldersGiven(int shouldersGiven) {
        this.shouldersGiven = shouldersGiven;
    }

    public int getGunGiven() {
        return gunGiven;
    }

    public void setGunGiven(int gunGiven) {
        this.gunGiven = gunGiven;
    }

    public int getChestReceived() {
        return chestReceived;
    }

    public void setChestReceived(int chestReceived) {
        this.chestReceived = chestReceived;
    }

    public int getBackReceived() {
        return backReceived;
    }

    public void setBackReceived(int backReceived) {
        this.backReceived = backReceived;
    }

    public int getShouldersReceived() {
        return shouldersReceived;
    }

    public void setShouldersReceived(int shouldersReceived) {
        this.shouldersReceived = shouldersReceived;
    }

    public int getGunReceived() {
        return gunReceived;
    }

    public void setGunReceived(int gunReceived) {
        this.gunReceived = gunReceived;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
