package com.haulmont.testtask.view;

import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.model.entity.Student;
import com.haulmont.testtask.service.GroupService;
import com.haulmont.testtask.service.StudentService;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import java.sql.Date;
import java.util.List;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.*;

public class ModalStudentWindow extends BasicModalWindow<Student> {
    private TextField lastNameTextField;
    private TextField firstNameTextField;
    private TextField middleNameTextField;
    private DateField birthDateField;
    private ComboBox groupComboBox;

    private StudentService studentService;
    private GroupService groupService;

    public ModalStudentWindow() {
        super();
        studentService = StudentService.getInstance();
        groupService = GroupService.getInstance();
        setCaption("Добавление студента");
        fillGroupComboBox(null);

        okButton.addClickListener(event -> {
            if (validate()) {
                if (studentService.insertStudent(convertFormToObject())) {
                    close();
                } else {
                    Notification.show("Такой студент уже существует", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

    }

    public ModalStudentWindow(Item item, Long id) {
        super();
        studentService = StudentService.getInstance();
        groupService = GroupService.getInstance();
        setCaption("Редактирование студента");

        lastNameTextField.setValue(String.valueOf(item.getItemProperty(TABLE_STUDENT_LAST_NAME).getValue()));
        firstNameTextField.setValue(String.valueOf(item.getItemProperty(TABLE_STUDENT_FIRST_NAME).getValue()));
        middleNameTextField.setValue(String.valueOf(item.getItemProperty(TABLE_STUDENT_MIDDLE_NAME).getValue()));
        birthDateField.setValue((Date) (item.getItemProperty(TABLE_STUDENT_BIRTHDATE).getValue()));
        fillGroupComboBox((Integer) item.getItemProperty(TABLE_GROUP_NUMBER).getValue());

        okButton.addClickListener(event -> {
            if (validate()) {
                studentService.updateStudent(convertFormToObject(id));
                close();
            }
        });
    }

    @Override
    protected void constructForm() {
        setModal(true);
        setWidth("500px");
        setHeight("500px");
        setResizable(false);
        setDraggable(false);

        initLastNameTextField();
        initFirstNameTextField();
        initMiddleNameTextField();
        initBirthDateField();
        initGroupComboBox();

        formLayout.addComponents(lastNameTextField, firstNameTextField, middleNameTextField, birthDateField, groupComboBox);
        setContent(mainLayout);
    }

    private void initLastNameTextField() {
        lastNameTextField = new TextField("Фамилия");
        lastNameTextField.setRequired(true);
        lastNameTextField.setWidth("100%");
        lastNameTextField.setRequiredError("Введите фамилию");
        lastNameTextField.setMaxLength(30);
        lastNameTextField.setImmediate(true);
        lastNameTextField.addValidator(new RegexpValidator("^[a-zA-ZА-ЯЁа-яё]{1,30}$", false, "Фамилия может содержать только буквы"));
    }

    private void initFirstNameTextField() {
        firstNameTextField = new TextField("Имя");
        firstNameTextField.setRequired(true);
        firstNameTextField.setWidth("100%");
        firstNameTextField.setRequiredError("Введите имя");
        firstNameTextField.setMaxLength(30);
        firstNameTextField.setImmediate(true);
        firstNameTextField.addValidator(new RegexpValidator("^[a-zA-ZА-ЯЁа-яё]{1,30}$", false, "Имя может содержать только буквы"));
    }

    private void initMiddleNameTextField() {
        middleNameTextField = new TextField("Отчество (если есть)");
        middleNameTextField.setWidth("100%");
        middleNameTextField.setMaxLength(30);
        middleNameTextField.setImmediate(true);
        middleNameTextField.addValidator(new RegexpValidator("^[a-zA-ZА-ЯЁа-яё]{1,30}$", false, "Отчество может содержать только буквы"));
    }

    private void initBirthDateField() {
        birthDateField = new DateField("Дата рождения");
        birthDateField.setRequired(true);
        birthDateField.setWidth("100%");
        birthDateField.setRequiredError("Укажите дату рождения");
        birthDateField.setDateFormat("dd.MM.yyyy");
    }

    private void initGroupComboBox() {
        groupComboBox = new ComboBox("Номер группы");
        groupComboBox.setRequired(true);
        groupComboBox.setImmediate(true);
        groupComboBox.setRequiredError("Укажите группу");
        groupComboBox.addValidator(new NullValidator("Укажите группу", false));
        groupComboBox.setNullSelectionAllowed(false);
        groupComboBox.setTextInputAllowed(false);
        groupComboBox.setWidth("100%");
    }

    private void fillGroupComboBox(Integer number) {
        groupComboBox.removeAllItems();
        List<Group> groups = groupService.getGroups();
        for (Group group : groups) {
            groupComboBox.addItem(group);
            groupComboBox.setItemCaption(group, group.getNumber().toString());
            if (number == null) {
                groupComboBox.select(group);
            } else if (number.equals(group.getNumber())) {
                groupComboBox.select(group);
            }
        }
    }

    @Override
    protected Student convertFormToObject(Long id) {
        Group group = (Group) groupComboBox.getValue();
        return new Student(id, firstNameTextField.getValue(), lastNameTextField.getValue(),
                middleNameTextField.getValue(), new Date(birthDateField.getValue().getTime()), group.getId());
    }

    @Override
    protected Student convertFormToObject() {
        Group group = (Group) groupComboBox.getValue();
        Student student = new Student();
        student.setFirstName(firstNameTextField.getValue());
        student.setLastName(lastNameTextField.getValue());
        student.setMiddleName(middleNameTextField.getValue());
        student.setBirthDate(new Date(birthDateField.getValue().getTime()));
        student.setGroupId(group.getId());
        return student;
    }

    private boolean validate() {
        try {
            firstNameTextField.validate();
            lastNameTextField.validate();
            middleNameTextField.validate();
            birthDateField.validate();
            groupComboBox.validate();
        } catch (Validator.InvalidValueException e) {
            Notification.show("Данные введены некорректно", Notification.Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
