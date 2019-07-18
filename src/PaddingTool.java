import java.io.File;

public class PaddingTool {

    public PaddingTool(File folder, String initialPath){
        File[] listOfFiles = folder.listFiles();

        File file2;
        for (File f : listOfFiles) {
            if (f.isFile()) {
                file2 = new File (initialPath + "/" + 0 + f.getName());
                boolean success = f.renameTo(file2);
                if (!success) {
                    System.out.println("Could not rename " + f.getName());
                }

            }
        }
    }
}
