import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderRenamingTool {

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
     * @param folder the original directory to read from
     * @return a numerically sorted array of folders from a directory (ignores files)
     */
    private static File[] sortFolders(File folder) {
        File[] listOfFiles = folder.listFiles();
        ArrayList<Integer> order = new ArrayList<>();
        ArrayList<Integer> initialOrder = new ArrayList<>();
        assert listOfFiles != null;
        for (File f : listOfFiles) {
            if (f.isDirectory()) {
                try {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(f.getName());
                    while (m.find()) {
//                        order.add(Integer.parseInt(listOfFiles[i].getName().substring(listOfFiles[i].getName().indexOf("Chapter") + 8)));
//                        initialOrder.add(Integer.parseInt(listOfFiles[i].getName().substring(listOfFiles[i].getName().indexOf("Chapter") + 8)));
                        order.add(Integer.parseInt(m.group()));
                        initialOrder.add(Integer.parseInt(m.group()));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Found a non-integer filename " + f.getName());
                }
            } else {
                order.add(Integer.MAX_VALUE);
                initialOrder.add(Integer.MAX_VALUE);
            }
        }
        Collections.sort(order);
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
     * Checks if a given directory is invalid.
     * @param path the path of a directory to check
     * @return true if a directory does not exist, false otherwise
     */
    private static boolean isInvalidDirectory(String path){
        File temp = new File(path);
        return !temp.exists();
    }

    /**
     * Represents a tool to rename all files found within folders within a specified directory by numerical order.
     * @param args well, it's psvm. What'd you expect?
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a directory: ");
        String initialPath = sc.nextLine();
//        if (initialPath.toLowerCase().contains("C:/") || initialPath.toLowerCase().contains("C:\\")) {
//            throw new HolyShitDontDoThatException("stop. stop that right now.");
//        }
        if(isInvalidDirectory(initialPath)) {
            while (isInvalidDirectory(initialPath)) {
                System.out.println("Invalid Directory");
                System.out.print("Enter another directory: ");
                initialPath = sc.nextLine();
            }
        }
        File folder = new File(initialPath);
        //PaddingTool pt = new PaddingTool(folder, initialPath);
        //Files.list(Paths.get(dirName)).sorted().forEach(System.out::println)
        File[] listOfFiles = sortFolders(folder);
        listFiles(initialPath, listOfFiles);
        System.out.print("Enter output directory: (Enter \"this\" for this directory): ");
        String outputDir = sc.nextLine();
        if (!outputDir.toLowerCase().contains("this")) {
            if (isInvalidDirectory(outputDir)) {
                System.out.println("Invalid Output Directory, outputting to the current directory");
                outputDir = "this";
            }
        }
        System.out.print("Enter a starting number: ");
        int start = sc.nextInt();
        if (start < 0 || start >= Integer.MAX_VALUE) {
            System.out.println("Invalid start number, defaulting to 0");
            start = 0;
        }
        System.out.print("Enter incrementation amount: ");
        int inc = sc.nextInt();
        if (inc < 1 || inc >= Integer.MAX_VALUE) {
            System.out.println("Invalid incrementation, defaulting to 1");
            inc = 1;
        }
        renameLoop(initialPath, listOfFiles, outputDir, start, inc);
        for(File f: listOfFiles) {
            if (f.isDirectory()) {
                f.deleteOnExit();
            }
        }
        sc.close();
        System.out.println("Completed!");
    }


    /**
     * Applies the Renaming Tool to each folder found inside a directory. Outputs the new files to outputDir if the
     * directory is a valid filepath or "this" indicating no change. Applies the same naming convention to all files
     * in every folder within the directory.
     *
     * @param initialPath the directory the user provided in which all files and folders to rename are located
     * @param listOfFiles all extracted files and folders from a directory
     * @param outputDir   the output directory chosen by the user
     * @param start       the starting number for renaming provided by the user
     * @param inc         the amount to increment by per iteration
     */
    private static void renameLoop(String initialPath, File[] listOfFiles, String outputDir, int start, int inc) {
        RenamingTool r;
        String subDirectory;
        int lastEnd = start;
        int nextChoice = 0;
        for (int i = 0; i < listOfFiles.length; i++) { //listOfFiles should be already sorted numerically
            subDirectory = listOfFiles[i].getPath(); //get the path of the first folder within the array
            File temp = new File(subDirectory); //create a temporary file based on the subdirectory's filepath
            File[] lof = sortFiles(temp); //sort the files within the subdirectory numerically
            if (i == 0) { //if on the first iteration, let the user choose which naming convention they want
                if (!outputDir.equalsIgnoreCase("this")) {
                    r = new RenamingTool(lof, outputDir, lastEnd, 0, inc);//rename to a specific output directory
                } else {
                    r = new RenamingTool(lof, initialPath, lastEnd, 0, inc);
                }
                nextChoice = r.returnChoice();
            } else {
                if (!outputDir.equalsIgnoreCase("this")) {
                    r = new RenamingTool(lof, outputDir, lastEnd, nextChoice, inc);
                } else {
                    r = new RenamingTool(lof, initialPath, lastEnd, nextChoice, inc);
                }
            }
            lastEnd = r.returnEnd();
        }
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
        if (ds == 0 && fs == 0) {
            throw new RuntimeException("Did not find any files or directories in " + initialPath);
        }
        System.out.println("Found " + fs + " files and " + ds + " directories in " + initialPath);
    }
}
