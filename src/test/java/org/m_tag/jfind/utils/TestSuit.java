package org.m_tag.jfind.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * test suit for all.
 *
 * @author mtag@m-tag.org
 */
@RunWith(Suite.class)
@SuiteClasses({org.m_tag.jfind.utils.find.FindFileIteratorTest.class,
    org.m_tag.jfind.utils.locate.DbFileIteratorTest.class})
public class TestSuit {
}
