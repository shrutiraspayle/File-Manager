package outdated;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Scanner;

class FileManager extends Methods {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Methods func = new Methods();
        UI ui = new UI();
        ui.banner();
        func.run();
    }
}

class Methods extends UI {

    File F_Home = new File(".");
    Scanner scan = new Scanner(System.in);

    public void allFiles() {
        banner();
        int i = 0, j = 0;
        try {
            File[] Files = F_Home.listFiles(File::isFile);
            File[] Folders = F_Home.listFiles(File::isDirectory);
            System.out.println(ANSI_CYAN + " Folders - \n" + ANSI_RESET);

            for (File folder : Folders) {
                if (folder != null) {
                    System.out.println(ANSI_PURPLE + "  " + folder.getName() + ANSI_RESET);
                }
                i = 1;
            }

            if (i == 0) {
                System.out.println(ANSI_RED + " No Folders Found! " + ANSI_RESET);
            }

            System.out.println(ANSI_CYAN + "\n Files - \n" + ANSI_RESET);

            for (File file : Files) {
                if (file != null) {
                    System.out.println(ANSI_GREEN + "  " + file.getName() + ANSI_RESET);
                }
                j = 1;
            }

            if (j == 0) {
                System.out.println(ANSI_RED + " No Files Found! " + ANSI_RESET);
            }

        } catch (NullPointerException e) {
            banner();
            System.out.println(" Folder or Path Doesn\'t exists");
        }

        back();
        listAll();
    }

    public void listAll() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Current Folder\n  2. Other Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                F_Home = new File(".");
                allFiles();
                break;

            case "2":
                banner();
                System.out.print("Path OR Folder Name: ");
                String foldername = scan.nextLine();
                F_Home = new File(foldername);
                allFiles();
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                run();
                break;
        }

    }

    public void create() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Create File\n  2. Create Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String filename = scan.nextLine();
                File Fi = new File(filename);
                try {
                    if (Fi.createNewFile()) {
                        banner();
                        System.out.println(" File Created Successfully!");
                        back();
                        create();
                    } else {
                        banner();
                        System.out.println(" File or Folder already exists!");
                        back();
                        create();
                    }
                } catch (FileAlreadyExistsException e) {
                    banner();
                    System.out.println(" File already exists!");
                    back();
                    create();
                } catch (IOException e) {
                    banner();
                    System.out.println(" Path Doesn\'t exists!");
                    back();
                    create();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                String foldername = scan.nextLine();
                File Fo = new File(foldername);

                if (!Fo.isDirectory()) {
                    if (Fo.mkdir()) {
                        banner();
                        System.out.println(" Folder Created Successfully!");
                        back();
                        create();
                    } else {
                        banner();
                        System.out.println(" Path Doesn\'t exists!");
                        back();
                        create();
                    }
                } else {
                    banner();
                    System.out.println(" Folder already exists!");
                    back();
                    create();
                }

                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                create();
                break;
        }

    }

    public boolean copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return true;
    }

    public void copy() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Copy File\n  2. Copy Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String filename = scan.nextLine();
                File src = new File(filename);
                banner();
                System.out.print(" New Path OR File Name: ");
                String newfilename = scan.nextLine();
                File dest = new File(newfilename);
                try {
                    if (src.isFile()) {
                        if (Files.copy(src.toPath(), dest.toPath()) != null && !filename.equals(newfilename)){
                            banner();
                            System.out.println(" File Copied Successfully!");
                            back();
                            copy();
                        } else {
                            banner();
                            System.out.println(" File or Folder already exists!");
                            back();
                            copy();
                        }

                    }else{
                        banner();
                        System.out.println(" File not found!");
                        back();
                        copy();
                    }
                } catch (Exception e) {
                    banner();
                    System.out.println(" Folder or Path Doesn\'t exists!");
                    back();
                    copy();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                String foldername = scan.nextLine();
                src = new File(foldername);
                banner();
                System.out.print(" New Path OR Folder Name: ");
                String newfoldername = scan.nextLine();
                dest = new File(newfoldername);
                try {
                    if(src.isDirectory()){
                        if (copyFolder(src, dest) && !foldername.equals(newfoldername)) {
                            banner();
                            System.out.println(" Folder Copied Successfully!");
                            back();
                            copy();
                        }
                        else {
                            banner();
                            System.out.println(" File or Folder already exists!");
                            back();
                            copy();
                        }
                    }
                    else{
                        banner();
                        System.out.println(" Folder not found!");
                        back();
                        move();
                    }
                } catch (Exception e) {
                    banner();
                    System.out.println(" Folder or Path Doesn\'t exists!");
                    back();
                    copy();
                }
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                copy();
                break;
        }
    }

    public void move() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Move File\n  2. Move Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String filename = scan.nextLine();
                File src = new File(filename);
                banner();
                System.out.print(" New Path OR File Name: ");
                String newfilename = scan.nextLine();
                File dest = new File(newfilename);
                try {
                    if(src.isFile()){
                        if (!filename.equals(newfilename)) {
                            Files.move(src.toPath(), dest.toPath());
                            banner();
                            System.out.println(" File Moved Successfully!");
                            back();
                            move();
                        } else {
                            banner();
                            System.out.println(" File or Folder already exists!");
                            back();
                            move();
                        }
                    }
                else{
                    banner();
                    System.out.println(" File not found!");
                    back();
                    move();
                }
                
                } catch (Exception e) {
                    banner();
                    System.out.println(" File or Path Doesn\'t exists!");
                    back();
                    move();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                String foldername = scan.nextLine();
                src = new File(foldername);
                banner();
                System.out.print(" New Path OR Folder Name: ");
                String newfoldername = scan.nextLine();
                dest = new File(newfoldername);
                try {
                    if(src.isDirectory()){
                        if (!foldername.equals(newfoldername) && !dest.isFile() && !dest.isDirectory()) {
                            if (copyFolder(src, dest)) {
                                banner();
                                System.out.println(" Folder Moved Successfully!");
                                fdelete(src);
                                back();
                                move();
                            }
                            
                        }
                        else{
                            banner();
                            System.out.println(" File or Folder already exists!");
                            back();
                            move();
                        }
                        
                    }
                    else{
                        banner();
                        System.out.println(" Folder not found!");
                        back();
                        move();
                    }
                   

                } catch (Exception e) {
                    banner();
                    System.out.println(" Folder or Path Doesn\'t exists!");
                    back();
                    move();
                }
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                move();
                break;
        }
    }

    public void rename() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Rename File\n  2. Rename Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String name = scan.nextLine();
                File old = new File(name);
                banner();
                System.out.print(" New Path OR File Name: ");
                String newname = scan.nextLine();
                File neww = new File(newname);
                try {
                    if (old.isFile()) {
                        if(old.renameTo(neww)){
                            banner();
                            System.out.println(" File Renamed Successfully!");
                            back();
                            rename();
                        }
                        else{
                            banner();
                            System.out.println(" Invalid File Name!");
                            back();
                            rename();
                        }
                    } else {
                        banner();
                        System.out.println(" File not found!");
                        back();
                        rename();
                    }

                } catch (Exception e) {
                    banner();
                    System.out.println(" File or Path Doesn\'t exists!");
                    back();
                    move();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                name = scan.nextLine();
                old = new File(name);
                banner();
                System.out.print(" New Path OR Folder Name: ");
                newname = scan.nextLine();
                neww = new File(newname);
                try {
                    if (old.isDirectory()) {
                        if(old.renameTo(neww)){
                            banner();
                            System.out.println(" Folder Renamed Successfully!");
                            back();
                            rename();
                        }
                        else{
                            banner();
                            System.out.println(" Invalid Folder Name!");
                            back();
                            rename();
                        }
                        
                    } else {
                        banner();
                        System.out.println(" Folder not found!");
                        back();
                        rename();
                    }

                } catch (Exception a) {
                    banner();
                    System.out.println(" Folder or Path Doesn\'t exists!");
                    back();
                    rename();
                }
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                rename();
                break;
        }
    }

    public boolean fdelete(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    fdelete(f);
                }
            }
        }
        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public void delete() {

        banner();
        System.out.print(ANSI_CYAN + "  1. Delete File\n  2. Delete Folder\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String filename = scan.nextLine();
                File Fi = new File(filename);
                if (Fi.isFile() && Fi.delete()) {
                    banner();
                    System.out.println(" File Deleted Successfully!");
                    back();
                    delete();
                } else {
                    banner();
                    System.out.println(" File not found!");
                    back();
                    delete();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                String foldername = scan.nextLine();
                File file = new File(foldername);
                if (fdelete(file)) {
                    banner();
                    System.out.println(" Folder Deleted Successfully!");
                    back();
                    delete();
                } else {
                    banner();
                    System.out.println(" Folder not found!");
                    back();
                    delete();
                }
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                delete();
                break;
        }

    }

    public void checkSize() {
        banner();
        System.out.print(ANSI_CYAN + "  1. Check File Size\n  2. Check Folder Size\n  3. Back\n\n  " + ANSI_YELLOW
                + "Choose an option: " + ANSI_RESET);
        String choice = scan.nextLine();

        switch (choice) {
            case "1":
                banner();
                System.out.print(" Path OR File Name: ");
                String name = scan.nextLine();
                File file = new File(name);
                try {
                    if (file.isFile()) {
                        banner();
                        System.out.println(" File = " + name + "\n Size = " + file.length() / 1024 + " KB");
                        back();
                        checkSize();
                    } else {
                        banner();
                        System.out.println(" File or Path Doesn\'t exists!");
                        back();
                        checkSize();
                        ;
                    }

                } catch (Exception a) {
                    banner();
                    System.out.println(" File or Path Doesn\'t exists!");
                    back();
                    checkSize();
                }
                break;

            case "2":
                banner();
                System.out.print(" Path OR Folder Name: ");
                name = scan.nextLine();
                File folder = new File(name);
                banner();
                try {
                    if (folder.isDirectory()) {
                        banner();
                        System.out.println(" Folder = " + name + "\n Size = " + folder.length() / 1024 + " KB");
                        back();
                        checkSize();
                    } else {
                        banner();
                        System.out.println(" Folder or Path Doesn\'t exists!");
                        back();
                        checkSize();
                    }

                } catch (Exception a) {
                    banner();
                    System.out.println(" Folder or Path Doesn\'t exists!");
                    back();
                    checkSize();
                }
                break;

            case "3":
                run();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                checkSize();
                break;
        }
    }

    public void freeSpace() {
        banner();
        File file = new File(".");
        double totalSize = file.getTotalSpace() / (1024.0 * 1024 * 1024);
        double size = file.getFreeSpace() / (1024.0 * 1024 * 1024);
        System.out.printf(" Free Space: %.3f GB\n\n", size);
        System.out.printf(" Total Space: %.3f GB\n\n", totalSize);
        back();
        run();
    }

    public void about() {
        banner();
        System.out.println(ANSI_CYAN + " Guided By - " + ANSI_RESET + "P.R Satav Sir\n");
        System.out.println(ANSI_CYAN + " Developed By -" + ANSI_RESET + "\n");
        System.out.println(" 19CM048 - Arpit Patil");
        System.out.println(" 19CM050 - Shruti Raspayle");
        System.out.println(" 19CM051 - Vaibhav Rathod");
        System.out.println(" 19CM053 - Wruddhi Raut");
        System.out.println(" 19CM028 - Rohan Ingle" );
        back();
        run();
    }

    public void run() {
        banner();
        System.out.print(ANSI_CYAN
                + "  1. List All\n  2. Create\n  3. Copy\n  4. Move\n  5. Rename\n  6. Delete\n  7. Free Space\n  8. Check Size\n  9. About\n  0. Exit\n\n  "
                + ANSI_YELLOW + "Choose an Option: " + ANSI_RESET);
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                listAll();
                break;

            case "2":
                create();
                break;

            case "3":
                copy();
                break;

            case "4":
                move();
                break;

            case "5":
                rename();
                break;

            case "6":
                delete();
                break;

            case "7":
                freeSpace();
                break;

            case "8":
                checkSize();
                break;

            case "9":
                about();
                break;

            case "0":
                clear();
                break;

            default:
                banner();
                System.out.println(" Invalid Option!");
                back();
                run();
                break;
        }
    }
}

class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public Scanner scan = new Scanner(System.in);

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void banner() {
        clear();
        System.out.println(ANSI_RED + "-------------------------------------");
        System.out.println("\t    File Manager");
        System.out.println("-------------------------------------" + ANSI_RESET);
        System.out.println();
    }

    public void back() {
        System.out.print(ANSI_YELLOW + "\n Press \"ENTER\" to Back..." + ANSI_RESET);
        scan.nextLine();
    }

}