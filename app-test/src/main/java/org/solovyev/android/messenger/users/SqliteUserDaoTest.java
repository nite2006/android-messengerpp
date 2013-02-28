package org.solovyev.android.messenger.users;

import com.google.inject.Inject;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.solovyev.android.messenger.AbstractMessengerTestCase;
import org.solovyev.android.messenger.realms.TestRealmDef;
import org.solovyev.android.properties.AProperty;
import org.solovyev.android.properties.APropertyImpl;
import org.solovyev.common.Objects;
import org.solovyev.common.equals.ListEqualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 2/24/13
 * Time: 4:50 PM
 */
public class SqliteUserDaoTest extends AbstractMessengerTestCase {

    @Inject
    private UserDao userDao;

    @Inject
    private TestRealmDef testRealmDef;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        userDao.deleteAllUsers();
    }

    public void testUserOperation() throws Exception {
        // INSERT

        List<AProperty> expectedProperties = new ArrayList<AProperty>();
        expectedProperties.add(APropertyImpl.newInstance("prop_1", "prop_1_value"));
        expectedProperties.add(APropertyImpl.newInstance("prop_2", "prop_2_value"));
        expectedProperties.add(APropertyImpl.newInstance("prop_3", "prop_3_value"));
        User expected = UserImpl.newInstance(TestRealmDef.REALM_ID, "test_01", UserSyncDataImpl.newNeverSyncedInstance(), expectedProperties);

        userDao.insertUser(expected);
        userDao.insertUser(expected);

        User actual = userDao.loadUserById("test_test_01");

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(TestRealmDef.REALM_ID, actual.getRealmUser().getRealmId());
        Assert.assertEquals("test_01", actual.getRealmUser().getRealmEntityId());
        Assert.assertEquals(TestRealmDef.REALM_ID + "_test_01", actual.getRealmUser().getEntityId());
        Assert.assertTrue(Objects.areEqual(expectedProperties, actual.getProperties(), new ListEqualizer<AProperty>(false, null)));
        Assert.assertEquals("prop_1_value", actual.getPropertyValueByName("prop_1"));

        User actual2 = userDao.loadUserById("test_test_01");
        Assert.assertEquals(expected, actual2);

        User actual3 = userDao.loadUserById("test_01");
        Assert.assertNull(actual3);

        Assert.assertTrue(Objects.areEqual(userDao.loadUserIds(), Arrays.asList("test_test_01"), new ListEqualizer<String>(false, null)));

        expected = UserImpl.newInstance(TestRealmDef.REALM_ID, "test_02", UserSyncDataImpl.newInstance(DateTime.now(), DateTime.now(), DateTime.now(), DateTime.now()), expectedProperties);
        userDao.insertUser(expected);
        Assert.assertTrue(Objects.areEqual(userDao.loadUserIds(), Arrays.asList("test_test_01", "test_test_02"), new ListEqualizer<String>(false, null)));

        // UPDATE
        expectedProperties = new ArrayList<AProperty>(expectedProperties);
        expectedProperties.remove(0);
        expectedProperties.add(APropertyImpl.newInstance("prop_4", "prop_4_value"));

        expected = UserImpl.newInstance(TestRealmDef.REALM_ID, "test_01", UserSyncDataImpl.newInstance(DateTime.now(), DateTime.now(), DateTime.now(), DateTime.now()), expectedProperties);
        userDao.updateUser(expected);
        actual = userDao.loadUserById("test_test_01");

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Assert.assertTrue(Objects.areEqual(expectedProperties, actual.getProperties(), new ListEqualizer<AProperty>(false, null)));

        Assert.assertTrue(Objects.areEqual(userDao.loadUserIds(), Arrays.asList("test_test_01", "test_test_02"), new ListEqualizer<String>(false, null)));

        expected = UserImpl.newInstance(TestRealmDef.REALM_ID, "test_01dsfsdfsf", UserSyncDataImpl.newInstance(DateTime.now(), DateTime.now(), DateTime.now(), DateTime.now()), expectedProperties);
        userDao.updateUser(expected);
    }

    @Override
    public void tearDown() throws Exception {
        userDao.deleteAllUsers();
        super.tearDown();
    }
}