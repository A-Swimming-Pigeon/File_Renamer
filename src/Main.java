import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    //REQUIRES: File != null, folder contents are all numerically ordered
    //EFFECTS: returns a folder's sorted files numerically (originally lexicographic) and ignores directories
    private static File[] sortFiles(File folder) {
        int folderOffset = 0;
        File[] listOfFiles = folder.listFiles();
        ArrayList<Integer> order = new ArrayList<>();
        ArrayList<Integer> initialOrder = new ArrayList<>();
        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) {
                order.add(Integer.parseInt(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf("."))));
                initialOrder.add(Integer.parseInt(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf("."))));
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
        for(int i = 0; i < temp.length; i++) {
            temp[i] = listOfFiles[order.get(i)];
        }
        return temp;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a folder/directory: ");
        String initialPath = sc.nextLine();


        // File (or directory) with old name
        File folder = new File(initialPath);
        //PaddingTool pt = new PaddingTool(folder, initialPath);
        //Files.list(Paths.get(dirName)).sorted().forEach(System.out::println);
        File[] listOfFiles = sortFiles(folder);
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
        System.out.print("Enter a starting number: ");
        int start = sc.nextInt();
        RenamingTool r = new RenamingTool(listOfFiles, initialPath, start);
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
}
