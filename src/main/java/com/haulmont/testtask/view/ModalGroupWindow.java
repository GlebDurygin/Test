package com.haulmont.testtask.view;

import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.service.GroupService;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.*;

public class ModalGroupWindow extends BasicModalWindow<Group> {
    private TextField facultyTextField;
    private TextField numberTextField;

    private GroupService groupService;

    public ModalGroupWindow() {
        super();
        groupService = GroupService.getInstance();
        setCaption("Добавление группы");

        okButton.addClickListener(event -> {
            if (validate()) {
                if (groupService.insertGroup(convertFormToObject())) {
                    close();
                } else {
                    Notification.show("Такая группа уже существует", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }

    public ModalGroupWindow(Item item, Long id) {
        super();
        groupService = GroupService.getInstance();
        setCaption("Редактирование группы");

        facultyTextField.setValue(String.valueOf(item.getItemProperty(TABLE_GROUP_FACULTY).getValue()));
        numberTextField.setValue(String.valueOf(item.getItemProperty(TABLE_GROUP_NUMBER).getValue()));

        okButton.addClickListener(event -> {
            if (validate()) {
                groupService.updateGroup(convertFormToObject(id));
                close();
            }
        });
    }

    @Override
    protected void constructForm() {
        setModal(true);
        setWidth("500px");
        setHeight("300px");
        setResizable(false);
        setDraggable(false);

        initFacultyTextField();
        initNumberTextField();

        formLayout.addComponents(facultyTextField, numberTextField);
        setContent(mainLayout);
    }

    private void initFacultyTextField() {
        facultyTextField = new TextField("Название факультета");
        facultyTextField.setRequired(true);
        facultyTextField.setWidth("100%");
        facultyTextField.setRequiredError("Введите название факультета (не более 30 символов)");
        facultyTextField.setMaxLength(30);
        facultyTextField.setImmediate(true);
        facultyTextField.addValidator(new StringLengthValidator("Не более 30 символов", 1, 30, false));
    }

    private void initNumberTextField() {
        numberTextField = new TextField("Номер группы");
        numberTextField.setRequired(true);
        numberTextField.setWidth("100%");
        numberTextField.setRequiredError("Введите номер группы (не более 5 чисел)");
        numberTextField.setMaxLength(30);
        numberTextField.setImmediate(true);
        numberTextField.addValidator(new RegexpValidator("^\\d{1,5}$", false, "Номер группы должен содержать не болле 5 чисел"));
    }

    @Override
    protected Group convertFormToObject(Long id) {
        return new Group(id, Integer.parseInt(numberTextField.getValue()), facultyTextField.getValue());
    }

    @Override
    protected Group convertFormToObject() {
        Group group = new Group();
        group.setNumber(Integer.parseInt(numberTextField.getValue()));
        group.setFaculty(facultyTextField.getValue());
        return group;
    }

    private boolean validate() {
        try {
            facultyTextField.validate();
            numberTextField.validate();
        } catch (Validator.InvalidValueException e) {
            Notification.show("Данные введены некорректно", Notification.Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
