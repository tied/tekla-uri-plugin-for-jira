package fi.sweco.jira.addon.tekla;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.bc.issue.comment.CommentService;
import com.atlassian.jira.bc.issue.link.RemoteIssueLinkService;
import com.atlassian.jira.config.SubTaskManager;
import com.atlassian.jira.event.issue.IssueEventBundleFactory;
import com.atlassian.jira.event.issue.IssueEventManager;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.fields.screen.FieldScreenRendererFactory;
import com.atlassian.jira.issue.link.RemoteIssueLink;
import com.atlassian.jira.issue.link.RemoteIssueLinkBuilder;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.web.action.issue.AbstractIssueLinkAction;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.springframework.beans.factory.annotation.Autowired;

@Scanned
public class AddTeklaLink extends AbstractIssueLinkAction {
    private String iconUrl;
    private String url;
    private String title;

    @Autowired
    AddTeklaLink(
            @ComponentImport final SubTaskManager subTaskManager,
            @ComponentImport final FieldScreenRendererFactory fieldScreenRendererFactory,
            @ComponentImport final FieldManager fieldManager,
            @ComponentImport final ProjectRoleManager projectRoleManager,
            @ComponentImport final CommentService commentService,
            @ComponentImport final UserUtil userUtil,
            @ComponentImport final RemoteIssueLinkService remoteIssueLinkService,
            @ComponentImport final EventPublisher eventPublisher,
            @ComponentImport final IssueEventManager issueEventManager,
            @ComponentImport final IssueEventBundleFactory issueEventBundleFactory) {
        super(subTaskManager, fieldScreenRendererFactory, fieldManager, projectRoleManager, commentService, userUtil, remoteIssueLinkService, eventPublisher, issueEventManager, issueEventBundleFactory);
    }

    protected void doValidation() {
        // Validate comment and permissions
        super.doValidation();

        final RemoteIssueLink remoteIssueLink = new RemoteIssueLinkBuilder()
                .issueId(getIssue().getLong("id"))
                .iconUrl(iconUrl)
                .url(url.replaceFirst("^tekla:","http://"))
                .title(title)
                .applicationType("com.trimble.tekla")
                .build();

        validationResult = remoteIssueLinkService.validateCreate(getLoggedInUser(), remoteIssueLink);

        if (!validationResult.isValid()) {
            addErrorCollection(validationResult.getErrorCollection());
        }

    }

    public String doDefault() throws Exception {
        // Set default value
        url = "tekla:";

        return super.doDefault();
    }

    @RequiresXsrfCheck
    protected String doExecute() {
        final RemoteIssueLinkService.RemoteIssueLinkResult result = createLink();

        if (!result.isValid()) {
            addErrorCollection(result.getErrorCollection());
            return ERROR;
        }

        createAndDispatchComment();

        return returnComplete(getRedirectUrl());
    }



    @SuppressWarnings("unused")
    public String getIconUrl() {
        return iconUrl;
    }

    @SuppressWarnings("unused")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @SuppressWarnings("unused")
    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }
}
