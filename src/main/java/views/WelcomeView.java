package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WelcomeView {
    // This method will greet the user with a message
    public void welcomeScreen(){
        Scanner sc=new Scanner(System.in);
        int choice=0;
        System.out.println("Welcome to the App");
        System.out.println("Press 1 To LogIn");
        System.out.println("Press 2 To SignUp");
        System.out.println("Press 3 To Exit");
        /*
        Here the do-while() loop is used for handle the exceptions
        if the user enters wrong data type then it will handle the exception
         */
        do {
            try {
                choice=sc.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Please Enter Only Numbers");
                sc.nextLine();
            }
        }while (true);

        switch (choice){
            case 1-> logIn();
            case 2 -> signUp();
            case 3 -> {
                System.out.println("Exiting Successfully ...........");
                System.exit(0);
            }
            default -> System.out.println("Wrong Choice");
        }
    }

    private static void signUp() {
        Scanner sc=new Scanner(System.in);
        String email;
        String name;
        System.out.println("Enter Your Name");
        name=sc.nextLine();
        System.out.println("Enter Your Mail");
        email=sc.nextLine();
        String generatedOTP=GenerateOTP.getOTP();
        SendOTPService.sendOTP(email,generatedOTP);
        System.out.println("OTP successfully Sent");
        System.out.println("Enter Your OTP");
        String otp=sc.nextLine();

           if (otp.equals(generatedOTP)){
                User user=new User(name,email);
               Integer response= UserService.saveUser(user);
                switch (response){
                    case 0->  System.out.println("No need to signUp Already Registered");
                    case 1-> System.out.println("User Registered Successfully");
                }
            }else{
                System.out.println("email Not valid");
            }

    }

    private static void logIn() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Your Email");
        String email=sc.nextLine();
        try {
            if (UserDAO.userExists(email)){
                String generatedOTP=GenerateOTP.getOTP();
                SendOTPService.sendOTP(email,generatedOTP);
                System.out.println("OTP successfully Sent");
                System.out.println("Enter Your OTP");
                String otp=sc.nextLine();
                if (generatedOTP.equals(otp)){
                    UserView userView=new UserView();
                    userView.home(email);
                }else{
                    System.out.println("OTP Not matched");
                }
            }else{
                System.out.println("User Not Found");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}

