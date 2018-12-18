package model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Measurement {

    int stationNumber;
    Date date;
    Time time;
    float temperature;
    float dewPoint;
    float stp;
    float slp;
    float visibility;
    float windSpeed;
    float precipitate;
    float snow;
    int frshtt;
    float cloudsPercentage;
    int windDirection;

    private Measurement() {

    }

    private Measurement(int stationNumber, Date date, Time time, float temperature, float dewPoint, float stp,
                        float slp, float visibility, float windSpeed, float precipitate, float snow, int frshtt,
                        float cloudsPercentage, int windDirection) {
        this.stationNumber = stationNumber;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.stp = stp;
        this.slp = slp;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.precipitate = precipitate;
        this.snow = snow;
        this.frshtt = frshtt;
        this.cloudsPercentage = cloudsPercentage;
        this.windDirection = windDirection;
    }

    public static Measurement fromData(List<String> data) {
        System.out.print(data.size());
        Measurement measurement = new Measurement();
        measurement.setStationNumber(Integer.getInteger(data.get(0)));
        measurement.setDate(Date.valueOf(data.get(1)));
        measurement.setTime(Time.valueOf(data.get(2)));
        measurement.setTemperature(Float.parseFloat(data.get(3)));
        measurement.setDewPoint(Float.parseFloat(data.get(4)));
        measurement.setStp(Float.parseFloat(data.get(5)));
        measurement.setSlp(Float.parseFloat(data.get(6)));
        measurement.setVisibility(Float.parseFloat(data.get(7)));
        measurement.setWindSpeed(Float.parseFloat(data.get(8)));
        measurement.setPrecipitate(Float.parseFloat(data.get(9)));
        measurement.setSnow(Float.parseFloat(data.get(10)));
        measurement.setFrshtt(Integer.getInteger(data.get(11)));
        measurement.setCloudsPercentage(Float.parseFloat(data.get(12)));
        measurement.setWindDirection(Integer.getInteger(data.get(13)));

        return measurement;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public float getStp() {
        return stp;
    }

    public void setStp(float stp) {
        this.stp = stp;
    }

    public float getSlp() {
        return slp;
    }

    public void setSlp(float slp) {
        this.slp = slp;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getPrecipitate() {
        return precipitate;
    }

    public void setPrecipitate(float precipitate) {
        this.precipitate = precipitate;
    }

    public float getSnow() {
        return snow;
    }

    public void setSnow(float snow) {
        this.snow = snow;
    }

    public int getFrshtt() {
        return frshtt;
    }

    public void setFrshtt(int frshtt) {
        this.frshtt = frshtt;
    }

    public float getCloudsPercentage() {
        return cloudsPercentage;
    }

    public void setCloudsPercentage(float cloudsPercentage) {
        this.cloudsPercentage = cloudsPercentage;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        result.append("_________Measurement_________");
        result.append(newLine);
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                result.append(field.get(this));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("_____________END_____________");

        return result.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }
}
