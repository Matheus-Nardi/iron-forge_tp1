package br.unitins.tp1.ironforge.model.whey.tabelanutricional;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Entity;

@Entity
public class Photo extends DefaultEntity {
    public String thumb;
    public String highres;
    public boolean is_user_uploaded;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public boolean isIs_user_uploaded() {
        return is_user_uploaded;
    }

    public void setIs_user_uploaded(boolean is_user_uploaded) {
        this.is_user_uploaded = is_user_uploaded;
    }

    public String getHighres() {
        return highres;
    }

    public void setHighres(String highres) {
        this.highres = highres;
    }

}