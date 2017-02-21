package com.example.nelioeasynet.model;

import java.io.Serializable;

/**
 * Created by nelio.easynet on 18/01/2017.
 */

public class Produto implements Serializable {

    private int ID;
    private String code;
    private String desc;
    private String unidMedida;
    private String qtd;

    public Produto(){

    }
    public Produto(String code, String desc, String unidMedida, String qtd){
        this.code = code;
        this.desc = desc;
        this.unidMedida = unidMedida;
        this.qtd = qtd;

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Produto (String code){
        this.code = code;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }
}