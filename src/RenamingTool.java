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
     * Represents a method to rename a file by adding the number in front and preserving the original filename
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
                    throw new java.io.IOException("file already exists");
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
     * Represents a method to rename a file by replacing the filename with the number
     * @param listOfFiles a sorted list of files to work with
     * @param initialPath the directory of the files
     * @throws IOException if a file already exists
     */
    private void renameAsNumber(File[] listOfFiles, String initialPath) throws IOException {
        File file2;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                String filetype = f.getName().substring(f.getName().length() - 4);
                file2 = new File (initialPath + "/" + start + filetype);
                if (file2.exists()) {
                    throw new java.io.IOException("file already exists");
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
                String filetype = f.getName().substring(f.getName().length() - 4);
                file2 = new File(initialPath + "/" + f.getName().substring(0, f.getName().length() - 4) + "_" + start + filetype);
                if (file2.exists()) {
                    throw new java.io.IOException("file already exists");
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

