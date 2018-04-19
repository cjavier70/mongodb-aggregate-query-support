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

package com.cisco.mongodb.aggregate.support.annotation.v2;

import com.cisco.mongodb.aggregate.support.annotation.Conditional;

import java.lang.annotation.Annotation;

import static com.cisco.mongodb.aggregate.support.annotation.Conditional.*;

/**
 * Created by rkolliva
 * 4/1/17.
 */
public @interface FacetPipelineStage {

  Class<? extends Annotation> stageType();

  String query();

  /**
   * A condition based on which the pipeline stage is included/excluded
   *
   * @return - the Conditions to be evaluated.
   */
  Conditional [] condition() default {};

  ConditionalMatchType conditionMatchType() default ConditionalMatchType.ANY;
}