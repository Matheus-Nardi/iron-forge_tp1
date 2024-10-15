
package com.example.models;

import java.util.ArrayList;
import java.util.Date;

public class Food {
    public String food_name;
    public String brand_name;
    public int serving_qty;
    public String serving_unit;
    public int serving_weight_grams;
    public int nf_metric_qty;
    public String nf_metric_uom;
    public int nf_calories;
    public int nf_total_fat;
    public Object nf_saturated_fat;
    public Object nf_cholesterol;
    public int nf_sodium;
    public int nf_total_carbohydrate;
    public Object nf_dietary_fiber;
    public Object nf_sugars;
    public int nf_protein;
    public Object nf_potassium;
    public Object nf_p;
    public ArrayList<FullNutrient> full_nutrients;
    public String nix_brand_name;
    public String nix_brand_id;
    public String nix_item_name;
    public String nix_item_id;
    public Metadata metadata;
    public int source;
    public Object ndb_no;
    public Object tags;
    public String alt_measures;
    public Object lat;
    public Object lng;
    public Photo photo;
    public Object note;
    public Object class_code;
    public Object brick_code;
    public Object tag_id;
    public Date updated_at;
    public String nf_ingredient_statement;
    @Override
    public String toString() {
        return "Food [food_name=" + food_name + ", brand_name=" + brand_name + ", serving_qty=" + serving_qty
                + ", serving_unit=" + serving_unit + ", serving_weight_grams=" + serving_weight_grams
                + ", nf_metric_qty=" + nf_metric_qty + ", nf_metric_uom=" + nf_metric_uom + ", nf_calories="
                + nf_calories + ", nf_total_fat=" + nf_total_fat + ", nf_saturated_fat=" + nf_saturated_fat
                + ", nf_cholesterol=" + nf_cholesterol + ", nf_sodium=" + nf_sodium + ", nf_total_carbohydrate="
                + nf_total_carbohydrate + ", nf_dietary_fiber=" + nf_dietary_fiber + ", nf_sugars=" + nf_sugars
                + ", nf_protein=" + nf_protein + ", nf_potassium=" + nf_potassium + ", nf_p=" + nf_p
                + ", full_nutrients=" + full_nutrients + ", nix_brand_name=" + nix_brand_name + ", nix_brand_id="
                + nix_brand_id + ", nix_item_name=" + nix_item_name + ", nix_item_id=" + nix_item_id + ", metadata="
                + metadata + ", source=" + source + ", ndb_no=" + ndb_no + ", tags=" + tags + ", alt_measures="
                + alt_measures + ", lat=" + lat + ", lng=" + lng + ", photo=" + photo + ", note=" + note
                + ", class_code=" + class_code + ", brick_code=" + brick_code + ", tag_id=" + tag_id + ", updated_at="
                + updated_at + ", nf_ingredient_statement=" + nf_ingredient_statement + "]";
    }

  
    

}
