package logic;

import java.io.Serializable;
import java.time.LocalDate;

public class BookingDetails implements Serializable {

    private String visitType;
    private String time;
    private String parkName;
    private String numOfVisitors;
    private String telephone;
    private String email;
    private LocalDate visitDate;

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getNumOfVisitors() {
        return numOfVisitors;
    }

    public void setNumOfVisitors(String numOfVisitors) {
        this.numOfVisitors = numOfVisitors;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return "BookingDetails [visitType=" + visitType + ", time=" + time + ", parkName=" + parkName
                + ", numOfVisitors=" + numOfVisitors + ", telephone=" + telephone + ", email=" + email
                + ", visitDate=" + visitDate + "]";
    }
}
