package tekvo.controllers.models;

public class TimeData {
    private long timeSpent;

    public TimeData() {
        // Construtor padrão
    }

    public TimeData(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
