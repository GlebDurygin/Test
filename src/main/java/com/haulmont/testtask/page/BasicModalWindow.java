package com.haulmont.testtask.page;

import com.vaadin.ui.*;

public abstract class BasicModalWindow extends Window {

    protected Button okButton;
    protected Button cancelButton;
    protected VerticalLayout mainLayout;
    protected FormLayout formLayout;
    protected HorizontalLayout buttonsLayout;

    public BasicModalWindow() {
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(false);

        formLayout = new FormLayout();

        okButton = new Button("ОК");
        okButton.addClickListener(event -> pressOnButtonOk());
        cancelButton = new Button("Отменить");
        cancelButton.addClickListener(event -> pressOnButtonCancel());

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(okButton,cancelButton);
        mainLayout.addComponents(formLayout,buttonsLayout);
        setContent(mainLayout);
        constructForm();
    }

    abstract protected void constructForm();

    abstract protected void pressOnButtonOk();

    abstract protected void pressOnButtonCancel();
}
