package com.haulmont.testtask.page;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import java.sql.Date;

public class ModalStudentWindow extends BasicModalWindow {
    private TextField lastNameTextField;
    private TextField firstNameTextField;
    private TextField middleNameTextField;
    private DateField birthDateField;
    private ComboBox groupComboBox;

    public ModalStudentWindow() {
        super();
        setModal(true);
        setWidth("500px");
        setHeight("500px");
        setResizable(false);
        setDraggable(false);
        setCaption("Добавление студента");
    }

    @Override
    protected void constructForm() {
        lastNameTextField = new TextField("Фамилия");
        lastNameTextField.setRequired(true);
        lastNameTextField.addValidator(new RegexpValidator("^\\d{1,10}$",true,"Фамилия может содержать только буквы"));
        firstNameTextField = new TextField("Имя");
        firstNameTextField.setRequired(true);
        firstNameTextField.addValidator(new RegexpValidator("^\\d{1,10}$",true,"Имя может содержать только буквы"));
        middleNameTextField = new TextField("Отчество (если есть)");
        middleNameTextField.addValidator(new RegexpValidator("^\\d{1,10}$",true,"Отчество может содержать только буквы"));
        birthDateField = new DateField("Дата рождения");
        birthDateField.setRequired(true);
        groupComboBox = new ComboBox("Номер группы");
        formLayout.addComponents(lastNameTextField,firstNameTextField,middleNameTextField,birthDateField,groupComboBox);

    }

    @Override
    protected void pressOnButtonOk() {

    }

    @Override
    protected void pressOnButtonCancel() {

    }
}
