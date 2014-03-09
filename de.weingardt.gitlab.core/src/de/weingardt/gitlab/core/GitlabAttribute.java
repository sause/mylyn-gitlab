package de.weingardt.gitlab.core;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

public enum GitlabAttribute {

	BODY("Description", TaskAttribute.DESCRIPTION,
			TaskAttribute.TYPE_LONG_RICH_TEXT),

	TITLE("Summary", TaskAttribute.SUMMARY, 
			TaskAttribute.TYPE_SHORT_RICH_TEXT),

	STATUS("Status", TaskAttribute.STATUS, 
			TaskAttribute.TYPE_SHORT_TEXT, GitlabFlag.ATTRIBUTE, GitlabFlag.READ_ONLY),

	LABELS("Labels", TaskAttribute.TASK_KIND,
			TaskAttribute.TYPE_SHORT_TEXT, GitlabFlag.ATTRIBUTE),

	UPDATED("Updated", TaskAttribute.DATE_MODIFICATION,
			TaskAttribute.TYPE_DATETIME, GitlabFlag.READ_ONLY, GitlabFlag.ATTRIBUTE),

	CREATED("Created", TaskAttribute.DATE_CREATION, 
			TaskAttribute.TYPE_DATETIME, GitlabFlag.READ_ONLY, GitlabFlag.ATTRIBUTE),

	AUTHOR("Author", TaskAttribute.USER_REPORTER,
			TaskAttribute.TYPE_PERSON, GitlabFlag.READ_ONLY, GitlabFlag.ATTRIBUTE),

	PROJECT("Project", TaskAttribute.PRODUCT,
			TaskAttribute.TYPE_SHORT_TEXT, GitlabFlag.READ_ONLY, GitlabFlag.ATTRIBUTE),

	ASSIGNEE("Assignee", TaskAttribute.USER_ASSIGNED,
			TaskAttribute.TYPE_PERSON, GitlabFlag.ATTRIBUTE);

	private Set<GitlabFlag> flags;

	private final String prettyName;

	private final String taskKey;

	private final String type;

	public String getKind() {
		if(type.equals(TaskAttribute.TYPE_PERSON)) {
			return TaskAttribute.KIND_PEOPLE;
		} else if (flags.contains(GitlabFlag.ATTRIBUTE)) {
			return TaskAttribute.KIND_DEFAULT;
		}
		return null;
	}

	public boolean isReadOnly() {
		return flags.contains(GitlabFlag.READ_ONLY);
	}

	GitlabAttribute(String prettyName, String taskKey, String type, GitlabFlag... flags) {
		this.taskKey = taskKey;
		this.prettyName = prettyName;
		this.type = type;
		if (flags == null || flags.length == 0) {
			this.setFlags(EnumSet.noneOf(GitlabFlag.class));
		} else {
			this.setFlags(EnumSet.copyOf(Arrays.asList(flags)));
		}
	}

	GitlabAttribute(String prettyName, String taskKey, String type) {
		this(prettyName, taskKey, type, new GitlabFlag[] {});
	}

	public Set<GitlabFlag> getFlags() {
		return flags;
	}

	public void setFlags(Set<GitlabFlag> flags) {
		this.flags = flags;
	}

	public String getPrettyName() {
		return prettyName;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		return this.prettyName;
	}

	public static GitlabAttribute get(String key) {
		for(GitlabAttribute attr : GitlabAttribute.values()) {
			if(attr.getTaskKey().equals(key)) {
				return attr;
			}
		}
		return null;
	}
	
}
