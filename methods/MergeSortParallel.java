package methods;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * @author KunalHS on 12/05/25
 */
public class MergeSortParallel extends AbstractSortMethod {
    @Override
    protected void doSort(int[] arr) {
        new ParallelMergeSortTask(arr, 0, arr.length - 1).compute();
    }

    private static class ParallelMergeSortTask extends RecursiveAction {

        int[] arr;
        int start;
        int end;

        ParallelMergeSortTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }


        @Override
        protected void compute() {
            if (start >= end) {
                return;
            }
            int mid = start + ((end - start) / 2);
            MergeSortParallel.ParallelMergeSortTask leftTask = new ParallelMergeSortTask(arr, start, mid);
            MergeSortParallel.ParallelMergeSortTask rightTask = new ParallelMergeSortTask(arr, mid + 1, end);
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
            merge(arr, start, mid, end);
        }

        private void merge(int[] arr, int start, int mid, int end) {
            int length = end - start + 1;
            int[] temp = new int[length];
            int p = 0, i = start, j = mid + 1;
            while (i <= mid && j <= end) {
                if (arr[i] <= arr[j]) {
                    temp[p++] = arr[i++];
                } else {
                    temp[p++] = arr[j++];
                }
            }
            while (i <= mid) {
                temp[p++] = arr[i++];
            }
            while (j <= end) {
                temp[p++] = arr[j++];
            }
            i = 0;
            j = start;
            while (i < length) {
                arr[j++] = temp[i++];
            }
        }
    }
}
