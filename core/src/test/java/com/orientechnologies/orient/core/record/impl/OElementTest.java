package com.orientechnologies.orient.core.record.impl;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by luigidellaquila on 02/07/16.
 */

public class OElementTest {

  static ODatabase db;


  @BeforeClass
  public static void beforeClass(){
    db = new ODatabaseDocumentTx("memory:OElementTest");
    db.create();
  }
  @AfterClass
  public static void afterClass(){
    db.drop();
  }

  @Test
  public void testGetSetProperty(){
    OElement elem = db.newElement();
    elem.setProperty("foo", "foo1");
    elem.setProperty("foo.bar", "foobar");
    elem.setProperty("  ", "spaces");

    Set<String> names = elem.getPropertyNames();
    Assert.assertTrue(names.contains("foo"));
    Assert.assertTrue(names.contains("foo.bar"));
    Assert.assertTrue(names.contains("  "));
  }

  @Test
  public void testLoadAndSave(){
    db.createClassIfNotExist("TestLoadAndSave");
    OElement elem = db.newElement("TestLoadAndSave");
    elem.setProperty("name", "foo");
    db.save(elem);

    List<OElement> result = db.query(new OSQLSynchQuery<>("select from TestLoadAndSave where name = 'foo'"));
    Assert.assertEquals(1, result.size());
    Assert.assertEquals("foo", result.get(0).getProperty("name"));

  }
}