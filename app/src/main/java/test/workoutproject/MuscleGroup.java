package test.workoutproject;

/**
 * Created by Pépère on 11/06/2017.
 */

public class MuscleGroup {
    private int _id;
    private String _name;


    public MuscleGroup(){
        this._name = null;
    }
    public MuscleGroup(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
