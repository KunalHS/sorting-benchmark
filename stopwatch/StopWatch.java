package stopwatch;

/**
 * @author KunalHS on 09/05/25
 */
public class StopWatch {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long startTime() {
        return startTime;
    }

    public StopWatch startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long endTime() {
        return endTime;
    }

    public StopWatch endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public long timeTaken() {
        return endTime - startTime;
    }
}
