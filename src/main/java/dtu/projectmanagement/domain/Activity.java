package dtu.projectmanagement.domain;

public class Activity {

    private int activityNum;
    String activityName;
    int startWeek;
    int endWeek;
    double expectedWorkHours;

    public Activity(int activityNum,String activityName) {
        this.activityName=activityName;
        this.activityNum=activityNum;

    }


}
