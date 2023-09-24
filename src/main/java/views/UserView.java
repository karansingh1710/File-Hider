package views;

import dao.DataDAO;
import dao.UserDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    // home method
    public  void home(String email){
        this.email=email;
        Scanner sc=new Scanner(System.in);
        do {
            System.out.println("Welcome "+email);
            System.out.println("Press 1 to View Hidden Files");
            System.out.println("Press 2 to Hide Files");
            System.out.println("Press 3 to Un-hide Files");
            System.out.println("Press 0 to exit");
            int ch=Integer.parseInt(sc.nextLine());

            switch (ch){
                case 1 ->{
                    try {
                        List<Data> hiddenFiles=DataDAO.getFiles(this.email);
                        for (Data data : hiddenFiles){
                            System.out.println(data.getId()+" "+data.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 ->{
                    try {
                        System.out.println("Enter the File path");
                        String path=sc.nextLine();
                        File file=new File(path);
                        System.out.println(file.getName());
                        Data data=new Data(0,file.getName(),path,this.email);
                        DataDAO.hideFile(data);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                case 3 ->{
                    List<Data> hiddenFiles=null;
                    try {
                         hiddenFiles=DataDAO.getFiles(email);
                        for (Data data : hiddenFiles){
                            System.out.println(data.getId()+" "+data.getFileName());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("Enter the id to unHide the file");
                    int id=Integer.parseInt(sc.nextLine());
                    // Check if the given id is valid or not
                    boolean isValid=false;
                    assert hiddenFiles != null;
                    for (Data data: hiddenFiles){
                        if (data.getId()==id){
                            isValid=true;
                            break;
                        }
                    }
                    if (isValid) {
                        try {
                            DataDAO.unHide(id);
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Wrong Id");
                    }
                }

                case 0 -> System.exit(0);
            }
        }while (true);


    }
}
