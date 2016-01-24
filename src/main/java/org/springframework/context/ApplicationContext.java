package org.springframework.context;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.EnvironmentCapable;

/**
 * spring上下文的接口
 * @author liuxiaoyang
 *
 */
public interface ApplicationContext extends EnvironmentCapable {
	String getId();
	String getDisplayName();
	long getStartupDate();
	ApplicationContext getParent();
	AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;
}
