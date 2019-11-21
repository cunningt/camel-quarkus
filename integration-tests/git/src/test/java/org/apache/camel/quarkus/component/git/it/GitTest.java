/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.component.git.it;

import java.util.UUID;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.jboss.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
class GitTest {
    private static final Logger LOG = Logger.getLogger(GitTest.class);
    private static final String FILE_BODY = "Hello Camel Quarkus";

    @Test
    public void testGitComponent() {
        String fileName = RestAssured.given()
                .contentType(ContentType.TEXT)
                .body(FILE_BODY)
                .post("/git/create")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        // Check for commits
        LOG.infof("Checking commits");

        RestAssured
                .get("/git/commits")
                .then()
                .statusCode(200)
                .body(startsWith("commit"));

        // Check for branches
        LOG.infof("Checking branches");

        RestAssured
                .get("/git/branches")
                .then()
                .statusCode(200)
                .body(startsWith("Ref"));
    }

}
