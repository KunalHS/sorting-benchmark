package methods;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * @author KunalHS on 09/05/25
 */
public class JavaSortParallel extends AbstractSortMethod {
    @Override
    protected void doSort(int[] arr) {
        Arrays.parallelSort(arr);
    }

}
