package nju.agile.travel.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class RUserActivityID implements Serializable {

    int userID;

    int activityID;

//    public RUserActivityID() {}
//
//    public RUserActivityID(int userID, int activityID) {
//        this.userID = userID;
//        this.activityID = activityID;
//    }

    public static RUserActivityID of(int userID, int activityID) {
        RUserActivityID id = new RUserActivityID();
        id.setUserID(userID);
        id.setActivityID(activityID);
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RUserActivityID that = (RUserActivityID) o;
        return Objects.equals(userID, that.userID) && Objects.equals(activityID, that.activityID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, activityID);
    }

}
