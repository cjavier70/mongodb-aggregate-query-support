package com.github.krr.mongodb.aggregate.support.test.tests;

import com.github.krr.mongodb.aggregate.support.test.beans.Possessions;
import com.github.krr.mongodb.aggregate.support.test.config.AggregateTestConfiguration;
import com.github.krr.mongodb.aggregate.support.test.repository.PossessionsRepository;
import com.github.krr.mongodb.aggregate.support.test.utils.FixtureUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertNotNull;

/**
 * camejavi 3/31/17
 */
@ContextConfiguration(classes = AggregateTestConfiguration.class)
public class UnbindableParametersTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnbindableParametersTest.class);

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private PossessionsRepository possessionsRepository;

  @Test
  public void mustBeAbleToUseConditionalPipelineStageAsWellAsPageable() {
    LOGGER.info(">>>> UnbindableParametersTest::mustBeAbleToUseConditionalPipelineStageAsWellAsPageable");
    String tag = RandomStringUtils.randomAlphabetic(10);
    Possessions expectedCarPossessions = FixtureUtils.createPossessions(true, false, tag);
    Possessions expectedHomePossessions = FixtureUtils.createPossessions(false, true, tag);
    possessionsRepository.saveAll(Arrays.asList(expectedCarPossessions, expectedHomePossessions));
    List<Possessions> carsOnlyPossessions = possessionsRepository.mutuallyExclusiveStagesPageable(tag, true,
                                                                                                  null,
                                                                                                  PageRequest.of(0, 10))
                                                                 .collectList().block();
    //this query must not throw error when accessing pageable in AggregateQueryProvider::getParameterValues
    assertNotNull(carsOnlyPossessions);
  }
}