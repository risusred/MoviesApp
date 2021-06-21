package org.example.moviesapp;


import org.example.moviesapp.model.MyItemTest;
import org.example.moviesapp.repo.MoviesHttpRepoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExampleUnitTest.class,
        MoviesHttpRepoTest.class,
        MyItemTest.class})
public class AllMyUnitTests {}
