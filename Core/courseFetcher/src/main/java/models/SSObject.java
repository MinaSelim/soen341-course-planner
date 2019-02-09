package models;

public class SSObject {

    public int ObjectID;
    public int BaseYear;

    public SSObject(int id)
    {
        ObjectID = id;
    }

    public SSObject()
    {
        ObjectID = 0;
        BaseYear = 2019;
    }

    public static enum Program
    {
        SOEN,
        COEN,
        COMP;
    }

    public static enum Seasons
    {
        WINTER,
        SUMMER,
        FALL
    }
}
