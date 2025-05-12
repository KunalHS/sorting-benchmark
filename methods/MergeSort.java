package methods;

/**
 * @author KunalHS on 12/05/25
 */
public class MergeSort extends AbstractSortMethod {
    @Override
    protected void doSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = start + ((end - start) / 2);
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, p = 0;
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
        while (j <= end) {
            arr[j++] = temp[i++];
        }
    }
}
