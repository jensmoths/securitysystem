package model;

import javax.management.ObjectName;
import java.util.HashMap;
import java.util.LinkedList;

public class Buffer {

    private LinkedList list = new LinkedList<>();

    public void add(Object object){

        list.addLast(object);

    }

    public boolean objectListIsEmpty(){

        if (list.isEmpty()){
            return true;
        }
        else return false;
    }

    public Object getList(){

        for (int i = 0; i<list.size(); i++){

           return list.get(i);

        }
        list.clear();
        return null;
    }

}
