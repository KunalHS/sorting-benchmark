package methods;

import stopwatch.StopWatch;

/**
 * @author KunalHS on 09/05/25
 */
public abstract class AbstractSortMethod implements SortMethod {
    Long timeTaken = null;
    @Override
    public void sort(int[] arr) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        doSort(arr);
        stopWatch.stop();
        updateTimeTaken(arr, stopWatch);
    }

    @Override
    public Long getTimeTaken() {
        return timeTaken;
    }

    private void updateTimeTaken(int[] arr, StopWatch stopWatch) {
        if (checkIfSorted(arr)) {
            timeTaken = stopWatch.timeTaken();
        } else {
            timeTaken = -1L;
        }
    }

    protected abstract void doSort(int[] arr);

    private boolean checkIfSorted(int[] arr) {
        int size = arr.length;
        for (int i = 1; i < size; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

}
