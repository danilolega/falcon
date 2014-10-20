/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.falcon.regression.core.enumsAndConstants;

import org.apache.falcon.regression.core.util.Config;
import org.apache.falcon.request.RequestKeys;
import org.apache.hadoop.conf.Configuration;
import org.testng.Assert;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Class for test constants.
 */
public final class MerlinConstants {
    private MerlinConstants() {
    }

    private static final Logger LOGGER = Logger.getLogger(MerlinConstants.class);

    public static final boolean IS_SECURE =
        "kerberos".equals(new Configuration().get("hadoop.security.authentication", "simple"));
    /** the user that is going to run tests. */
    public static final String CURRENT_USER_NAME = System.getProperty("user.name");
    /** keytab of current user. */
    private static final String CURRENT_USER_KEYTAB_STR = "current_user_keytab";
    /** group of the current user. */
    public static final String CURRENT_USER_GROUP =
        Config.getProperty("current_user.group.name", "users");

    /** a user that does not belong to the group of current user. */
    public static final String DIFFERENT_USER = Config.getProperty("other.user.name", "root");

    /** falcon super user. */
    public static final String FALCON_SUPER_USER_NAME =
            Config.getProperty("falcon.super.user.name", "falcon");

    /** a user that belongs to falcon super user group but is not FALCON_SUPER_USER_NAME. */
    public static final String FALCON_SUPER_USER2_NAME =
            Config.getProperty("falcon.super.user2.name", "falcon2");
    private static final String USER_2_NAME_STR = "user2_name";
    private static final String USER_2_KEYTAB_STR = "user2_keytab";
    public static final String USER2_NAME;
    private static HashMap<String, String> keyTabMap;
    public static final String ACL_OWNER = Config.getProperty("ACL.OWNER", RequestKeys.CURRENT_USER);
    public static final String ACL_GROUP = Config.getProperty("ACL.GROUP", "default");
    public static final String USER_REALM = Config.getProperty("USER.REALM", "");

    public static final boolean CLEAN_TEST_DIR =
        Boolean.valueOf(Config.getProperty("clean_test_dir", "true"));

    /* initialize keyTabMap */
    static {
        final String currentUserKeytab = Config.getProperty(CURRENT_USER_KEYTAB_STR);
        final String user2Name = Config.getProperty(USER_2_NAME_STR);
        final String user2Keytab = Config.getProperty(USER_2_KEYTAB_STR);
        LOGGER.info("CURRENT_USER_NAME: " + CURRENT_USER_NAME);
        LOGGER.info("currentUserKeytab: " + currentUserKeytab);
        LOGGER.info("user2Name: " + user2Name);
        LOGGER.info("user2Keytab: " + user2Keytab);
        USER2_NAME = user2Name;
        keyTabMap = new HashMap<String, String>();
        keyTabMap.put(CURRENT_USER_NAME, currentUserKeytab);
        keyTabMap.put(user2Name, user2Keytab);
        keyTabMap.put(FALCON_SUPER_USER_NAME, Config.getProperty("falcon.super.user.keytab"));
        keyTabMap.put(FALCON_SUPER_USER2_NAME, Config.getProperty("falcon.super.user2.keytab"));
        keyTabMap.put(DIFFERENT_USER, Config.getProperty("other.user.keytab"));
    }

    public static String getKeytabForUser(String user) {
        Assert.assertTrue(keyTabMap.containsKey(user), "Unknown user: " + user);
        return keyTabMap.get(user);
    }
}
