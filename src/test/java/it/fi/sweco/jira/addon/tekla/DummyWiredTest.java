package it.fi.sweco.jira.addon.tekla;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;

import static org.junit.Assert.assertTrue;

@RunWith(AtlassianPluginsTestRunner.class)
public class DummyWiredTest
{
    private final ApplicationProperties applicationProperties;

    public DummyWiredTest(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    @Test
    public void testMyName()
    {
        assertTrue(true);
    }
}