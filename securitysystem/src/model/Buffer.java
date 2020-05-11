package model;

import javax.management.ObjectName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Buffer<T> implements Serializable {

    private LinkedList<T> list = new LinkedList<T>();

    public void add(T put) {
        list.addLast(put);
    }

    public boolean objectListIsEmpty() {
        if (list.isEmpty()) {
            return true;
        } else return false;
    }

   /* public T get(){

        for (int i = 0; i<list.size(); i++){

           return list.get(i);

        }
        list.clear();
        return null;
    }

    */

    public LinkedList<T> getList() {
        return list;
    }

    public T getObjects(int i) {

        return list.get(i);

    }

    public void clearObjectBuffer(){

        list.clear();

    }


    public int getBufferSize() {

        return list.size();

    }

}
