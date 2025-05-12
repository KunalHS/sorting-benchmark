package printer;

import javax.swing.*;
import javax.swing.table.TableModel;

public class JTablePrinter {
    public static void printTable(JTable table) {
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        TableModel model = table.getModel();
        int columnCount = model.getColumnCount();
        int rowCount = model.getRowCount();

        // Step 1: Calculate max width for each column
        int[] columnWidths = new int[columnCount];
        for (int col = 0; col < columnCount; col++) {
            columnWidths[col] = model.getColumnName(col).length();
            for (int row = 0; row < rowCount; row++) {
                Object value = model.getValueAt(row, col);
                int len = value != null ? value.toString().length() : 0;
                columnWidths[col] = Math.max(columnWidths[col], len);
            }
        }

        // Step 2: Print header
        for (int col = 0; col < columnCount; col++) {
            String header = model.getColumnName(col);
            System.out.print(padRight(header, columnWidths[col]) + " | ");
        }
        System.out.println();

        // Step 3: Print separator
        for (int col = 0; col < columnCount; col++) {
            System.out.print("-".repeat(columnWidths[col]) + " | ");
        }
        System.out.println();

        // Step 4: Print rows
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                Object value = model.getValueAt(row, col);
                String str = value != null ? value.toString() : "";
                System.out.print(padRight(str, columnWidths[col]) + " | ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    private static String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
