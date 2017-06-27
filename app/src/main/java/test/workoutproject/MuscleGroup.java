package test.workoutproject;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Pépère on 11/06/2017.
 * Classe contenant une liste de groupes musculaires, je ne sais pas si elle est vraiment utile... Mais ça m'amuse.
 */

public class MuscleGroup {
    private int _id;
    private String _name;
    private HashMap<String,Integer> groups = new HashMap<>();

    public MuscleGroup(){
        this._name = null;
        initGroups();
    }
    public MuscleGroup(String _name) {
        this._name = _name;
        initGroups();
        this._id = find_id(_name);
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    private void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
        this._id = find_id(_name);
    }

    private void initGroups(){
        groups.put("Bras",0);
        groups.put("Epaules",1);
        groups.put("Pectoraux",2);
        groups.put("Dos",3);
        groups.put("Abdominaux",4);
        groups.put("Fessiers",5);
        groups.put("Jambes",6);
    }
    private int find_id(String name){
        if (groups.containsKey(name)){
            return groups.get(name);
        }
        else{
            Log.i("MuscleGroupError","No muscle group of name "+name+" found.");
            return -1;
        }
    }
}
