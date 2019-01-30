package com.haulmont.testtask.page;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import static com.haulmont.testtask.page.PageConstants.*;

public class MainView extends VerticalLayout implements View {
    private Navigator navigator;
    private Button goToMainGroupView;
    private Button goToMainStudentView;

    public MainView() {
        Button.ClickListener listener = (Button.ClickListener) event -> navigator.navigateTo(event.getButton().getData().toString());
        goToMainGroupView = new Button("Отображение списка групп");
        goToMainGroupView.setData(GROUPS_PAGE);
        goToMainGroupView.addClickListener(listener);
        goToMainGroupView.setStyleName(ValoTheme.BUTTON_LINK);
        goToMainStudentView = new Button("Отображение списка студентов");
        goToMainStudentView.setData(STUDENTS_PAGE);
        goToMainStudentView.addClickListener(listener);
        goToMainStudentView.setStyleName(ValoTheme.BUTTON_LINK);
        addComponents(goToMainGroupView, goToMainStudentView);
        setMargin(true);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
