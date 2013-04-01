package com.example.nfcandroid;

import java.util.ArrayList;

public class EditableInformationPersona {
	private String Name;
	public String getName() {return Name;}
	public void setName(String name) {Name = name;}

	private String Pront;
	public String getPront() {return Pront;}
	public void setPront(String pront) {Pront = pront;}
	
	private String Value;
	public String getValue() {return Value;}
	public void setValue(String value) {Value = value;}
	
	private int Id;
	public int getId() {return Id;}
	public void setId(int id) {Id = id;}
	
	public EditableInformationPersona(String Pront, String Name, String Value, int Id) {
		this.setName(Name);
		this.setPront(Pront);
		this.setValue(Value);
		this.setId(200200 + Id);
	}

	public static ArrayList<EditableInformationPersona> GetEditableInformation(){
		ArrayList<EditableInformationPersona> editable_information= new ArrayList<EditableInformationPersona>();
		editable_information.add(new EditableInformationPersona("Email", "Email","", 1));
		editable_information.add(new EditableInformationPersona("Telefono","TelefonoResidencial","", 2));
		editable_information.add(new EditableInformationPersona("Celular","TelefonoCelular","", 3));
		editable_information.add(new EditableInformationPersona("Direccion","Direccion","", 4));
		return editable_information;
	}



}
