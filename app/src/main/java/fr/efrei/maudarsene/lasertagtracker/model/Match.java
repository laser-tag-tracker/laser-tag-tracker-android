package fr.efrei.maudarsene.lasertagtracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class Match implements Serializable {

    private String id;
    private String userId;

    private String playerName;
    private int rank;
    private int score;
    private double precision;
    private int teamScore;

    private LocalDate date;

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

    /**
     * For jackson JSON serialization
     */
    public Match() {
    }

    public Match(String id, String userId, String playerName, int rank, int score, double precision, int teamScore, LocalDate date, int chestGiven, int backGiven, int shouldersGiven, int gunGiven, int chestReceived, int backReceived, int shouldersReceived, int gunReceived, String address, double latitude, double longitude) {
        this(playerName, rank, score, precision, teamScore,date, chestGiven, backGiven, shouldersGiven, gunGiven, chestReceived, backReceived, shouldersReceived, gunReceived, address, latitude, longitude);
        this.id = id;
        this.userId = userId;
    }

    public Match(String playerName, int rank, int score, double precision, int teamScore,LocalDate date, int chestGiven, int backGiven, int shouldersGiven, int gunGiven, int chestReceived, int backReceived, int shouldersReceived, int gunReceived, String address, double latitude, double longitude) {
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
        this.date = date;
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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", playerName='" + playerName + '\'' +
                ", rank=" + rank +
                ", score=" + score +
                ", precision=" + precision +
                ", teamScore=" + teamScore +
                ", date=" + date +
                ", chestGiven=" + chestGiven +
                ", backGiven=" + backGiven +
                ", shouldersGiven=" + shouldersGiven +
                ", gunGiven=" + gunGiven +
                ", chestReceived=" + chestReceived +
                ", backReceived=" + backReceived +
                ", shouldersReceived=" + shouldersReceived +
                ", gunReceived=" + gunReceived +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
