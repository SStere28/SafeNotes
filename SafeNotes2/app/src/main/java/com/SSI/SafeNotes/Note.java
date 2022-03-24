package com.SSI.SafeNotes;


public class Note {

    //private variables
    int id;
    String titlu;
    String text;
    String modified;
    String size;
    String type;

    // Empty constructor
    public Note(){

    }
    // constructor
    public Note(int id, String titlu, String text,String modified, String size, String type){
        this.id = id;
        this.titlu = titlu;
        this.text = text;
        this.modified = modified;
        this.size=size;
        this.type=type;
    }

    // constructor
    public Note(String titlu, String text,String modified , String size, String type){

        this.titlu = titlu;
        this.text = text;
        this.modified = modified;
        this.size=size;
        this.type=type;
    }

    // constructor
    public Note(int id, String titlu, String text,String modified){
        this.id = id;
        this.titlu = titlu;
        this.text = text;
        this.modified = modified;

    }

    // constructor
    public Note(String titlu, String text,String modified ){

        this.titlu = titlu;
        this.text = text;
        this.modified = modified;

    }
    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // getting name
    public String getTitlu(){
        return this.titlu;
    }

    // setting name
    public void setTitlu(String titlu){
        this.titlu = titlu;
    }

    // getting phone number
    public String getText(){
        return this.text;
    }

    // setting phone number
    public void setText(String text){
        this.text = text;
    }

    // getting phone number
    public String getModified(){
        return this.modified;
    }

    // setting phone number
    public void setModified(String modified){
        this.modified = modified;
    }

    public String  getSize(){return this.size;}

    public void setSize(String size){this.size=size;}

    public String  getType(){return this.type;}

    public void setType(String type){this.type=type;}

}