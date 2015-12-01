package com.sdl.demo.login;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.bootstrap.window.Window;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;
import com.sdl.selenium.web.form.TextField;

public class ChangePasswordWindow extends Window {

    public ChangePasswordWindow() {
        this("Change Password");
    }

    public ChangePasswordWindow(String title) {
        super(title);
    }

    private TextField currentPassField = new TextField(this).setLabel("Current Password");
    private TextField newPassField = new TextField(this).setLabel("New Password");
    private TextField confirmPassField = new TextField(this).setLabel("Repeat Password");

    private Button saveButton = new Button(this).setText("Save");

    private WebLocator statusMsgElement = new WebLocator(this).setClasses("status-msg");

    public void changePassword(String currentPass, String newPass, String confirmPass) {
        currentPassField.setValue(currentPass);
        newPassField.setValue(newPass);
        confirmPassField.setValue(confirmPass);
        saveButton.assertClick();
    }

    public static void main(String[] args) {
        WebLocatorUtils.getXPathScript(new ChangePasswordWindow());
    }
}
