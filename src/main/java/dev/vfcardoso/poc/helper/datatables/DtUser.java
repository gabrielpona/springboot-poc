package dev.vfcardoso.poc.helper.datatables;

import dev.vfcardoso.poc.helper.arch.utility.ConvertUtils;
import dev.vfcardoso.poc.infra.annotation.DtExportType;

import java.util.Objects;

public class DtUser implements DtWrapper{

    @DtExportType(Long.class) private Long DT_RowId;
    @DtExportType(String.class) private String status;
    @DtExportType(String.class) private String nome;
    @DtExportType(String.class) private String login;
    @DtExportType(String.class) private String email;
    //@DtExportType(String.class) private String papel;


    public DtUser() {
    }


    public DtUser(Object[] rawObjects) {

        this.DT_RowId = rawObjects[0] == null ? 0L : ConvertUtils.convertTo(rawObjects[0], Long.class);
        this.status = rawObjects[1] == null ? "" : ConvertUtils.convertTo(rawObjects[1], String.class);
        this.nome = rawObjects[2] == null ? "" : ConvertUtils.convertTo(rawObjects[2], String.class);
        this.login = rawObjects[3] == null ? "" : ConvertUtils.convertTo(rawObjects[3], String.class);
        this.email = rawObjects[4] == null ? "" : ConvertUtils.convertTo(rawObjects[4], String.class);
        //this.papel = rawObjects[5] == null ? "" : ConvertUtils.convertTo(rawObjects[5], String.class);

    }

    @Override
    public boolean isEmpty() {
        return (DT_RowId == null || DT_RowId == 0) &&
                (status == null || Objects.equals(status, "")) &&
                (nome == null || Objects.equals(nome, "")) &&
                (login == null || Objects.equals(login, "")) &&
                (email == null || Objects.equals(email, "")); //&&
                //(papel == null || Objects.equals(papel, ""));
    }

    public Long getDT_RowId() {
        return DT_RowId;
    }

    public void setDT_RowId(Long DT_RowId) {
        this.DT_RowId = DT_RowId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
     */



}