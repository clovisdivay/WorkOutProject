package test.workoutproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pépère on 11/06/2017.
 */

public class Set {
    private int _NExercises;
    private ArrayList<Exercise> _Exercises;
    private int _NSets;
    private ArrayList<Integer> _NReps;
    private ArrayList<Double> _Weightkg;
    private ArrayList<Double> _WeightRM;
    private ArrayList<Integer> _WorkTime;
    private ArrayList<Integer> _RestTimeForExercise;
    private int _RestTimeForSet;


    //Constructors
    public Set(){
        this._NExercises = 0;
        this._NSets = 0;
        this._RestTimeForSet = 0;
        this._Exercises = null;
        this._NReps = null;
        this._Weightkg = null;
        this._WeightRM = null;
        this._WorkTime = null;
        this._RestTimeForExercise = null;
    }
    public Set(int _NExercises, ArrayList<Exercise> _Exercises, int _NSets, ArrayList<Integer> _NReps, ArrayList<Double> _Weightkg, int _RestTimeForSet) {
        this._NExercises = _NExercises;
        this._Exercises = _Exercises;
        this._NSets = _NSets;
        this._NReps = _NReps;
        this._Weightkg = _Weightkg;
        this._RestTimeForSet = _RestTimeForSet;
        this._WeightRM = new ArrayList<>();
        this._WorkTime = new ArrayList<>();
        this._RestTimeForExercise = new ArrayList<>();
        for (int i=0; i<_NExercises; i++){
            this._WeightRM.add(ConvertKgToRM(this._Exercises.get(i),this._Weightkg.get(i)));
            this._WorkTime.add(0);
            this._RestTimeForExercise.add(0);
        }
    }
    public Set(Exercise _Exercise, int _NSets, int _NReps, double _Weightkg, int _RestTimeForSet) {
        this._NExercises = 1;
        this._Exercises = new ArrayList<>();
        this._Exercises.add(_Exercise);
        this._NSets = _NSets;
        this._NReps = new ArrayList<>();
        this._NReps.add(_NReps);
        this._Weightkg = new ArrayList<>();
        this._Weightkg.add(_Weightkg);
        this._RestTimeForSet = _RestTimeForSet;
        this._WeightRM = new ArrayList<>();
        this._WorkTime = new ArrayList<>();
        this._RestTimeForExercise = new ArrayList<>();
        for (int i=0; i<_NExercises; i++){
            this._WeightRM.add(ConvertKgToRM(this._Exercises.get(i),this._Weightkg.get(i)));
            this._WorkTime.add(0);
            this._RestTimeForExercise.add(0);
        }
    }
    public Set(Exercise _Exercise, int _NSets, int _NReps, double _Weight, String Mode, int _RestTimeForSet) {
        this._NExercises = 1;
        this._Exercises = new ArrayList<Exercise>();
        this._Exercises.add(_Exercise);
        this._NSets = _NSets;
        this._NReps = new ArrayList<Integer>();
        this._NReps.add(_NReps);
        this._RestTimeForSet = _RestTimeForSet;

        if (Mode == "kg") {
            this._Weightkg = new ArrayList<Double>();
            this._Weightkg.add(_Weight);
            this._WeightRM = new ArrayList<Double>();
            this._WeightRM.add(ConvertKgToRM(_Exercise, _Weight));
        }
        else if (Mode == "rm") {
            this._WeightRM = new ArrayList<Double>();
            this._WeightRM.add(_Weight);
            this._Weightkg = new ArrayList<Double>();
            this._Weightkg.add(ConvertRMToKg(_Exercise,_Weight));
        }
        else {
            System.out.println("Error: Unknown weight setting mode! \n Please specify weight in \'kg\' or \'rm\' mode.");
        }

        this._WorkTime = new ArrayList<>();
        this._RestTimeForExercise = new ArrayList<>();
        for (int i=0; i<_NExercises; i++){
            this._WorkTime.add(0);
            this._RestTimeForExercise.add(0);
        }
    }
    public Set(int _NExercises, ArrayList<Exercise> _Exercises, int _NSets, ArrayList<Integer> _WorkTime, int _RestTimeForSet) {
        this._NExercises = _NExercises;
        this._Exercises = _Exercises;
        this._NSets = _NSets;
        this._WorkTime = _WorkTime;
        this._RestTimeForSet = _RestTimeForSet;
        this._WorkTime = new ArrayList<>();
        this._RestTimeForExercise = new ArrayList<>();
        this._NReps = new ArrayList<>();
        this._WeightRM = new ArrayList<>();
        this._Weightkg = new ArrayList<>();
        for (int i=0; i<_NExercises; i++){
            this._WorkTime.add(0);
            this._RestTimeForExercise.add(0);
            this._NReps.add(0);
            this._WeightRM.add(0.);
            this._Weightkg.add(0.);
        }
    }

    // Getters
    public int get_NExercises() {
        return _NExercises;
    }

    public ArrayList<Exercise> get_Exercises() {
        return _Exercises;
    }

    public Exercise get_Exercise(int i) {
        return _Exercises.get(i);
    }

    public int get_NSets() {
        return _NSets;
    }

    public ArrayList<Integer> get_NReps() {
        return _NReps;
    }

    public int get_NRepsForExercise(int i) {
        return _NReps.get(i);
    }

    public ArrayList<Double> get_Weightskg() {
        return _Weightkg;
    }

    public double get_WeightkgForExercise(int i) {
        return _Weightkg.get(i);
    }

    public ArrayList<Double> get_WeightsRM() {
        return _WeightRM;
    }

    public double get_WeightRMForExercise(int i) {
        return _WeightRM.get(i);
    }

    public ArrayList<Integer> get_WorkTime() {
        return _WorkTime;
    }

    public int get_WorkTimeForExercise(int i) {
        return _WorkTime.get(i);
    }

    public ArrayList<Integer> get_RestTimeForExercises() {
        return _RestTimeForExercise;
    }

    public int get_RestTimeForExercise(int i) {
        return _RestTimeForExercise.get(i);
    }

    public int get_RestTimeForSet() {
        return _RestTimeForSet;
    }


    //Setters
    public void set_NExercises(int _NExercises) {
        this._NExercises = _NExercises;
    }

    public void set_Exercises(ArrayList<Exercise> _Exercises) {
        this._Exercises = _Exercises;
        this._NExercises = _Exercises.size();
    }

    public void set_NSets(int _NSets) {
        this._NSets = _NSets;
    }

    public void set_NReps(ArrayList<Integer> _NReps) {
        this._NReps = _NReps;
    }

    public void set_Weightkg(ArrayList<Double> _Weightkg) {
        this._Weightkg = _Weightkg;
    }

    public void set_WeightForExercise(int i, double _Weight, String Mode) {
        if (Mode == "kg") {
            this._Weightkg = new ArrayList<Double>();
            this._Weightkg.add(_Weight);
            this._WeightRM = new ArrayList<Double>();
            this._WeightRM.add(ConvertKgToRM(this._Exercises.get(i),_Weight));
        }
        else if (Mode == "rm") {
            this._WeightRM = new ArrayList<Double>();
            this._WeightRM.add(_Weight);
            this._Weightkg = new ArrayList<Double>();
            this._Weightkg.add(ConvertRMToKg(this._Exercises.get(i),_Weight));
        }
        else {
            System.out.println("Error: Unknown weight setting mode! \n Please specify weight in \'kg\' or \'rm\' mode.");
        }
    }

    public void set_WeightRM(ArrayList<Double> _WeightRM) {
        this._WeightRM = _WeightRM;
    }

    public void set_WorkTime(ArrayList<Integer> _WorkTime) {
        this._WorkTime = _WorkTime;
    }

    public void set_RestTimeForExercise(ArrayList<Integer> _RestTimeForExercise) {
        this._RestTimeForExercise = _RestTimeForExercise;
    }

    public void set_RestTimeForSet(int _RestTimeForSet) {
        this._RestTimeForSet = _RestTimeForSet;
    }


    // Additionnal Methods
    public void Add_Exercise(Exercise Ex, int reps, double weight){
        if (this._NExercises == 0) {
            this._NExercises = 1;
            this._NSets = 1;
            this._RestTimeForSet = 0;
            this._Exercises = new ArrayList<>();
            this._NExercises = this._Exercises.size();
            this._NReps = new ArrayList<>();
            this._NReps.add(reps);
            this._Weightkg = new ArrayList<>();
            this._Weightkg.add(weight);
            this._WeightRM = new ArrayList<>();
            this._WeightRM.add(ConvertKgToRM(Ex, weight));
            this._WorkTime = new ArrayList<>();
            this._RestTimeForExercise = new ArrayList<>();

        }
        else {
            System.out.println("else statement of Add_Exercise method");
            this._Exercises.add(Ex);
            this._NExercises = this._Exercises.size();
            this._NReps.add(reps);
            this._Weightkg.add(weight);
            this._WeightRM.add(ConvertKgToRM(Ex, weight));
            System.out.println("out of else statement of Add_Exercise method");
        }

        System.out.println("out of Add_Exercise method");
    }

    public double ConvertKgToRM(Exercise Ex, double weightkg){
        double weightRM;
        double RM = Ex.get_RM();
        weightRM = Math.round(weightkg/RM *1000);
        weightRM /= 10.;
        return weightRM;
    }
    public double ConvertRMToKg(Exercise Ex, double weightRM){
        double weightkg;
        double RM = Ex.get_RM();
        weightkg = Math.round(weightRM*RM *10.);
        weightkg /= 10.;
        return weightkg;
    }

    public double CalculateRM(double weightkg, int reps){
        double RM = 0.;
        return RM;
    }

}
