package org.m_tag.jfindutils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses
({
	org.m_tag.jfindutils.find.FindFileIteratorTest.class,
	org.m_tag.jfindutils.locate.DbFileIteratorTest.class
})
public class TestSuit {

}