package com.second.or.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Controller
public class OperRedirectController {
    @GetMapping("/")
    public String index(){return "redirect:/payment";}
    @GetMapping("/payment")
    public String payment(){
        return "or-payment";
    }

    //1111111111111111
    //Ara Ra
    @PostMapping("/payment")
    public String payment(Model model,
                          @RequestParam("cardnumber") String cardnumber,
                          @RequestParam("owner") String owner,
                          @RequestParam("cvv") String cvv){
        if(cardnumber.isEmpty() || owner.isEmpty() || cvv.isEmpty()){
            model.addAttribute("message", "Please fill all inputs");
            return "or-payment";
        }
        if(!cardnumber.matches("^[0-9]{16}$")){
            model.addAttribute("message", "Wrong card format");
            return "or-payment";
        }
        if(!owner.matches("^[A-Z][a-z]+\\s[A-Z][a-z]+$")){
            model.addAttribute("message", "Wrong owner format");
            return "or-payment";
        }
        if(!cvv.matches("^[0-9]{3}$")){
            model.addAttribute("message", "Wrong cvv format");
            return "or-payment";
        }
        model.addAttribute("message", "Your payment is being processed");
        save(cardnumber, owner, cvv);
        return "or-payment";
    }

    private void save(String cardnumber, String owner, String cvv){
        String filename = "vault.txt";
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(cardnumber+":"+owner+":"+cvv);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
