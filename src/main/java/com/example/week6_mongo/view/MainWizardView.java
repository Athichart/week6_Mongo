package com.example.week6_mongo.view;

import com.example.week6_mongo.pojo.Wizard;
import com.example.week6_mongo.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@Route(value = "/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField name, money;
    private RadioButtonGroup<String> gender;
    private ComboBox<String> position, school, house;
    private Button back, create, update, delete, next;
    private int index_num;
    private Wizards wizards;

    public MainWizardView(){
        name = new TextField();
        name.setPlaceholder("Fullname");
        money = new TextField();
//        money.setPlaceholder("$");
        money.setPrefixComponent(new Span("$"));
        gender = new RadioButtonGroup<String>();
        gender.setLabel("Gender :");
        gender.setItems("m", "f");
        position = new ComboBox<>("Position");
        position.setItems("Student", "Teacher");
        position.setPlaceholder("Position");
        school = new ComboBox<>();
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        school.setPlaceholder("School");
        house = new ComboBox<>();
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        house.setPlaceholder("House");
        HorizontalLayout hr_btn = new HorizontalLayout();
        back = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        next = new Button(">>");
        hr_btn.add(back,create,update,delete,next);
        this.add(name,gender,position,money,school,house,hr_btn);

        this.wizards = new Wizards();

        data_load();

        next.addClickListener(event ->{
            if(index_num + 1 >= this.wizards.model.size()){
                index_num = this.wizards.model.size()-1;
                show_data();
            }
            else{
                index_num += 1;
                show_data();
            }
        });

        back.addClickListener(event ->{
            if(index_num - 1 < 0){
                index_num = 0;
                show_data();
            }
            else {
                index_num -= 1;
                show_data();
            }
        });
        create.addClickListener(event ->{
            Wizard out = WebClient.create().post().uri("http://127.0.0.1:8080/addWizard?sex="+ gender.getValue() +"&name="+ name.getValue() +"&position="+position.getValue() +"&money="+ money.getValue() +"&school="+school.getValue()+"&house=" + house.getValue())
                    .retrieve().bodyToMono(Wizard.class).block();
            Notification noti = Notification.show("Wizard Has Been Create");
            noti.setPosition(Notification.Position.BOTTOM_START);
            data_load();
        });

        update.addClickListener(event ->{
            boolean out = WebClient.create().post().uri("http://127.0.0.1:8080/updateWizard?sex="+ gender.getValue() +"&name="+ name.getValue() +"&position="+position.getValue() +"&money="+ money.getValue() +"&school="+school.getValue()+"&house=" + house.getValue() + "&old_name="
                            + this.wizards.model.get(index_num).getName())
                    .retrieve().bodyToMono(Boolean.class).block();
            Notification noti = Notification.show("Wizard Has Been Update");
            noti.setPosition(Notification.Position.BOTTOM_START);
            data_load();
        });
        delete.addClickListener(event ->{
            boolean out = WebClient.create().post().uri("http://127.0.0.1:8080/deleteWizard?name=" + name.getValue())
                    .retrieve().bodyToMono(Boolean.class).block();
            Notification noti = Notification.show("Wizard Has Been Delete");
            noti.setPosition(Notification.Position.BOTTOM_START);
            data_load();
            show_data();
        });


    }
    private void data_load(){
        this.wizards.model = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ArrayList<Wizard>>() {})
                .block();
    }
    private void show_data(){
        if(this.wizards.model.size() != 0){
            this.name.setValue(this.wizards.model.get(index_num).getName());
            this.gender.setValue(this.wizards.model.get(index_num).getSex());
            this.money.setValue(this.wizards.model.get(index_num).getMoney());
            this.position.setValue(this.wizards.model.get(index_num).getPosition());
            this.school.setValue(this.wizards.model.get(index_num).getSchool());
            this.house.setValue(this.wizards.model.get(index_num).getHouse());
        }
        else{
            this.name.setValue("");
            this.gender.setValue("m");
            this.money.setValue("");
            this.position.setValue("");
            this.school.setValue("");
            this.house.setValue("");
        }
    }



}
