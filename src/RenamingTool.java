import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class RenamingTool {
    private int start;
    private int lastChoice;
    private int inc;

    /**
     * Represents a tool to rename all directories within a directory
     * @param listOfFiles the sorted list of files within a directory
     * @param initialPath the initial directory
     * @param start the first number to rename a file to
     * @param inc the incrementation amount per iteration
     */
    RenamingTool(File[] listOfFiles, String initialPath, int start, int inc){
        this.inc = inc;
        this.start = start;

        if (listOfFiles != null) {
            int choice = pickRenamingType();
            try {
                if (choice == 1) {
                    renameNumberInFront(listOfFiles, initialPath);
                } else if (choice == 2) {
                    renameAsNumber(listOfFiles, initialPath);
                } else if (choice == 3) {
                    renameNumberBehind(listOfFiles, initialPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("List of Files is null");
        }
    }

    /**
     *
     * @return the naming convention choice picked by the user
     */
    int returnChoice() {
        return lastChoice;
    }

    /**
     * Represents a tool to rename all directories within a directory
     * @param listOfFiles the sorted list of files within a directory
     * @param initialPath the initial directory
     * @param start the first number to rename a file to
     * @param choice a naming convention chosen prior to the constructor being called
     */
    RenamingTool(File[] listOfFiles, String initialPath, int start, int choice, int inc){
        this.inc = inc;
        this.start = start;

        if (listOfFiles != null) {
            if (choice == 0) {
                choice = pickRenamingType();
                lastChoice = choice;
                returnChoice();
            }
            try {
                if (choice == 1) {
                    renameNumberInFront(listOfFiles, initialPath);
                } else if (choice == 2) {
                    renameAsNumber(listOfFiles, initialPath);
                } else if (choice == 3) {
                    renameNumberBehind(listOfFiles, initialPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("List of Files is null");
        }
    }


    /**
     *
     * @return start (used at the end to determine what the last number used was)
     */
    int returnEnd() {
        return this.start;
    }

    /**
     * Represents a menu for the user to choose how to rename their files.
     * @return the renaming convention picked by a user
     */
    private int pickRenamingType() {
        Scanner s = new Scanner(System.in);
        System.out.println("Pick a naming convention: ");
        System.out.println("1. Rename with a number in front (i.e. 1_filename.jpg)");
        System.out.println("2. Rename your file as the number (i.e. 2.jpg)");
        System.out.println("3. Rename with a number behind (i.e. filename_3.jpg)");
        System.out.print("Pick a convention: ");
        int tbr = s.nextInt();
        if (tbr > 0 && tbr < 4) {
            return tbr;
        } else {
            System.out.println("Unrecognized command. Defaulting to option 2.");
            return 2;
        }
    }


    /**
     * Represents a method to rename a list of files by adding a number in front and preserving the original filename
     * @param listOfFiles a sorted list of files to work with
     * @param initialPath the directory of the files
     * @throws IOException if a file already exists
     */
    private void renameNumberInFront(File[] listOfFiles, String initialPath) throws IOException {
        File file2;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                file2 = new File (initialPath + "/" + start + "_" + f.getName());
                if (file2.exists()) {
                    throw new java.io.IOException(f.getName() + " already exists");
                }
                boolean success = f.renameTo(file2);
                if (!success) {
                    System.out.println("Could not rename " + f.getName());
                }
                start += inc;

            }
        }
    }

    /**
     * Determines the filetype of a file. Requires there to be at least one period in the string.
     * @param s the file name of a file
     * @return the filetype of the file including the period.
     */
    private String determineFiletype(String s) {
        if (s.contains(".")) {
            while (s.contains(".")) {
                if (s.contains(".") && !s.substring(s.indexOf(".") + 1).contains(".")) {
                    return s.substring(s.indexOf("."));
                } else {
                    s = s.substring(s.indexOf(".") + 1);
                }
            }
        }
        return "";
    }

    /**
     * Represents a method to rename a file by replacing the filename with the number
     * @param listOfFiles a sorted list of files to work with
     * @param initialPath the directory of the files
     * @throws IOException if a file already exists
     */
    private void renameAsNumber(File[] listOfFiles, String initialPath) throws IOException {
        File file2;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                String filetype = determineFiletype(f.getName());
                file2 = new File (initialPath + "/" + start + filetype);
                if (file2.exists()) {
                    throw new java.io.IOException(f.getName() + " already exists");
                }
                boolean success = f.renameTo(file2);
                if (!success) {
                    System.out.println("Could not rename " + f.getName());
                }
                start += inc;

            }
        }
    }

    /**
     * Represents a method to rename a file by adding the number behind and preserving the original filename
     * @param listOfFiles a sorted list of files to work with
     * @param initialPath the directory of the files
     * @throws IOException if a file already exists
     */
    private void renameNumberBehind(File[] listOfFiles, String initialPath) throws IOException {
        File file2;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                String filetype = determineFiletype(f.getName());
                file2 = new File(initialPath + "/" + f.getName().substring(0, f.getName().length() - 4) + "_" + start + filetype);
                if (file2.exists()) {
                    throw new java.io.IOException(f.getName() + " already exists");
                }
                boolean success = f.renameTo(file2);
                if (!success) {
                    System.out.println("Could not rename " + f.getName());
                }
                start += inc;

            }
        }
    }
}

