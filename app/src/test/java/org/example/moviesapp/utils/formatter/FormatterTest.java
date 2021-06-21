package org.example.moviesapp.utils.formatter;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

//@RunWith(RobolectricTestRunner.class)
public class FormatterTest extends TestCase {

    private Formatter SUT;

    public void setUp() throws Exception {
        super.setUp();
        SUT = new Formatter();
    }

    @Test
    public void testGetGenreIds() {
        ArrayList<Integer> candidates = new ArrayList<>();
        candidates.add(0);
        candidates.add(0);
        String actual = SUT.getGenreIds(candidates);
        assertEquals("0\t0\t", actual);

        candidates = new ArrayList<>();
        candidates.add(28);
        candidates.add(12);
        actual = SUT.getGenreIds(candidates);
        assertEquals("Action\tAdventure\t", actual);

        candidates = new ArrayList<>();
        candidates.add(35);
        actual = SUT.getGenreIds(candidates);
        assertEquals("Comedy\t", actual);
    }
}