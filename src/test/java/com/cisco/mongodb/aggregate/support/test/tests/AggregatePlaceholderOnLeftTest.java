/*
 *  Copyright (c) 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *
 */

package com.cisco.mongodb.aggregate.support.test.tests;

import com.cisco.mongodb.aggregate.support.test.beans.Asset;
import com.cisco.mongodb.aggregate.support.test.beans.Possessions;
import com.cisco.mongodb.aggregate.support.test.config.AggregateTestConfiguration;
import com.cisco.mongodb.aggregate.support.test.repository.PossessionsRepository;
import com.cisco.mongodb.aggregate.support.test.utils.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cisco.mongodb.aggregate.support.test.utils.FixtureUtils.createPossessions;
import static com.cisco.mongodb.aggregate.support.test.utils.StringUtils.getRandomStrings;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by rkolliva
 * 3/1/17.
 */


@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@ContextConfiguration(classes = AggregateTestConfiguration.class)
public class AggregatePlaceholderOnLeftTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private PossessionsRepository possessionsRepository;

  private Possessions possessionsWithCars;

  private Possessions possessionsWithHomes;

  @BeforeClass
  public void setup() {
    possessionsWithCars = createPossessions(true, false);
    possessionsWithHomes = createPossessions(false, true);
    possessionsRepository.save(Arrays.asList(possessionsWithCars, possessionsWithHomes));
  }

  @Test
  public void mustReplacePlaceholderOnLeftForAggregateQuery() {
    boolean hasCars = possessionsRepository.hasCars();
    assertTrue(hasCars);
  }

  @Test(dataProvider = "possessionsProvider")
  public void mustReturnCarsFromAggregateQuery(String type, Possessions expectedPossessions) {
    List<Possessions> possessions = possessionsRepository.getPossessions(type);
    assertNotNull(possessions);
    assertTrue(possessions.size() == 1);
    Possessions possession = possessions.get(0);
    assertTrue(possession.getId().equals(expectedPossessions.getId()));
  }

  @DataProvider
  public Object[][] possessionsProvider() {
    return new Object[][] {
        new Object[] {"cars", possessionsWithCars},
        new Object[] {"homes", possessionsWithHomes}
    };
  }

}