package com.jruilibarary.widget.letterlist;



import java.util.Comparator;

/**
 * Created by zhush on 2016/8/11.
 */
public class SortByName implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        LetterModle s1 = (LetterModle) o;
        LetterModle s2 = (LetterModle) t1;
        return s1.getFirstLetter().compareTo(s2.getFirstLetter());
    }
}
