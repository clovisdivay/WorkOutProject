package test.workoutproject;

/**
 * Created by Pépère on 11/06/2017.
 */

public class Muscle {

    private int _id;
    private String _name;
    private int _groupID;


    public Muscle() {
    }

    public Muscle(String name, int gID) {
        this._name = name;
        this._groupID = gID;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_groupID() {
        return _groupID;
    }

    public void set_groupID(int _groupID) {
        this._groupID = _groupID;
    }
}
