package com.haulmont.testtask.page;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.TextField;

public class ModalGroupWindow extends BasicModalWindow {
    private TextField facultyTextField;
    private TextField numberTextField;

    public ModalGroupWindow() {
        super();
        setModal(true);
        setWidth("500px");
        setHeight("300px");
        setResizable(false);
        setDraggable(false);
        setCaption("Добавление группы");
    }

    @Override
    protected void constructForm() {
        facultyTextField = new TextField("Название факультета");
        facultyTextField.setRequired(true);
        facultyTextField.addValidator(new StringLengthValidator("Не более 30 символов",1,30,true));
        numberTextField = new TextField("Номер группы");
        numberTextField.setRequired(true);
        numberTextField.addValidator(new RegexpValidator("^\\d{1,10}$",true,"Номер группы должен содержать не болле 10 чисел"));
        formLayout.addComponents(facultyTextField,numberTextField);
    }

    @Override
    protected void pressOnButtonOk() {

    }

    @Override
    protected void pressOnButtonCancel() {

    }
}
