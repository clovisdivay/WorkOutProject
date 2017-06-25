package test.workoutproject;

/**
 * Created by Pépère on 11/06/2017.
 */

public class Exercise {

    private int _id;
    private String _name;
    private String _type;
    private Muscle[] _primary;
    private Muscle[] _secondary;
    private double _RM;

    public Exercise(){}
    public Exercise(String name){
        this._name = name;
        this._type = null;
        this._primary = null;
        this._secondary = null;
        this._RM = -1;
    }
    public Exercise(String name, String type, Muscle pr) {
        this._name = name;
        this._type = type;
        this._primary = new Muscle[1];
        this._primary[0] = pr;
        this._secondary = null;
        this._RM = -1;
    }

    public Exercise(String name, String type, Muscle pr, Muscle sec){
        this._name = name;
        this._type = type;
        this._primary = new Muscle[1];
        this._primary[0] = pr;
        this._secondary = new Muscle[1];
        this._secondary[0] = sec;
        this._RM = -1;
    }

    public Exercise(String _name, String _type, Muscle[] _primary, Muscle[] _secondary) {
        this._name = _name;
        this._type = _type;
        this._primary = _primary;
        this._secondary = _secondary;
        this._RM = -1;
    }

    public Exercise(int _id, String _name, String _type, Muscle[] _primary, Muscle[] _secondary, double _RM) {
        this._id = _id;
        this._name = _name;
        this._type = _type;
        this._primary = _primary;
        this._secondary = _secondary;
        this._RM = _RM;
    }

    public Exercise( Exercise FromDB ){
        this._id = FromDB._id;
        this._name = FromDB._name;
        this._type = FromDB._type;
        this._primary = FromDB._primary;
        this._secondary = FromDB._secondary;
        this._RM = FromDB._RM;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_type() {
        return _type;
    }

    public Muscle[] get_primary() {
        return _primary;
    }

    public Muscle[] get_secondary() {
        return _secondary;
    }

    public double get_RM() {
        return _RM;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public void set_primary(Muscle[] _primary) {
        this._primary = _primary;
    }

    public void set_secondary(Muscle[] _secondary) {
        this._secondary = _secondary;
    }

    public void set_primary(Muscle _primary) {
        this._primary = new Muscle[1];
        this._primary[0] = _primary;
    }

    public void set_secondary(Muscle _secondary) {
        this._secondary = new Muscle[1];
        this._secondary[0] = _secondary;
    }

    public void set_RM(double _RM) {
        this._RM = _RM;
    }
}
