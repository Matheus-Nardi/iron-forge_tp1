    package br.unitins.tp1.ironforge.model.whey.tabelanutricional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
@Entity
public class Food extends DefaultEntity {
    private String food_name;
    private String brand_name;
    private int serving_qty;
    private String serving_unit;
    private int serving_weight_grams;
    private int nf_metric_qty;
    private String nf_metric_uom;
    private int nf_calories;
    private int nf_total_fat;
    private String nf_saturated_fat;
    private String nf_cholesterol;
    private int nf_sodium;
    private int nf_total_carbohydrate;
    private String nf_dietary_fiber;
    private String nf_sugars;
    private int nf_protein;
    private String nf_potassium;
    private String nf_p;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_food")
    private List<FullNutrient> full_nutrients;
    private String nix_brand_name;
    private String nix_brand_id;
    private String nix_item_name;
    private String nix_item_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_metadata")
    private Metadata metadata;

    private int source;
    private String ndb_no;
    private String tags;
    private String alt_measures;
    private String lat;
    private String lng;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_photo")
    private Photo photo;

    private String note;
    private String class_code;
    private String brick_code;
    private String tag_id;
    private Date updated_at;
    private String nf_ingredient_statement;

    public Food() {
        this.full_nutrients = new ArrayList<>();
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getServing_qty() {
        return serving_qty;
    }

    public void setServing_qty(int serving_qty) {
        this.serving_qty = serving_qty;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public void setServing_unit(String serving_unit) {
        this.serving_unit = serving_unit;
    }

    public int getServing_weight_grams() {
        return serving_weight_grams;
    }

    public void setServing_weight_grams(int serving_weight_grams) {
        this.serving_weight_grams = serving_weight_grams;
    }

    public int getNf_metric_qty() {
        return nf_metric_qty;
    }

    public void setNf_metric_qty(int nf_metric_qty) {
        this.nf_metric_qty = nf_metric_qty;
    }

    public String getNf_metric_uom() {
        return nf_metric_uom;
    }

    public void setNf_metric_uom(String nf_metric_uom) {
        this.nf_metric_uom = nf_metric_uom;
    }

    public int getNf_calories() {
        return nf_calories;
    }

    public void setNf_calories(int nf_calories) {
        this.nf_calories = nf_calories;
    }

    public int getNf_total_fat() {
        return nf_total_fat;
    }

    public void setNf_total_fat(int nf_total_fat) {
        this.nf_total_fat = nf_total_fat;
    }

    public String getNf_saturated_fat() {
        return nf_saturated_fat;
    }

    public void setNf_saturated_fat(String nf_saturated_fat) {
        this.nf_saturated_fat = nf_saturated_fat;
    }

    public String getNf_cholesterol() {
        return nf_cholesterol;
    }

    public void setNf_cholesterol(String nf_cholesterol) {
        this.nf_cholesterol = nf_cholesterol;
    }

    public int getNf_sodium() {
        return nf_sodium;
    }

    public void setNf_sodium(int nf_sodium) {
        this.nf_sodium = nf_sodium;
    }

    public int getNf_total_carbohydrate() {
        return nf_total_carbohydrate;
    }

    public void setNf_total_carbohydrate(int nf_total_carbohydrate) {
        this.nf_total_carbohydrate = nf_total_carbohydrate;
    }

    public String getNf_dietary_fiber() {
        return nf_dietary_fiber;
    }

    public void setNf_dietary_fiber(String nf_dietary_fiber) {
        this.nf_dietary_fiber = nf_dietary_fiber;
    }

    public String getNf_sugars() {
        return nf_sugars;
    }

    public void setNf_sugars(String nf_sugars) {
        this.nf_sugars = nf_sugars;
    }

    public int getNf_protein() {
        return nf_protein;
    }

    public void setNf_protein(int nf_protein) {
        this.nf_protein = nf_protein;
    }

    public String getNf_potassium() {
        return nf_potassium;
    }

    public void setNf_potassium(String nf_potassium) {
        this.nf_potassium = nf_potassium;
    }

    public String getNf_p() {
        return nf_p;
    }

    public void setNf_p(String nf_p) {
        this.nf_p = nf_p;
    }

    public String getNix_brand_name() {
        return nix_brand_name;
    }

    public void setNix_brand_name(String nix_brand_name) {
        this.nix_brand_name = nix_brand_name;
    }

    public String getNix_brand_id() {
        return nix_brand_id;
    }

    public void setNix_brand_id(String nix_brand_id) {
        this.nix_brand_id = nix_brand_id;
    }

    public String getNix_item_name() {
        return nix_item_name;
    }

    public void setNix_item_name(String nix_item_name) {
        this.nix_item_name = nix_item_name;
    }

    public String getNix_item_id() {
        return nix_item_id;
    }

    public void setNix_item_id(String nix_item_id) {
        this.nix_item_id = nix_item_id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getNdb_no() {
        return ndb_no;
    }

    public void setNdb_no(String ndb_no) {
        this.ndb_no = ndb_no;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAlt_measures() {
        return alt_measures;
    }

    public void setAlt_measures(String alt_measures) {
        this.alt_measures = alt_measures;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getBrick_code() {
        return brick_code;
    }

    public void setBrick_code(String brick_code) {
        this.brick_code = brick_code;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getNf_ingredient_statement() {
        return nf_ingredient_statement;
    }

    public void setNf_ingredient_statement(String nf_ingredient_statement) {
        this.nf_ingredient_statement = nf_ingredient_statement;
    }

    public List<FullNutrient> getFull_nutrients() {
        return full_nutrients;
    }

    public void setFull_nutrients(List<FullNutrient> full_nutrients) {
        this.full_nutrients = full_nutrients;
    }

}
