package fi.sweco.jira.addon.tekla;

import com.atlassian.jira.issue.link.RemoteIssueLink;
import com.atlassian.jira.issue.link.RemoteIssueLinkBuilder;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.webresource.UrlMode;
import com.atlassian.plugin.webresource.WebResourceUrlProvider;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by jlinnosa on 05/06/16.
 */
@Scanned
public class TeklaIssueLinkRenderer extends com.atlassian.jira.plugin.viewissue.issuelink.DefaultIssueLinkRenderer {

    private final WebResourceUrlProvider urlProvider;

    @Autowired
    public TeklaIssueLinkRenderer(@ComponentImport final WebResourceUrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    @Override
    public Map<String, Object> getInitialContext(RemoteIssueLink remoteIssueLink, Map<String, Object> context) {
        Map<String,Object> map = Maps.newHashMap();

        map.putAll(super.getInitialContext(remoteIssueLink,context));
        map.put("iconUrl",urlProvider.getStaticPluginResourceUrl("aechackathon.tekla-uri-plugin:resources","images/tekla.png", UrlMode.RELATIVE));
        map.put("iconTooltip","Tekla Link");
        map.put("url",remoteIssueLink.getUrl().replaceFirst("^http://","tekla:"));
        map.put("title",remoteIssueLink.getTitle());
        return map;
    }
}
