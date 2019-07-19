import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamingTool {

    /**
     * @param folder the original file to read from
     * @return a numerically sorted array of files from a directory (ignores folders)
     */
    private static File[] sortFiles(File folder) {
        int folderOffset = 0;
        File[] listOfFiles = folder.listFiles();
        ArrayList<Integer> order = new ArrayList<>();
        ArrayList<Integer> initialOrder = new ArrayList<>();
        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                try {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(listOfFile.getName());
                    while (m.find()) {

                        order.add(Integer.parseInt(m.group()));
                        initialOrder.add(Integer.parseInt(m.group()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                order.add(Integer.MAX_VALUE - folderOffset);
                initialOrder.add(Integer.MAX_VALUE - folderOffset);
            }
        }
        Collections.sort(order);
        //System.out.println("Sorted order " + order);
        //System.out.println("Initial order" + initialOrder);
        for (int i = 0; i < order.size(); i++) {
            order.set(i, initialOrder.indexOf(order.get(i)));
        }
        File[] temp = folder.listFiles();
        for (int i = 0; i < order.size(); i++) {
            assert temp != null;
            temp[i] = listOfFiles[order.get(i)];
        }
        return temp;
    }

    /**
     * Represents a tool used to rename large quantities of files sequentially in a folder.
     * @param args well, it's psvm. What'd you expect.
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a folder/directory: ");
        String initialPath = sc.nextLine();


        // File (or directory) with old name
        File folder = new File(initialPath);
        //PaddingTool pt = new PaddingTool(folder, initialPath);
        //Files.list(Paths.get(dirName)).sorted().forEach(System.out::println);
        File[] listOfFiles = sortFiles(folder);
        listFiles(initialPath, listOfFiles);
        System.out.print("Enter a starting number: ");
        int start = sc.nextInt();
        System.out.print("Enter increment amount: ");
        int inc = sc.nextInt();
        RenamingTool r = new RenamingTool(listOfFiles, initialPath, start, inc);
        int lastEnd = r.returnEnd();
        System.out.println(lastEnd);
        sc.close();

        // File (or directory) with new name


        // Rename file (or directory)


//        if (!success) {
//            // File was not successfully renamed
//        }
        //File file = new File("D:/Tutoring Materials/test.txt");

    }

    /**
     * Displays all files and folders in a directory. Assumes listOfFiles to be presorted.
     * @param initialPath the path of the directory being displayed
     * @param listOfFiles the list of files from the file constructed from the directory
     */
    private static void listFiles(String initialPath, File[] listOfFiles) {
        int fs = 0;
        int ds = 0;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                System.out.println("File " + f.getName());
                fs += 1;
            } else if (f.isDirectory()) {
                System.out.println("Directory " + f.getName());
                ds += 1;
            }
        }
        System.out.println("Found " + fs + " files and " + ds + " directories in " + initialPath);
    }
}
