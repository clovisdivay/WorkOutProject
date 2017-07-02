package test.workoutproject;

/**
 * Created by Pépère on 11/06/2017.
 * Classe "exercice" pour remplir et récupérer depuis une base de données.
 * Permet également de construire une séance.
 */

public class Exercise {

    private int _id;
    private String _name;
    private String _description;
    private Muscle[] _muscles;
    private double _RM;

    public Exercise(){}
    public Exercise(String name){
        this._name = name;
        this._description = "";
        this._muscles = null;
        this._RM = -1;
    }
    public Exercise(String name,  Muscle[] muscles) {
        this._name = name;
        this._description = "";
        this._muscles = muscles;
        this._RM = -1;
    }

    public Exercise(String _name, double _RM) {
        this._name = _name;
        this._description = "";
        this._muscles = null;
        this._RM = _RM;
    }

    public Exercise(String _name, String _description, Muscle[] _muscles, double _RM) {
        this._name = _name;
        this._description = _description;
        this._muscles = _muscles;
        this._RM = _RM;
    }

    public Exercise( Exercise FromDB ){
        this._id = FromDB._id;
        this._name = FromDB._name;
        this._description = FromDB._description;
        this._muscles = FromDB._muscles;
        this._RM = FromDB._RM;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_description() {
        return _description;
    }

    public Muscle[] get_muscles() {
        return _muscles;
    }

    public Muscle get_muscle(int i) {
        return _muscles[i];
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

    public void set_description(String _description) {
        this._description = _description;
    }

    public void set_muscles(Muscle[] _muscles) {
        this._muscles = _muscles;
    }


    public void set_muscle(Muscle muscle) {
        this._muscles = new Muscle[1];
        this._muscles[0] = muscle;
    }

    public void set_RM(double _RM) {
        this._RM = _RM;
    }
}
