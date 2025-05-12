package methods;

import java.util.Arrays;

/**
 * @author KunalHS on 09/05/25
 */
public class JavaSort extends AbstractSortMethod {
    @Override
    protected void doSort(int[] arr) {
        Arrays.sort(arr);
    }
}