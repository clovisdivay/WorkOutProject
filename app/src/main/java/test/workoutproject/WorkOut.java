package test.workoutproject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pépère on 11/06/2017.
 */

public class WorkOut {
    private int _NSets;
    private ArrayList<Set> _Sets;
    private Date _date;

    public WorkOut(){
        this._NSets = 0;
        this._Sets = new ArrayList<>();
        this._date = new Date();
    }

    public WorkOut(ArrayList<Set> _Sets, Date _date) {
        this._NSets = _Sets.size();
        this._Sets = _Sets;
        this._date = _date;
    }

    public WorkOut(Set _Set, Date _date) {
        this._Sets = new ArrayList<>();
        this._Sets.add(_Set);
        this._NSets = this._Sets.size();
        this._date = _date;
    }

    // Getters
    public ArrayList<Set> get_Sets() {
        return _Sets;
    }

    public Set get_Set(int i) {
        return _Sets.get(i);
    }

    public int get_NSets() {
        return _NSets;
    }

    public Date get_date() {
        return _date;
    }

    // Setters
    public void set_Sets(ArrayList<Set> _Sets) {
        this._Sets = _Sets;
        this._NSets = _Sets.size();
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    // Other Methods
    public void AddSet(Set _Set){
        this._NSets += 1;
        this._Sets.add(_Set);
    }

}
