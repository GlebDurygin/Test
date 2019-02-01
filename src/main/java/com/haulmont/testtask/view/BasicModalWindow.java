package com.haulmont.testtask.view;

import com.vaadin.ui.*;

public abstract class BasicModalWindow<T> extends Window {

    protected Button okButton;
    protected Button cancelButton;
    protected VerticalLayout mainLayout;
    protected FormLayout formLayout;
    protected HorizontalLayout buttonsLayout;

    public BasicModalWindow() {
        setStyleName(MainThemeConstants.MODAL_WINDOW);
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setMargin(false);
        formLayout.setSpacing(true);

        okButton = new Button("ОК");
        cancelButton = new Button("Отменить");
        cancelButton.addClickListener(event -> close());

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(okButton, cancelButton);
        buttonsLayout.setSpacing(false);
        mainLayout.addComponents(formLayout, buttonsLayout);
        mainLayout.setExpandRatio(formLayout, 7.0f);
        mainLayout.setExpandRatio(buttonsLayout, 1.0f);
        constructForm();
    }

    abstract protected void constructForm();

    abstract protected T convertFormToObject(Long id);

    abstract protected T convertFormToObject();
}
