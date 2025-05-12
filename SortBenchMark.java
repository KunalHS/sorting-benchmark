import methods.SortMethod;
import printer.JTablePrinter;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

class SortBenchMark {

    public static final String CLASS_EXTENSION = ".class";
    public static final String METHODS_PACKAGE = "methods.";
    private static int MAX_VALUE = 1000000;

    public static void main(String[] args) {
        List<Class<SortMethod>> allSortMethods = getAllSortMethods();
        int[] sizes = {10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};
        List<long[]> benchMarkValues = new ArrayList<>();
        for (int size : sizes) {
            System.out.println("On size: " + size);
            int[] unsortedArray = generateRandomArray(size);
            long[] timeTaken = performBenchmarking(allSortMethods, unsortedArray);
            benchMarkValues.add(timeTaken);
        }
        try {
            printTable(allSortMethods, sizes, benchMarkValues);
        } catch (PrinterException ignored) {

        }
    }

    private static void warmUp(int iterations, List<Class<SortMethod>> allSortMethods, int[] sizes) {
        for (Class<SortMethod> method : allSortMethods) {
            for (int size : sizes) {
                warmUp(iterations, method, size);
            }
        }
    }

    private static void warmUp(int iterations, Class<SortMethod> method, int size) {
        for (int i = 0; i < iterations; i++) {
            int[] arr = generateRandomArray(size);
            try {
                benchmarkWith(arr, method);
            } catch (Exception ignored) {
            }
        }
    }

    private static void printTable(List<Class<SortMethod>> allSortMethods, int[] sizes, List<long[]> benchMarkValues) throws PrinterException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        DecimalFormat indianFormat = new DecimalFormat("##,##,###", symbols);

        List<String> columns = new ArrayList<>();
        columns.add("Size");
        allSortMethods.forEach(method -> columns.add(method.getSimpleName()));

        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < sizes.length; i++) {
            Object[] row = new Object[allSortMethods.size() + 1];
            row[0] = indianFormat.format(sizes[i]); // Indian format

            long[] timeTaken = benchMarkValues.get(i);
            int index = 1;
            for (long time : timeTaken) {
                row[index++] = indianFormat.format(time); // Indian format
            }

            data.add(row);
        }

        JTable table = new JTable(data.toArray(new Object[0][]), columns.toArray(new String[0]));
        JTablePrinter.printTable(table);
    }

    private static long[] performBenchmarking(List<Class<SortMethod>> allSortMethods, int[] unsortedArray) {
        long[] timeTaken = new long[allSortMethods.size()];
        int index = 0;
        for (Class<SortMethod> sortMethod : allSortMethods) {
            try {
                warmUp(10, sortMethod, unsortedArray.length);
                timeTaken[index++] = benchmarkWithAndAverage(unsortedArray, sortMethod, 5);
            } catch (Exception e) {
                System.out.println("Exception with sorting for class: " + sortMethod.getSimpleName() + ", exception: " + e.getMessage());
            }
        }
        return timeTaken;
    }

    private static long benchmarkWithAndAverage(int[] unsortedArray, Class<SortMethod> sortMethod, int sampleSize) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        long[] sampleTimes = new long[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            SortMethod method = benchmarkWith(unsortedArray, sortMethod);
            sampleTimes[i] = method.getTimeTaken();
        }
        long sum = 0;
        for (long time : sampleTimes) {
            sum += time;
        }
        return sum / 5;
    }

    private static SortMethod benchmarkWith(int[] unsortedArray, Class<SortMethod> sortMethod) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SortMethod method = sortMethod.getDeclaredConstructor().newInstance();
        method.sort(unsortedArray);
        return method;
    }

    private static List<Class<SortMethod>> getAllSortMethods() {
        List<Class<SortMethod>> sortMethods = new ArrayList<>();
        File currentDirectory = new File("./out/methods");
        File[] currentDirectoryFiles = currentDirectory.listFiles();
        for (File file : currentDirectoryFiles) {
            if (Objects.isNull(file) || !file.getName().endsWith(CLASS_EXTENSION)) {
                continue;
            }
            String className = file.getName().replace(CLASS_EXTENSION, "");
            try {
                Class<?> forName = Class.forName(METHODS_PACKAGE + className);
                if (SortMethod.class.isAssignableFrom(forName) && !Modifier.isAbstract(forName.getModifiers())) {
                    sortMethods.add((Class<SortMethod>) forName);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return sortMethods;
    }

    private static void debugArray(int[] arr) {
        int size = arr.length;
        System.out.print("Array: ");
        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random();
        }
        return arr;
    }

    private static int random() {
        Random random = new Random();
        return random.nextInt(MAX_VALUE);
    }
}

